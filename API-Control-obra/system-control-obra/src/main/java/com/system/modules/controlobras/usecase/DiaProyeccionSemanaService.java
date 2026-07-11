package com.system.modules.controlobras.usecase;

import com.system.crosscutting.domain.model.EntyDiaProyeccionSemanaDto;
import com.system.crosscutting.domain.model.GenerarSemanasRequestDto;
import com.system.crosscutting.domain.model.ProyeccionSemanaConDiasDto;
import com.system.crosscutting.persistence.entity.EntityDiaProyeccionSemana;
import com.system.crosscutting.persistence.entity.EntyOrsordmdproyecsemana;
import com.system.crosscutting.persistence.repository.EntyDiaProyeccionSemanaRepository;
import com.system.crosscutting.persistence.repository.EntyOrsordmdproyecsemanaRepository;
import com.system.crosscutting.translate.EntyDiaProyeccionSemanaEntityToDtoTranslate;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DiaProyeccionSemanaService {

    private final EntyDiaProyeccionSemanaRepository diaRepository;
    private final EntyOrsordmdproyecsemanaRepository proyeccionRepository;
    private final EntyDiaProyeccionSemanaEntityToDtoTranslate entityToDtoTranslate;

    public DiaProyeccionSemanaService(
            final EntyDiaProyeccionSemanaRepository diaRepository,
            final EntyOrsordmdproyecsemanaRepository proyeccionRepository,
            final EntyDiaProyeccionSemanaEntityToDtoTranslate entityToDtoTranslate
    ) {
        this.diaRepository = diaRepository;
        this.proyeccionRepository = proyeccionRepository;
        this.entityToDtoTranslate = entityToDtoTranslate;
    }

    @Transactional
    public List<EntyDiaProyeccionSemanaDto> getDiasByProyeccion(
            final String proyeccionKey
    ) {
        validarTexto(proyeccionKey, "La proyección semanal es obligatoria.");

        return diaRepository
                .findByOrsIdentifkeyPsemOrderByOrsFechaDpseAsc(proyeccionKey)
                .stream()
                .map(entityToDtoTranslate::translate)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<EntyDiaProyeccionSemanaDto> getDiasByOrden(
            final String ordenKey
    ) {
        validarTexto(ordenKey, "La orden de servicio es obligatoria.");

        return diaRepository
                .findByOrsIdentifkeyOrdeOrderByOrsFechaDpseAsc(ordenKey)
                .stream()
                .map(entityToDtoTranslate::translate)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProyeccionSemanaConDiasDto> generarSemanas(
            final GenerarSemanasRequestDto request
    ) {
        validarRequestGenerarSemanas(request);

        final String ordenKey = request.getOrdenKey().trim();

        final LocalDate fechaInicialContrato = request.getFechaInicial();
        final LocalDate fechaFinalContrato = request.getFechaFinal();

        /*
         * Regla de negocio:
         * 1. Toda semana operativa inicia lunes.
         * 2. Toda semana operativa termina domingo.
         * 3. Si el contrato inicia después del lunes, los días anteriores
         *    al inicio real quedan creados, pero inhabilitados.
         * 4. Si el contrato termina antes del domingo, los días posteriores
         *    al fin real quedan creados, pero inhabilitados.
         * 5. Sábados y domingos dependen de incluirSabados e incluirDomingos.
         */
        final LocalDate fechaInicialSemana = obtenerLunesDeSemana(
                fechaInicialContrato
        );

        final LocalDate fechaFinalSemana = obtenerDomingoDeSemana(
                fechaFinalContrato
        );

        final boolean incluirSabados = request.getIncluirSabados() != null
                && Boolean.TRUE.equals(request.getIncluirSabados());

        final boolean incluirDomingos = request.getIncluirDomingos() != null
                && Boolean.TRUE.equals(request.getIncluirDomingos());

        List<ProyeccionSemanaConDiasDto> resultado = new ArrayList<>();

        LocalDate cursor = fechaInicialSemana;
        int numeroSemana = 1;

        while (!cursor.isAfter(fechaFinalSemana)) {
            LocalDate inicioSemana = cursor;
            LocalDate finSemana = cursor.plusDays(6);

            EntyOrsordmdproyecsemana proyeccion =
                    crearOActualizarProyeccionSemana(
                            ordenKey,
                            numeroSemana,
                            inicioSemana,
                            finSemana
                    );

            List<EntityDiaProyeccionSemana> dias =
                    crearOActualizarDiasSemana(
                            proyeccion.getOrsIdentifkeyPsem(),
                            ordenKey,
                            inicioSemana,
                            finSemana,
                            fechaInicialContrato,
                            fechaFinalContrato,
                            incluirSabados,
                            incluirDomingos
                    );

            actualizarTotalesSemana(proyeccion, dias);

            resultado.add(construirSemanaConDiasDto(proyeccion, dias));

            cursor = cursor.plusDays(7);
            numeroSemana++;
        }

        return resultado;
    }

    @Transactional
    public EntyDiaProyeccionSemanaDto cambiarHabilitado(
            final String diaKey,
            final Boolean habilitado
    ) {
        validarTexto(diaKey, "El día de proyección es obligatorio.");

        EntityDiaProyeccionSemana dia = buscarDiaPorKey(diaKey);

        boolean nuevoEstado = Boolean.TRUE.equals(habilitado);

        dia.setOrsEshabilDpse(nuevoEstado);

        if (!nuevoEstado) {
            dia.setOrsEstrabajadoDpse(false);
        }

        EntityDiaProyeccionSemana actualizado = diaRepository.save(dia);

        recalcularTotalesSemana(dia.getOrsIdentifkeyPsem());

        return entityToDtoTranslate.translate(actualizado);
    }

    @Transactional
    public EntyDiaProyeccionSemanaDto cambiarTrabajado(
            final String diaKey,
            final Boolean trabajado
    ) {
        validarTexto(diaKey, "El día de proyección es obligatorio.");

        EntityDiaProyeccionSemana dia = buscarDiaPorKey(diaKey);

        if (!Boolean.TRUE.equals(dia.getOrsEshabilDpse())
                && Boolean.TRUE.equals(trabajado)) {
            throw new IllegalArgumentException(
                    "No se puede marcar como trabajado un día deshabilitado."
            );
        }

        dia.setOrsEstrabajadoDpse(Boolean.TRUE.equals(trabajado));

        EntityDiaProyeccionSemana actualizado = diaRepository.save(dia);

        recalcularTotalesSemana(dia.getOrsIdentifkeyPsem());

        return entityToDtoTranslate.translate(actualizado);
    }

    private EntyOrsordmdproyecsemana crearOActualizarProyeccionSemana(
            final String ordenKey,
            final int numeroSemana,
            final LocalDate inicioSemana,
            final LocalDate finSemana
    ) {
        Optional<EntyOrsordmdproyecsemana> existente =
                proyeccionRepository.findByOrsIdentifkeyOrdeAndOrsNumerosemPsem(
                        ordenKey,
                        numeroSemana
                );

        EntyOrsordmdproyecsemana entity;

        if (existente.isPresent()) {
            entity = existente.get();
        } else {
            entity = new EntyOrsordmdproyecsemana();
            entity.setOrsIdentifkeyPsem(generarKey("PSEM"));
            entity.setOrsIdentifkeyOrde(ordenKey);
            entity.setOrsNumerosemPsem(numeroSemana);
        }

        entity.setOrsTitulosemPsem("Semana " + numeroSemana);
        entity.setOrsSemfechiniPsem(inicioSemana);
        entity.setOrsSemfechfinPsem(finSemana);
        entity.setOrsTiporegistPsem("1");
        entity.setOrsEstadoregPsem("1");

        if (entity.getOrsDiashabilesPsem() == null) {
            entity.setOrsDiashabilesPsem("0");
        }

        if (entity.getOrsDiasnhabilesPsem() == null) {
            entity.setOrsDiasnhabilesPsem("0");
        }

        return proyeccionRepository.save(entity);
    }

    private List<EntityDiaProyeccionSemana> crearOActualizarDiasSemana(
            final String proyeccionKey,
            final String ordenKey,
            final LocalDate inicioSemana,
            final LocalDate finSemana,
            final LocalDate fechaInicialContrato,
            final LocalDate fechaFinalContrato,
            final boolean incluirSabados,
            final boolean incluirDomingos
    ) {
        LocalDate cursor = inicioSemana;

        while (!cursor.isAfter(finSemana)) {
            DayOfWeek dayOfWeek = cursor.getDayOfWeek();

            boolean habilitado = calcularDiaHabilitado(
                    cursor,
                    dayOfWeek,
                    fechaInicialContrato,
                    fechaFinalContrato,
                    incluirSabados,
                    incluirDomingos
            );

            EntityDiaProyeccionSemana dia =
                    obtenerOCrearDiaProyeccion(
                            proyeccionKey,
                            ordenKey,
                            cursor
                    );

            dia.setOrsNombrediaDpse(nombreDia(dayOfWeek));
            dia.setOrsEshabilDpse(habilitado);

            if (!habilitado) {
                dia.setOrsEstrabajadoDpse(false);
            } else if (dia.getOrsEstrabajadoDpse() == null) {
                dia.setOrsEstrabajadoDpse(false);
            }

            dia.setOrsObservacionDpse(
                    construirObservacionDia(
                            cursor,
                            dayOfWeek,
                            fechaInicialContrato,
                            fechaFinalContrato,
                            incluirSabados,
                            incluirDomingos
                    )
            );

            dia.setOrsTiporegistDpse("1");
            dia.setOrsEstadoregDpse("1");

            diaRepository.save(dia);

            cursor = cursor.plusDays(1);
        }

        return diaRepository
                .findByOrsIdentifkeyPsemOrderByOrsFechaDpseAsc(proyeccionKey);
    }

    private EntityDiaProyeccionSemana obtenerOCrearDiaProyeccion(
            final String proyeccionKey,
            final String ordenKey,
            final LocalDate fecha
    ) {
        Optional<EntityDiaProyeccionSemana> existente =
                diaRepository.findByOrsIdentifkeyPsemAndOrsFechaDpse(
                        proyeccionKey,
                        fecha
                );

        if (existente.isPresent()) {
            return existente.get();
        }

        EntityDiaProyeccionSemana dia = new EntityDiaProyeccionSemana();

        dia.setOrsIdentifkeyDpse(generarKey("DPSE"));
        dia.setOrsIdentifkeyPsem(proyeccionKey);
        dia.setOrsIdentifkeyOrde(ordenKey);
        dia.setOrsFechaDpse(fecha);
        dia.setOrsEstrabajadoDpse(false);

        return dia;
    }

    private boolean calcularDiaHabilitado(
            final LocalDate fecha,
            final DayOfWeek dayOfWeek,
            final LocalDate fechaInicialContrato,
            final LocalDate fechaFinalContrato,
            final boolean incluirSabados,
            final boolean incluirDomingos
    ) {
        boolean estaDentroDelContrato =
                !fecha.isBefore(fechaInicialContrato)
                        && !fecha.isAfter(fechaFinalContrato);

        if (!estaDentroDelContrato) {
            return false;
        }

        if (DayOfWeek.SATURDAY.equals(dayOfWeek)) {
            return incluirSabados;
        }

        if (DayOfWeek.SUNDAY.equals(dayOfWeek)) {
            return incluirDomingos;
        }

        return true;
    }

    private String construirObservacionDia(
            final LocalDate fecha,
            final DayOfWeek dayOfWeek,
            final LocalDate fechaInicialContrato,
            final LocalDate fechaFinalContrato,
            final boolean incluirSabados,
            final boolean incluirDomingos
    ) {
        if (fecha.isBefore(fechaInicialContrato)) {
            return "Día fuera del contrato: anterior al inicio de la orden.";
        }

        if (fecha.isAfter(fechaFinalContrato)) {
            return "Día fuera del contrato: posterior al fin de la orden.";
        }

        if (DayOfWeek.SATURDAY.equals(dayOfWeek) && !incluirSabados) {
            return "Sábado no habilitado para esta proyección.";
        }

        if (DayOfWeek.SUNDAY.equals(dayOfWeek) && !incluirDomingos) {
            return "Domingo no habilitado para esta proyección.";
        }

        return null;
    }

    private void actualizarTotalesSemana(
            final EntyOrsordmdproyecsemana proyeccion,
            final List<EntityDiaProyeccionSemana> dias
    ) {
        int diasHabiles = (int) dias.stream()
                .filter(dia -> Boolean.TRUE.equals(dia.getOrsEshabilDpse()))
                .count();

        int diasNoHabiles = dias.size() - diasHabiles;

        proyeccion.setOrsDiashabilesPsem(String.valueOf(diasHabiles));
        proyeccion.setOrsDiasnhabilesPsem(String.valueOf(diasNoHabiles));

        proyeccionRepository.save(proyeccion);
    }

    private void recalcularTotalesSemana(final String proyeccionKey) {
        List<EntityDiaProyeccionSemana> dias = diaRepository
                .findByOrsIdentifkeyPsemOrderByOrsFechaDpseAsc(proyeccionKey);

        Optional<EntyOrsordmdproyecsemana> proyeccionOptional =
                proyeccionRepository.findByOrsIdentifkeyPsem(proyeccionKey);

        if (!proyeccionOptional.isPresent()) {
            return;
        }

        actualizarTotalesSemana(proyeccionOptional.get(), dias);
    }

    private ProyeccionSemanaConDiasDto construirSemanaConDiasDto(
            final EntyOrsordmdproyecsemana proyeccion,
            final List<EntityDiaProyeccionSemana> dias
    ) {
        ProyeccionSemanaConDiasDto dto = new ProyeccionSemanaConDiasDto();

        dto.setOrsPrimarykeyPsem(proyeccion.getOrsPrimarykeyPsem());
        dto.setOrsIdentifkeyPsem(proyeccion.getOrsIdentifkeyPsem());
        dto.setOrsIdentifkeyOrde(proyeccion.getOrsIdentifkeyOrde());
        dto.setOrsNumerosemPsem(proyeccion.getOrsNumerosemPsem());
        dto.setOrsTitulosemPsem(proyeccion.getOrsTitulosemPsem());
        dto.setOrsSemfechiniPsem(proyeccion.getOrsSemfechiniPsem());
        dto.setOrsSemfechfinPsem(proyeccion.getOrsSemfechfinPsem());
        dto.setOrsDiashabilesPsem(proyeccion.getOrsDiashabilesPsem());
        dto.setOrsDiasnhabilesPsem(proyeccion.getOrsDiasnhabilesPsem());
        dto.setOrsTiporegistPsem(proyeccion.getOrsTiporegistPsem());
        dto.setOrsEstadoregPsem(proyeccion.getOrsEstadoregPsem());

        List<EntyDiaProyeccionSemanaDto> diasDto = dias.stream()
                .sorted(Comparator.comparing(
                        EntityDiaProyeccionSemana::getOrsFechaDpse
                ))
                .map(entityToDtoTranslate::translate)
                .collect(Collectors.toList());

        dto.setDias(diasDto);

        return dto;
    }

    private EntityDiaProyeccionSemana buscarDiaPorKey(final String diaKey) {
        return diaRepository
                .findByOrsIdentifkeyDpse(diaKey)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el día de proyección semanal: " + diaKey
                ));
    }

    private void validarRequestGenerarSemanas(
            final GenerarSemanasRequestDto request
    ) {
        if (request == null) {
            throw new IllegalArgumentException(
                    "La solicitud para generar semanas es obligatoria."
            );
        }

        validarTexto(request.getOrdenKey(), "La orden de servicio es obligatoria.");

        if (request.getFechaInicial() == null) {
            throw new IllegalArgumentException("La fecha inicial es obligatoria.");
        }

        if (request.getFechaFinal() == null) {
            throw new IllegalArgumentException("La fecha final es obligatoria.");
        }

        if (request.getFechaInicial().isAfter(request.getFechaFinal())) {
            throw new IllegalArgumentException(
                    "La fecha inicial no puede ser mayor que la fecha final."
            );
        }
    }

    private void validarTexto(final String value, final String mensaje) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    private LocalDate obtenerLunesDeSemana(final LocalDate fecha) {
        int diasDesdeLunes =
                fecha.getDayOfWeek().getValue()
                        - DayOfWeek.MONDAY.getValue();

        return fecha.minusDays(diasDesdeLunes);
    }

    private LocalDate obtenerDomingoDeSemana(final LocalDate fecha) {
        int diasHastaDomingo =
                DayOfWeek.SUNDAY.getValue()
                        - fecha.getDayOfWeek().getValue();

        return fecha.plusDays(diasHastaDomingo);
    }

    private String generarKey(final String prefijo) {
        return prefijo + "-" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8)
                .toUpperCase();
    }

    private String nombreDia(final DayOfWeek dayOfWeek) {
        if (DayOfWeek.MONDAY.equals(dayOfWeek)) {
            return "Lunes";
        }

        if (DayOfWeek.TUESDAY.equals(dayOfWeek)) {
            return "Martes";
        }

        if (DayOfWeek.WEDNESDAY.equals(dayOfWeek)) {
            return "Miércoles";
        }

        if (DayOfWeek.THURSDAY.equals(dayOfWeek)) {
            return "Jueves";
        }

        if (DayOfWeek.FRIDAY.equals(dayOfWeek)) {
            return "Viernes";
        }

        if (DayOfWeek.SATURDAY.equals(dayOfWeek)) {
            return "Sábado";
        }

        if (DayOfWeek.SUNDAY.equals(dayOfWeek)) {
            return "Domingo";
        }

        return "";
    }
}