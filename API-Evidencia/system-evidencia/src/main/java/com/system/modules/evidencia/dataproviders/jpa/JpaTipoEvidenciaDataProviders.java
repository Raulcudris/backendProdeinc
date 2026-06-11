package com.system.modules.evidencia.dataproviders.jpa;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.PersistenceException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyEvitipmatipoevidencia;
import com.system.crosscutting.persistence.repository.EntyEvitipmatipoevidenciaRepository;
import com.system.modules.evidencia.dataproviders.IjpaTipoEvidenciaDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaTipoEvidenciaDataProviders extends JpaDataProviderSupport
        implements IjpaTipoEvidenciaDataProviders {

    private final EntyEvitipmatipoevidenciaRepository repository;

    @Override
    public EntyEvitipmatipoevidenciaResponse getAll()
            throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyEvitipmatipoevidenciaResponse getAll(
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
            Page<EntyEvitipmatipoevidencia> page;

            switch (parameterValue) {
                case "ID":
                    page = repository.searchByPrimaryKey(parseInteger(search), pageable);
                    break;

                case "KEY":
                case "TIPO":
                case "TIPO_EVIDENCIA":
                    page = repository.searchByIdentifKey(search, pageable);
                    break;

                case "STATUS":
                case "ESTADO":
                    page = repository.searchByStatus(search, pageable);
                    break;

                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyEvitipmatipoevidenciaDto> data = page.getContent()
                    .stream()
                    .map(entity -> toDto(entity, EntyEvitipmatipoevidenciaDto.class))
                    .collect(Collectors.toList());

            return buildResponse(
                    data,
                    buildPagination(pageNumber + 1, size, page)
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando tipos de evidencia.", e);
        }
    }

    @Override
    public EntyEvitipmatipoevidenciaDto get(final Integer id)
            throws EBusinessException {
        try {
            EntyEvitipmatipoevidencia entity = repository.findById(id).orElse(null);

            return entity == null
                    ? new EntyEvitipmatipoevidenciaDto()
                    : toDto(entity, EntyEvitipmatipoevidenciaDto.class);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando tipo de evidencia.", e);
        }
    }

    @Override
    public EntyEvitipmatipoevidenciaDto save(
            final EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException {
        try {
            prepareBeforeSave(dto, true);

            EntyEvitipmatipoevidencia entity = toEntity(
                    dto,
                    EntyEvitipmatipoevidencia.class
            );

            EntyEvitipmatipoevidencia saved = repository.save(entity);

            return toDto(saved, EntyEvitipmatipoevidenciaDto.class);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error creando tipo de evidencia.", e);
        }
    }

    @Override
    public List<EntyEvitipmatipoevidenciaDto> save(
            final List<EntyEvitipmatipoevidenciaDto> dtos
    ) throws EBusinessException {
        List<EntyEvitipmatipoevidenciaDto> result = new ArrayList<>();

        if (dtos == null || dtos.isEmpty()) {
            return result;
        }

        for (EntyEvitipmatipoevidenciaDto dto : dtos) {
            result.add(save(dto));
        }

        return result;
    }

    @Override
    public EntyEvitipmatipoevidenciaDto update(
            final Integer id,
            final EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException {
        try {
            EntyEvitipmatipoevidencia current = repository.findById(id).orElse(null);

            if (current == null) {
                return new EntyEvitipmatipoevidenciaDto();
            }

            prepareBeforeSave(dto, false);

            Integer primaryKey = current.getEviPrimarykeyTiev();

            BeanUtils.copyProperties(dto, current);
            current.setEviPrimarykeyTiev(primaryKey);

            EntyEvitipmatipoevidencia saved = repository.save(current);

            return toDto(saved, EntyEvitipmatipoevidenciaDto.class);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error actualizando tipo de evidencia.", e);
        }
    }

    @Override
    public void delete(final Integer id) throws EBusinessException {
        try {
            repository.deleteById(id);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error eliminando tipo de evidencia.", e);
        }
    }

    @Override
    public EntyEvitipmatipoevidenciaDto findByKey(
            final String tipoEvidenciaKey
    ) throws EBusinessException {
        try {
            EntyEvitipmatipoevidencia entity = repository
                    .findByEviIdentifkeyTiev(tipoEvidenciaKey)
                    .orElse(null);

            return entity == null
                    ? new EntyEvitipmatipoevidenciaDto()
                    : toDto(entity, EntyEvitipmatipoevidenciaDto.class);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando tipo de evidencia por key.", e);
        }
    }

    @Override
    public EntyEvitipmatipoevidenciaResponse findByEstado(
            final String estado
    ) throws EBusinessException {
        try {
            List<EntyEvitipmatipoevidenciaDto> data =
                    repository.findByEviEstadoregTiev(estado)
                            .stream()
                            .map(entity -> toDto(entity, EntyEvitipmatipoevidenciaDto.class))
                            .collect(Collectors.toList());

            return buildResponse(data, null);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando tipos de evidencia por estado.", e);
        }
    }

    @Override
    public EntyEvitipmatipoevidenciaDto changestatus(
            final Integer id,
            final String estado
    ) throws EBusinessException {
        try {
            EntyEvitipmatipoevidencia entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyEvitipmatipoevidenciaDto();
            }

            entity.setEviEstadoregTiev(estado);

            EntyEvitipmatipoevidencia saved = repository.save(entity);

            return toDto(saved, EntyEvitipmatipoevidenciaDto.class);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error cambiando estado de tipo de evidencia.", e);
        }
    }

    private void prepareBeforeSave(
            final EntyEvitipmatipoevidenciaDto dto,
            final boolean isCreate
    ) {
        if (isCreate) {
            dto.setEviPrimarykeyTiev(null);
        }

        if (dto.getEviTiporegistTiev() == null
                || dto.getEviTiporegistTiev().isBlank()) {
            dto.setEviTiporegistTiev("1");
        }

        if (dto.getEviEstadoregTiev() == null
                || dto.getEviEstadoregTiev().isBlank()) {
            dto.setEviEstadoregTiev("1");
        }
    }

    private EntyEvitipmatipoevidenciaResponse buildResponse(
            final List<EntyEvitipmatipoevidenciaDto> data,
            final PaginationResponse pagination
    ) {
        EntyEvitipmatipoevidenciaResponse response =
                new EntyEvitipmatipoevidenciaResponse();

        response.setRspMessage("OK");
        response.setRspValue("OK");
        response.setRspParentKey("NA");
        response.setRspAppKey("msvc-evidencias");
        response.setRspData(data);
        response.setRspPagination(pagination);

        return response;
    }
}