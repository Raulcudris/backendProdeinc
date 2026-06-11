package com.system.modules.controlobras.dataproviders.jpa;

import java.util.ArrayList;
import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsordmdactamodificaciondetalleDto;
import com.system.crosscutting.domain.model.EntyOrsordmdactamodificaciondetalleResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsordmdactamodificaciondetalle;
import com.system.crosscutting.persistence.repository.EntyOrsordmdactamodificaciondetalleRepository;
import com.system.modules.controlobras.contracts.IjpaActaModificacionDetalleDataProviders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaActaModificacionDetalleDataProviders
        extends JpaDataProviderSupport
        implements IjpaActaModificacionDetalleDataProviders {

    @Autowired
    private EntyOrsordmdactamodificaciondetalleRepository repository;

    @Override
    public EntyOrsordmdactamodificaciondetalleResponse getAll() throws EBusinessException {
        try {
            EntyOrsordmdactamodificaciondetalleResponse response =
                    new EntyOrsordmdactamodificaciondetalleResponse();

            response.setRspMessage("Detalles de acta de modificación consultados correctamente");
            response.setRspValue("OK");
            response.setRspData(translateList(repository.findAll()));
            return response;
        } catch (Exception e) {
            throw buildException("Error consultando detalles de acta de modificación", e);
        }
    }

    @Override
    public EntyOrsordmdactamodificaciondetalleResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        try {
            Pageable pageable = PageRequest.of(
                    safeCurrentPage(currentPage),
                    safePageSize(pageSize),
                    Sort.by(Sort.Direction.DESC, "orsPrimarykeyAcmd")
            );

            Page<EntyOrsordmdactamodificaciondetalle> page;
            String param = safeParameter(parameter);
            String value = safeFilter(filter);

            switch (param) {
                case "ID":
                case "KEY":
                    page = repository.searchByIdentifKey(value, pageable);
                    break;

                case "ACTA":
                case "ACMO":
                    page = repository.searchByActa(value, pageable);
                    break;

                case "ORDEN":
                    page = repository.searchByOrden(value, pageable);
                    break;

                case "RSEQ":
                case "RESUMEN_EQUIPO":
                    page = repository.searchByResumenEquipo(value, pageable);
                    break;

                case "TIPO_EQUIPO":
                    page = repository.searchByTipoEquipo(value, pageable);
                    break;

                case "ESTADO":
                    page = repository.searchByStatus(value, pageable);
                    break;

                case "TEXT":
                default:
                    page = repository.searchByText(value, pageable);
                    break;
            }

            EntyOrsordmdactamodificaciondetalleResponse response =
                    new EntyOrsordmdactamodificaciondetalleResponse();

            response.setRspMessage("Detalles de acta de modificación consultados correctamente");
            response.setRspValue("OK");
            response.setRspData(translateList(page.getContent()));
            response.setRspPagination(buildPagination(currentPage, pageSize, page));
            return response;

        } catch (Exception e) {
            throw buildException("Error consultando detalles de acta de modificación paginados", e);
        }
    }

    @Override
    public EntyOrsordmdactamodificaciondetalleDto get(final Integer id) throws EBusinessException {
        try {
            EntyOrsordmdactamodificaciondetalle entity = repository.findById(id)
                    .orElseThrow(() -> buildException(
                            "No existe detalle de acta de modificación con id: " + id,
                            null
                    ));

            return toDto(entity, EntyOrsordmdactamodificaciondetalleDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error consultando detalle de acta de modificación por id", e);
        }
    }

    @Override
    public EntyOrsordmdactamodificaciondetalleDto save(
            final EntyOrsordmdactamodificaciondetalleDto dto
    ) throws EBusinessException {
        try {
            calcularValores(dto);

            EntyOrsordmdactamodificaciondetalle entity =
                    toEntity(dto, EntyOrsordmdactamodificaciondetalle.class);

            EntyOrsordmdactamodificaciondetalle saved = repository.save(entity);

            return toDto(saved, EntyOrsordmdactamodificaciondetalleDto.class);

        } catch (Exception e) {
            throw buildException("Error guardando detalle de acta de modificación", e);
        }
    }

    @Override
    public List<EntyOrsordmdactamodificaciondetalleDto> save(
            final List<EntyOrsordmdactamodificaciondetalleDto> dtoList
    ) throws EBusinessException {
        try {
            List<EntyOrsordmdactamodificaciondetalleDto> result = new ArrayList<>();

            for (EntyOrsordmdactamodificaciondetalleDto dto : dtoList) {
                result.add(save(dto));
            }

            return result;

        } catch (Exception e) {
            throw buildException("Error guardando lista de detalles de acta de modificación", e);
        }
    }

    @Override
    public EntyOrsordmdactamodificaciondetalleDto update(
            final Integer id,
            final EntyOrsordmdactamodificaciondetalleDto dto
    ) throws EBusinessException {
        try {
            EntyOrsordmdactamodificaciondetalle current = repository.findById(id)
                    .orElseThrow(() -> buildException(
                            "No existe detalle de acta de modificación con id: " + id,
                            null
                    ));

            calcularValores(dto);

            EntyOrsordmdactamodificaciondetalle entity =
                    toEntity(dto, EntyOrsordmdactamodificaciondetalle.class);

            entity.setOrsPrimarykeyAcmd(current.getOrsPrimarykeyAcmd());

            EntyOrsordmdactamodificaciondetalle saved = repository.save(entity);

            return toDto(saved, EntyOrsordmdactamodificaciondetalleDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error actualizando detalle de acta de modificación", e);
        }
    }

    @Override
    public void delete(final Integer id) throws EBusinessException {
        try {
            if (!repository.existsById(id)) {
                throw buildException("No existe detalle de acta de modificación con id: " + id, null);
            }

            repository.deleteById(id);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error eliminando detalle de acta de modificación", e);
        }
    }

    @Override
    public EntyOrsordmdactamodificaciondetalleDto changestatus(
            final Integer id,
            final String status
    ) throws EBusinessException {
        try {
            EntyOrsordmdactamodificaciondetalle entity = repository.findById(id)
                    .orElseThrow(() -> buildException(
                            "No existe detalle de acta de modificación con id: " + id,
                            null
                    ));

            entity.setOrsEstadoregAcmd(status);

            EntyOrsordmdactamodificaciondetalle saved = repository.save(entity);

            return toDto(saved, EntyOrsordmdactamodificaciondetalleDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error cambiando estado del detalle de acta de modificación", e);
        }
    }

    @Override
    public EntyOrsordmdactamodificaciondetalleDto findByKey(
            final String detalleActaModificacionKey
    ) throws EBusinessException {
        try {
            EntyOrsordmdactamodificaciondetalle entity =
                    repository.findByOrsIdentifkeyAcmd(detalleActaModificacionKey)
                            .orElseThrow(() -> buildException(
                                    "No existe detalle de acta de modificación con key: "
                                            + detalleActaModificacionKey,
                                    null
                            ));

            return toDto(entity, EntyOrsordmdactamodificaciondetalleDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error consultando detalle de acta por key", e);
        }
    }

    @Override
    public List<EntyOrsordmdactamodificaciondetalleDto> findByActa(
            final String actaModificacionKey
    ) throws EBusinessException {
        try {
            return translateList(repository.findByOrsIdentifkeyAcmo(actaModificacionKey));
        } catch (Exception e) {
            throw buildException("Error consultando detalles por acta", e);
        }
    }

    @Override
    public List<EntyOrsordmdactamodificaciondetalleDto> findByOrden(final String ordenKey)
            throws EBusinessException {
        try {
            return translateList(repository.findByOrsIdentifkeyOrde(ordenKey));
        } catch (Exception e) {
            throw buildException("Error consultando detalles de acta por orden", e);
        }
    }

    @Override
    public List<EntyOrsordmdactamodificaciondetalleDto> findByResumenEquipo(
            final String resumenEquipoKey
    ) throws EBusinessException {
        try {
            return translateList(repository.findByOrsIdentifkeyRseq(resumenEquipoKey));
        } catch (Exception e) {
            throw buildException("Error consultando detalles de acta por resumen de equipo", e);
        }
    }

    @Override
    public List<EntyOrsordmdactamodificaciondetalleDto> findByTipoEquipo(
            final String tipoEquipoKey
    ) throws EBusinessException {
        try {
            return translateList(repository.findByPrvTipoequipoTieq(tipoEquipoKey));
        } catch (Exception e) {
            throw buildException("Error consultando detalles de acta por tipo de equipo", e);
        }
    }

    @Override
    public List<EntyOrsordmdactamodificaciondetalleDto> findByEstado(final String estado)
            throws EBusinessException {
        try {
            return translateList(repository.findByOrsEstadoregAcmd(estado));
        } catch (Exception e) {
            throw buildException("Error consultando detalles de acta por estado", e);
        }
    }

    private void calcularValores(final EntyOrsordmdactamodificaciondetalleDto dto) {
        if (dto == null) {
            return;
        }

        if (dto.getOrsValororiginalAcmd() == null
                && dto.getOrsCantidadoriginalAcmd() != null
                && dto.getOrsValorunitarioAcmd() != null) {
            dto.setOrsValororiginalAcmd(
                    dto.getOrsCantidadoriginalAcmd().multiply(dto.getOrsValorunitarioAcmd())
            );
        }

        if (dto.getOrsValoranteriorAcmd() == null
                && dto.getOrsCantidadanteriorAcmd() != null
                && dto.getOrsValorunitarioAcmd() != null) {
            dto.setOrsValoranteriorAcmd(
                    dto.getOrsCantidadanteriorAcmd().multiply(dto.getOrsValorunitarioAcmd())
            );
        }

        if (dto.getOrsValormodificadoAcmd() == null
                && dto.getOrsCantidadmodificadaAcmd() != null
                && dto.getOrsValorunitarioAcmd() != null) {
            dto.setOrsValormodificadoAcmd(
                    dto.getOrsCantidadmodificadaAcmd().multiply(dto.getOrsValorunitarioAcmd())
            );
        }

        if (dto.getOrsValoractualizadoAcmd() == null
                && dto.getOrsCantidadactualizadaAcmd() != null
                && dto.getOrsValorunitarioAcmd() != null) {
            dto.setOrsValoractualizadoAcmd(
                    dto.getOrsCantidadactualizadaAcmd().multiply(dto.getOrsValorunitarioAcmd())
            );
        }

        if (dto.getOrsTiporegistAcmd() == null) {
            dto.setOrsTiporegistAcmd("1");
        }

        if (dto.getOrsEstadoregAcmd() == null) {
            dto.setOrsEstadoregAcmd("1");
        }
    }

    private List<EntyOrsordmdactamodificaciondetalleDto> translateList(
            final List<EntyOrsordmdactamodificaciondetalle> entities
    ) {
        List<EntyOrsordmdactamodificaciondetalleDto> result = new ArrayList<>();

        for (EntyOrsordmdactamodificaciondetalle entity : entities) {
            result.add(toDto(entity, EntyOrsordmdactamodificaciondetalleDto.class));
        }

        return result;
    }
}