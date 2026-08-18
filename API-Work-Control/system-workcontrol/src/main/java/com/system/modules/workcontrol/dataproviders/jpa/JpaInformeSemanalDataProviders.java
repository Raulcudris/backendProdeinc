package com.system.modules.workcontrol.dataproviders.jpa;

import java.util.ArrayList;
import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsplamainformesemanalDto;
import com.system.crosscutting.domain.model.EntyOrsplamainformesemanalResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsplamainformesemanal;
import com.system.crosscutting.persistence.repository.EntyOrsplamainformesemanalRepository;
import com.system.modules.workcontrol.contracts.IjpaInformeSemanalDataProviders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaInformeSemanalDataProviders
        extends JpaDataProviderSupport
        implements IjpaInformeSemanalDataProviders {

    @Autowired
    private EntyOrsplamainformesemanalRepository repository;

    @Override
    public EntyOrsplamainformesemanalResponse getAll() throws EBusinessException {
        try {
            EntyOrsplamainformesemanalResponse response = new EntyOrsplamainformesemanalResponse();
            response.setRspMessage("Informes semanales consultados correctamente");
            response.setRspValue("OK");
            response.setRspData(translateList(repository.findAll()));
            return response;
        } catch (Exception e) {
            throw buildException("Error consultando informes semanales", e);
        }
    }

    @Override
    public EntyOrsplamainformesemanalResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        try {
            Pageable pageable = PageRequest.of(
                    safeCurrentPage(currentPage),
                    safePageSize(pageSize),
                    Sort.by(Sort.Direction.DESC, "orsPrimarykeyInse")
            );

            Page<EntyOrsplamainformesemanal> page;
            String param = safeParameter(parameter);
            String value = safeFilter(filter);

            switch (param) {
                case "ID":
                case "KEY":
                    page = repository.searchByIdentifKey(value, pageable);
                    break;

                case "ORDEN":
                    page = repository.searchByOrden(value, pageable);
                    break;

                case "PSEM":
                case "PROYECCION":
                    page = repository.searchByProyeccionSemana(value, pageable);
                    break;

                case "SEMANA":
                    page = repository.searchBySemana(parseInteger(value), pageable);
                    break;

                case "ESTADO_AVANCE":
                    page = repository.searchByEstadoAvance(value, pageable);
                    break;

                case "ESTADO":
                    page = repository.searchByStatus(value, pageable);
                    break;

                case "TEXT":
                default:
                    page = repository.searchByText(value, pageable);
                    break;
            }

            EntyOrsplamainformesemanalResponse response = new EntyOrsplamainformesemanalResponse();
            response.setRspMessage("Informes semanales consultados correctamente");
            response.setRspValue("OK");
            response.setRspData(translateList(page.getContent()));
            response.setRspPagination(buildPagination(currentPage, pageSize, page));
            return response;

        } catch (Exception e) {
            throw buildException("Error consultando informes semanales paginados", e);
        }
    }

    @Override
    public EntyOrsplamainformesemanalDto get(final Integer id) throws EBusinessException {
        try {
            EntyOrsplamainformesemanal entity = repository.findById(id)
                    .orElseThrow(() -> buildException("No existe informe semanal con id: " + id, null));

            return toDto(entity, EntyOrsplamainformesemanalDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error consultando informe semanal por id", e);
        }
    }

    @Override
    public EntyOrsplamainformesemanalDto save(final EntyOrsplamainformesemanalDto dto)
            throws EBusinessException {
        try {
            aplicarDefaults(dto);

            EntyOrsplamainformesemanal entity =
                    toEntity(dto, EntyOrsplamainformesemanal.class);

            EntyOrsplamainformesemanal saved = repository.save(entity);

            return toDto(saved, EntyOrsplamainformesemanalDto.class);

        } catch (Exception e) {
            throw buildException("Error guardando informe semanal", e);
        }
    }

    @Override
    public List<EntyOrsplamainformesemanalDto> save(
            final List<EntyOrsplamainformesemanalDto> dtoList
    ) throws EBusinessException {
        try {
            List<EntyOrsplamainformesemanalDto> result = new ArrayList<>();

            for (EntyOrsplamainformesemanalDto dto : dtoList) {
                result.add(save(dto));
            }

            return result;

        } catch (Exception e) {
            throw buildException("Error guardando lista de informes semanales", e);
        }
    }

    @Override
    public EntyOrsplamainformesemanalDto update(
            final Integer id,
            final EntyOrsplamainformesemanalDto dto
    ) throws EBusinessException {
        try {
            EntyOrsplamainformesemanal current = repository.findById(id)
                    .orElseThrow(() -> buildException("No existe informe semanal con id: " + id, null));

            aplicarDefaults(dto);

            EntyOrsplamainformesemanal entity =
                    toEntity(dto, EntyOrsplamainformesemanal.class);

            entity.setOrsPrimarykeyInse(current.getOrsPrimarykeyInse());

            EntyOrsplamainformesemanal saved = repository.save(entity);

            return toDto(saved, EntyOrsplamainformesemanalDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error actualizando informe semanal", e);
        }
    }

    @Override
    public void delete(final Integer id) throws EBusinessException {
        try {
            if (!repository.existsById(id)) {
                throw buildException("No existe informe semanal con id: " + id, null);
            }

            repository.deleteById(id);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error eliminando informe semanal", e);
        }
    }

    @Override
    public EntyOrsplamainformesemanalDto changestatus(
            final Integer id,
            final String status
    ) throws EBusinessException {
        try {
            EntyOrsplamainformesemanal entity = repository.findById(id)
                    .orElseThrow(() -> buildException("No existe informe semanal con id: " + id, null));

            entity.setOrsEstadoregInse(status);

            EntyOrsplamainformesemanal saved = repository.save(entity);

            return toDto(saved, EntyOrsplamainformesemanalDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error cambiando estado del informe semanal", e);
        }
    }

    @Override
    public EntyOrsplamainformesemanalDto findByKey(final String informeSemanalKey)
            throws EBusinessException {
        try {
            EntyOrsplamainformesemanal entity = repository.findByOrsIdentifkeyInse(informeSemanalKey)
                    .orElseThrow(() -> buildException(
                            "No existe informe semanal con key: " + informeSemanalKey,
                            null
                    ));

            return toDto(entity, EntyOrsplamainformesemanalDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error consultando informe semanal por key", e);
        }
    }

    @Override
    public List<EntyOrsplamainformesemanalDto> findByOrden(final String ordenKey)
            throws EBusinessException {
        try {
            return translateList(repository.findByOrsIdentifkeyOrde(ordenKey));
        } catch (Exception e) {
            throw buildException("Error consultando informes semanales por orden", e);
        }
    }

    @Override
    public List<EntyOrsplamainformesemanalDto> findByProyeccionSemana(
            final String proyeccionSemanaKey
    ) throws EBusinessException {
        try {
            return translateList(repository.findByOrsIdentifkeyPsem(proyeccionSemanaKey));
        } catch (Exception e) {
            throw buildException("Error consultando informes semanales por proyección semanal", e);
        }
    }

    @Override
    public List<EntyOrsplamainformesemanalDto> findBySemana(final Integer semana)
            throws EBusinessException {
        try {
            return translateList(repository.findByOrsSemanaInse(semana));
        } catch (Exception e) {
            throw buildException("Error consultando informes semanales por semana", e);
        }
    }

    @Override
    public List<EntyOrsplamainformesemanalDto> findByEstado(final String estado)
            throws EBusinessException {
        try {
            return translateList(repository.findByOrsEstadoregInse(estado));
        } catch (Exception e) {
            throw buildException("Error consultando informes semanales por estado", e);
        }
    }

    private void aplicarDefaults(final EntyOrsplamainformesemanalDto dto) {
        if (dto == null) {
            return;
        }

        if (dto.getOrsEstadoavanceInse() == null) {
            dto.setOrsEstadoavanceInse("SIN_AVANCE");
        }

        if (dto.getOrsTiporegistInse() == null) {
            dto.setOrsTiporegistInse("1");
        }

        if (dto.getOrsEstadoregInse() == null) {
            dto.setOrsEstadoregInse("1");
        }
    }

    private List<EntyOrsplamainformesemanalDto> translateList(
            final List<EntyOrsplamainformesemanal> entities
    ) {
        List<EntyOrsplamainformesemanalDto> result = new ArrayList<>();

        for (EntyOrsplamainformesemanal entity : entities) {
            result.add(toDto(entity, EntyOrsplamainformesemanalDto.class));
        }

        return result;
    }
}