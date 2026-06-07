package com.system.modules.equiposmaquinaria.dataproviders.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyPrvinvmainventarioequiposDto;
import com.system.crosscutting.domain.model.EntyPrvinvmainventarioequiposResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyPrvinvmainventarioequipos;
import com.system.crosscutting.persistence.repository.EntyPrvinvmainventarioequiposRepository;
import com.system.crosscutting.translate.EntyPrvinvmainventarioequiposDtoToEntityTranslate;
import com.system.crosscutting.translate.EntyPrvinvmainventarioequiposEntityToDtoTranslate;
import com.system.modules.equiposmaquinaria.dataproviders.IjpaEquipoDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaEquipoDataProviders extends JpaDataProviderSupport
        implements IjpaEquipoDataProviders {

    private final EntyPrvinvmainventarioequiposRepository repository;
    private final EntyPrvinvmainventarioequiposDtoToEntityTranslate dtoToEntityTranslate;
    private final EntyPrvinvmainventarioequiposEntityToDtoTranslate entityToDtoTranslate;

    @Override
    public EntyPrvinvmainventarioequiposResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyPrvinvmainventarioequiposResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        try {
            int pageNumber = safeCurrentPage(currentPage);
            int size = safePageSize(pageSize);
            String search = safeFilter(filter);

            Pageable pageable = PageRequest.of(pageNumber, size);
            Page<EntyPrvinvmainventarioequipos> page;

            switch (safeParameter(parameter)) {
                case "ID":
                    page = repository.searchByPrimaryKey(parseInteger(search), pageable);
                    break;

                case "KEY":
                    page = repository.searchByIdentifKey(search, pageable);
                    break;

                case "PROVEEDOR":
                    page = repository.searchByProveedor(search, pageable);
                    break;

                case "TIPO":
                    page = repository.searchByTipoEquipo(search, pageable);
                    break;

                case "DISPONIBLE":
                    page = repository.searchByDisponible(search, pageable);
                    break;

                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;

                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyPrvinvmainventarioequiposDto> data = new ArrayList<>();

            for (EntyPrvinvmainventarioequipos entity : page.getContent()) {
                data.add(entityToDtoTranslate.translate(entity));
            }

            EntyPrvinvmainventarioequiposResponse response = new EntyPrvinvmainventarioequiposResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-equipos-maquinaria");
            response.setRspData(data);
            response.setRspPagination(buildPagination(pageNumber + 1, size, page));

            return response;

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando inventario de equipos", e);
        }
    }

    @Override
    public EntyPrvinvmainventarioequiposDto get(
            Integer id
    ) throws EBusinessException {
        try {
            EntyPrvinvmainventarioequipos entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyPrvinvmainventarioequiposDto();
            }

            return entityToDtoTranslate.translate(entity);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando equipo", e);
        }
    }

    @Override
    public EntyPrvinvmainventarioequiposDto save(
            EntyPrvinvmainventarioequiposDto dto
    ) throws EBusinessException {
        try {
            dto.setPrvPrimarykeyInve(null);

            if (dto.getPrvEquipoestadoInve() == null || dto.getPrvEquipoestadoInve().isBlank()) {
                dto.setPrvEquipoestadoInve("A01");
            }

            if (dto.getPrvEquipoactivoInve() == null || dto.getPrvEquipoactivoInve().isBlank()) {
                dto.setPrvEquipoactivoInve("1");
            }

            if (dto.getPrvEstadoregInve() == null || dto.getPrvEstadoregInve().isBlank()) {
                dto.setPrvEstadoregInve("1");
            }

            EntyPrvinvmainventarioequipos entity = dtoToEntityTranslate.translate(dto);
            EntyPrvinvmainventarioequipos saved = repository.save(entity);

            return entityToDtoTranslate.translate(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error creando equipo", e);
        }
    }

    @Override
    public List<EntyPrvinvmainventarioequiposDto> save(
            List<EntyPrvinvmainventarioequiposDto> dtos
    ) throws EBusinessException {
        List<EntyPrvinvmainventarioequiposDto> result = new ArrayList<>();

        for (EntyPrvinvmainventarioequiposDto dto : dtos) {
            result.add(save(dto));
        }

        return result;
    }

    @Override
    public EntyPrvinvmainventarioequiposDto update(
            Integer id,
            EntyPrvinvmainventarioequiposDto dto
    ) throws EBusinessException {
        try {
            EntyPrvinvmainventarioequipos old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyPrvinvmainventarioequiposDto();
            }

            dto.setPrvPrimarykeyInve(id);

            if (dto.getPrvEquipoestadoInve() == null || dto.getPrvEquipoestadoInve().isBlank()) {
                dto.setPrvEquipoestadoInve("A01");
            }

            if (dto.getPrvEquipoactivoInve() == null || dto.getPrvEquipoactivoInve().isBlank()) {
                dto.setPrvEquipoactivoInve("1");
            }

            if (dto.getPrvEstadoregInve() == null || dto.getPrvEstadoregInve().isBlank()) {
                dto.setPrvEstadoregInve("1");
            }

            EntyPrvinvmainventarioequipos entity = dtoToEntityTranslate.translate(dto);
            EntyPrvinvmainventarioequipos saved = repository.save(entity);

            return entityToDtoTranslate.translate(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error actualizando equipo", e);
        }
    }

    @Override
    public void delete(
            Integer id
    ) throws EBusinessException {
        try {
            repository.deleteById(id);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error eliminando equipo", e);
        }
    }

    @Override
    public EntyPrvinvmainventarioequiposDto findByKey(
            String equipoKey
    ) throws EBusinessException {
        try {
            EntyPrvinvmainventarioequipos entity = repository.findByPrvIdentifkeyInve(equipoKey)
                    .orElse(null);

            if (entity == null) {
                return new EntyPrvinvmainventarioequiposDto();
            }

            return entityToDtoTranslate.translate(entity);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando equipo por key", e);
        }
    }

    @Override
    public List<EntyPrvinvmainventarioequiposDto> findByProveedor(
            String proveedorKey
    ) throws EBusinessException {
        try {
            return translateList(repository.findByPrvIdentifkeyMprv(proveedorKey));

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando equipos por proveedor", e);
        }
    }

    @Override
    public List<EntyPrvinvmainventarioequiposDto> findByTipoEquipo(
            String tipoEquipoKey
    ) throws EBusinessException {
        try {
            return translateList(repository.findByPrvTipoequipoTieq(tipoEquipoKey));

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando equipos por tipo", e);
        }
    }

    @Override
    public List<EntyPrvinvmainventarioequiposDto> findByDisponible(
            String disponible
    ) throws EBusinessException {
        try {
            return translateList(repository.findByPrvEquipoactivoInve(disponible));

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando equipos por disponibilidad", e);
        }
    }

    @Override
    public List<EntyPrvinvmainventarioequiposDto> findByEstado(
            String estado
    ) throws EBusinessException {
        try {
            return translateList(repository.findByPrvEstadoregInve(estado));

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando equipos por estado", e);
        }
    }

    private List<EntyPrvinvmainventarioequiposDto> translateList(
            List<EntyPrvinvmainventarioequipos> entities
    ) {
        return entities.stream()
                .map(entity -> {
                    try {
                        return entityToDtoTranslate.translate(entity);
                    } catch (EBusinessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
}