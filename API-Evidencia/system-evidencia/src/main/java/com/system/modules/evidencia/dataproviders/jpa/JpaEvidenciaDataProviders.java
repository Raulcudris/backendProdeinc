package com.system.modules.evidencia.dataproviders.jpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyEvievimaevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvievimaevidenciaResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyEvievimaevidencia;
import com.system.crosscutting.persistence.repository.EntyEvievimaevidenciaRepository;
import com.system.modules.evidencia.dataproviders.IjpaEvidenciaDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaEvidenciaDataProviders extends JpaDataProviderSupport
        implements IjpaEvidenciaDataProviders {

    private final EntyEvievimaevidenciaRepository repository;

    @Override
    public EntyEvievimaevidenciaResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyEvievimaevidenciaResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        try {
            int pageNumber = safeCurrentPage(currentPage);
            int size = safePageSize(pageSize);
            String search = safeFilter(filter);
            String parameterValue = safeParameter(parameter);

            Pageable pageable = PageRequest.of(pageNumber, size);
            Page<EntyEvievimaevidencia> page;

            switch (parameterValue) {
                case "ID":
                    page = repository.searchByPrimaryKey(parseInteger(search), pageable);
                    break;

                case "KEY":
                case "EVIDENCIA":
                    page = repository.searchByIdentifKey(search, pageable);
                    break;

                case "TIPO":
                case "TIPO_EVIDENCIA":
                    page = repository.searchByTipo(search, pageable);
                    break;

                case "NOMBRE":
                case "ARCHIVO":
                    page = repository.searchByNombreArchivo(search, pageable);
                    break;

                case "STATUS":
                case "ESTADO":
                    page = repository.searchByStatus(search, pageable);
                    break;

                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyEvievimaevidenciaDto> data = page.getContent()
                    .stream()
                    .map(entity -> toDto(entity, EntyEvievimaevidenciaDto.class))
                    .collect(Collectors.toList());

            return buildResponse(
                    data,
                    buildPagination(pageNumber + 1, size, page)
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando evidencias.", e);
        }
    }

    @Override
    public EntyEvievimaevidenciaDto get(final Integer id)
            throws EBusinessException {
        try {
            EntyEvievimaevidencia entity = repository.findById(id).orElse(null);

            return entity == null
                    ? new EntyEvievimaevidenciaDto()
                    : toDto(entity, EntyEvievimaevidenciaDto.class);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando evidencia.", e);
        }
    }

    @Override
    public EntyEvievimaevidenciaDto save(
            final EntyEvievimaevidenciaDto dto
    ) throws EBusinessException {
        try {
            prepareBeforeSave(dto, true);

            EntyEvievimaevidencia entity = toEntity(
                    dto,
                    EntyEvievimaevidencia.class
            );

            EntyEvievimaevidencia saved = repository.save(entity);

            return toDto(saved, EntyEvievimaevidenciaDto.class);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error creando evidencia.", e);
        }
    }

    @Override
    public List<EntyEvievimaevidenciaDto> save(
            final List<EntyEvievimaevidenciaDto> dtos
    ) throws EBusinessException {
        List<EntyEvievimaevidenciaDto> result = new ArrayList<>();

        if (dtos == null || dtos.isEmpty()) {
            return result;
        }

        for (EntyEvievimaevidenciaDto dto : dtos) {
            result.add(save(dto));
        }

        return result;
    }

    @Override
    public EntyEvievimaevidenciaDto update(
            final Integer id,
            final EntyEvievimaevidenciaDto dto
    ) throws EBusinessException {
        try {
            EntyEvievimaevidencia current = repository.findById(id).orElse(null);

            if (current == null) {
                return new EntyEvievimaevidenciaDto();
            }

            prepareBeforeSave(dto, false);

            Integer primaryKey = current.getEviPrimarykeyEvid();

            BeanUtils.copyProperties(dto, current);
            current.setEviPrimarykeyEvid(primaryKey);

            EntyEvievimaevidencia saved = repository.save(current);

            return toDto(saved, EntyEvievimaevidenciaDto.class);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error actualizando evidencia.", e);
        }
    }

    @Override
    public void delete(final Integer id) throws EBusinessException {
        try {
            repository.deleteById(id);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error eliminando evidencia.", e);
        }
    }

    @Override
    public EntyEvievimaevidenciaDto findByKey(
            final String evidenciaKey
    ) throws EBusinessException {
        try {
            EntyEvievimaevidencia entity = repository
                    .findByEviIdentifkeyEvid(evidenciaKey)
                    .orElse(null);

            return entity == null
                    ? new EntyEvievimaevidenciaDto()
                    : toDto(entity, EntyEvievimaevidenciaDto.class);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando evidencia por key.", e);
        }
    }

    @Override
    public EntyEvievimaevidenciaResponse findByTipo(
            final String tipoKey
    ) throws EBusinessException {
        try {
            List<EntyEvievimaevidenciaDto> data =
                    repository.findByEviIdentifkeyTiev(tipoKey)
                            .stream()
                            .map(entity -> toDto(entity, EntyEvievimaevidenciaDto.class))
                            .collect(Collectors.toList());

            return buildResponse(data, null);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando evidencias por tipo.", e);
        }
    }

    @Override
    public EntyEvievimaevidenciaResponse findByEstado(
            final String estado
    ) throws EBusinessException {
        try {
            List<EntyEvievimaevidenciaDto> data =
                    repository.findByEviEstadoregEvid(estado)
                            .stream()
                            .map(entity -> toDto(entity, EntyEvievimaevidenciaDto.class))
                            .collect(Collectors.toList());

            return buildResponse(data, null);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando evidencias por estado.", e);
        }
    }

    @Override
    public EntyEvievimaevidenciaDto changestatus(
            final Integer id,
            final String estado
    ) throws EBusinessException {
        try {
            EntyEvievimaevidencia entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyEvievimaevidenciaDto();
            }

            entity.setEviEstadoregEvid(estado);

            EntyEvievimaevidencia saved = repository.save(entity);

            return toDto(saved, EntyEvievimaevidenciaDto.class);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error cambiando estado de evidencia.", e);
        }
    }

    private void prepareBeforeSave(
            final EntyEvievimaevidenciaDto dto,
            final boolean isCreate
    ) {
        if (isCreate) {
            dto.setEviPrimarykeyEvid(null);
        }

        if (dto.getEviFechacapturaEvid() == null) {
            dto.setEviFechacapturaEvid(LocalDate.now());
        }

        if (dto.getEviTiporegistEvid() == null
                || dto.getEviTiporegistEvid().isBlank()) {
            dto.setEviTiporegistEvid("1");
        }

        if (dto.getEviEstadoregEvid() == null
                || dto.getEviEstadoregEvid().isBlank()) {
            dto.setEviEstadoregEvid("1");
        }
    }

    private EntyEvievimaevidenciaResponse buildResponse(
            final List<EntyEvievimaevidenciaDto> data,
            final PaginationResponse pagination
    ) {
        EntyEvievimaevidenciaResponse response =
                new EntyEvievimaevidenciaResponse();

        response.setRspMessage("OK");
        response.setRspValue("OK");
        response.setRspParentKey("NA");
        response.setRspAppKey("msvc-evidencias");
        response.setRspData(data);
        response.setRspPagination(pagination);

        return response;
    }
}