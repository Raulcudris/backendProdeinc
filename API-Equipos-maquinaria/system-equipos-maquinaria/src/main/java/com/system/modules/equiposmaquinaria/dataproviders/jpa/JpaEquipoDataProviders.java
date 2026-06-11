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
    public EntyPrvinvmainventarioequiposResponse getAll()
            throws EBusinessException {
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
                    Integer id = parseInteger(search);
                    page = repository.searchByPrimaryKey(id, pageable);
                    break;

                case "KEY":
                    page = repository.searchByIdentifKey(search, pageable);
                    break;

                case "PROVEEDOR":
                    page = repository.searchByProveedor(search, pageable);
                    break;

                case "TIPO_EQUIPO":
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

            EntyPrvinvmainventarioequiposResponse response =
                    new EntyPrvinvmainventarioequiposResponse();

            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-equipos-maquinaria");
            response.setRspData(data);
            response.setRspPagination(buildPagination(pageNumber + 1, size, page));

            return response;

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando equipos", e);
        }
    }

    @Override
    public EntyPrvinvmainventarioequiposDto get(
            Integer id
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyPrvinvmainventarioequiposDto();
            }

            EntyPrvinvmainventarioequipos entity = repository.findById(id)
                    .orElse(null);

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
            if (dto.getPrvIdentifkeyInve() == null ||
                    dto.getPrvIdentifkeyInve().isBlank()) {
                return new EntyPrvinvmainventarioequiposDto();
            }

            dto.setPrvPrimarykeyInve(null);
            normalizeDto(dto);

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
            if (id == null) {
                return new EntyPrvinvmainventarioequiposDto();
            }

            EntyPrvinvmainventarioequipos current = repository.findById(id)
                    .orElse(null);

            if (current == null) {
                return new EntyPrvinvmainventarioequiposDto();
            }

            dto.setPrvPrimarykeyInve(id);

            if (dto.getPrvIdentifkeyInve() == null ||
                    dto.getPrvIdentifkeyInve().isBlank()) {
                dto.setPrvIdentifkeyInve(current.getPrvIdentifkeyInve());
            }

            if (dto.getPrvIdentifkeyMprv() == null ||
                    dto.getPrvIdentifkeyMprv().isBlank()) {
                dto.setPrvIdentifkeyMprv(current.getPrvIdentifkeyMprv());
            }

            if (dto.getPrvTipoequipoTieq() == null ||
                    dto.getPrvTipoequipoTieq().isBlank()) {
                dto.setPrvTipoequipoTieq(current.getPrvTipoequipoTieq());
            }

            if (dto.getPrvNombrequipoInve() == null ||
                    dto.getPrvNombrequipoInve().isBlank()) {
                dto.setPrvNombrequipoInve(current.getPrvNombrequipoInve());
            }

            if (dto.getPrvRefermodeloInve() == null ||
                    dto.getPrvRefermodeloInve().isBlank()) {
                dto.setPrvRefermodeloInve(current.getPrvRefermodeloInve());
            }

            if (dto.getPrvEquipoestadoInve() == null ||
                    dto.getPrvEquipoestadoInve().isBlank()) {
                dto.setPrvEquipoestadoInve(current.getPrvEquipoestadoInve());
            }

            if (dto.getPrvEquipoactivoInve() == null ||
                    dto.getPrvEquipoactivoInve().isBlank()) {
                dto.setPrvEquipoactivoInve(current.getPrvEquipoactivoInve());
            }

            if (dto.getPrvEstadoregInve() == null ||
                    dto.getPrvEstadoregInve().isBlank()) {
                dto.setPrvEstadoregInve(current.getPrvEstadoregInve());
            }

            if (dto.getPrvDescripcionInve() == null ||
                    dto.getPrvDescripcionInve().isBlank()) {
                dto.setPrvDescripcionInve(current.getPrvDescripcionInve());
            }

            normalizeDto(dto);

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
            if (id == null || !repository.existsById(id)) {
                return;
            }

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
            if (equipoKey == null || equipoKey.isBlank()) {
                return new EntyPrvinvmainventarioequiposDto();
            }

            String key = equipoKey.trim().toUpperCase();

            EntyPrvinvmainventarioequipos entity = repository
                    .findByPrvIdentifkeyInve(key)
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
            if (proveedorKey == null || proveedorKey.isBlank()) {
                return new ArrayList<>();
            }

            String key = proveedorKey.trim().toUpperCase();

            return translateList(repository.findByPrvIdentifkeyMprv(key));

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando equipos por proveedor", e);
        }
    }

    @Override
    public List<EntyPrvinvmainventarioequiposDto> findByTipoEquipo(
            String tipoEquipoKey
    ) throws EBusinessException {
        try {
            if (tipoEquipoKey == null || tipoEquipoKey.isBlank()) {
                return new ArrayList<>();
            }

            String key = tipoEquipoKey.trim().toUpperCase();

            return translateList(repository.findByPrvTipoequipoTieq(key));

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando equipos por tipo de equipo", e);
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

    private void normalizeDto(
            EntyPrvinvmainventarioequiposDto dto
    ) {
        if (dto.getPrvIdentifkeyInve() != null) {
            dto.setPrvIdentifkeyInve(dto.getPrvIdentifkeyInve().trim().toUpperCase());
        }

        if (dto.getPrvIdentifkeyMprv() != null &&
                !dto.getPrvIdentifkeyMprv().isBlank()) {
            dto.setPrvIdentifkeyMprv(dto.getPrvIdentifkeyMprv().trim().toUpperCase());
        }

        if (dto.getPrvTipoequipoTieq() != null &&
                !dto.getPrvTipoequipoTieq().isBlank()) {
            dto.setPrvTipoequipoTieq(dto.getPrvTipoequipoTieq().trim().toUpperCase());
        }

        if (dto.getPrvNombrequipoInve() == null ||
                dto.getPrvNombrequipoInve().isBlank()) {
            dto.setPrvNombrequipoInve("Sin nombre");
        }

        if (dto.getPrvRefermodeloInve() == null ||
                dto.getPrvRefermodeloInve().isBlank()) {
            dto.setPrvRefermodeloInve("Sin referencia");
        }

        if (dto.getPrvEquipoestadoInve() == null ||
                dto.getPrvEquipoestadoInve().isBlank()) {
            dto.setPrvEquipoestadoInve("OPE");
        }

        if (dto.getPrvEquipoactivoInve() == null ||
                dto.getPrvEquipoactivoInve().isBlank()) {
            dto.setPrvEquipoactivoInve("1");
        }

        if (dto.getPrvEstadoregInve() == null ||
                dto.getPrvEstadoregInve().isBlank()) {
            dto.setPrvEstadoregInve("1");
        }

        if (dto.getPrvDescripcionInve() == null ||
                dto.getPrvDescripcionInve().isBlank()) {
            dto.setPrvDescripcionInve("Sin descripcion");
        }
    }
}