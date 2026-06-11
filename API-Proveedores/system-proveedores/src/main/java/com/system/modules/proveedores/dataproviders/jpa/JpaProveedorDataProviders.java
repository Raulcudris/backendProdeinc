package com.system.modules.proveedores.dataproviders.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaDto;
import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyPrvmaeproveedoresma;
import com.system.crosscutting.persistence.repository.EntyPrvmaeproveedoresmaRepository;
import com.system.modules.proveedores.dataproviders.IjpaProveedorDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaProveedorDataProviders extends JpaDataProviderSupport
        implements IjpaProveedorDataProviders {

    private final EntyPrvmaeproveedoresmaRepository repository;

    @Override
    public EntyPrvmaeproveedoresmaResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyPrvmaeproveedoresmaResponse getAll(
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
            Page<EntyPrvmaeproveedoresma> page;

            switch (safeParameter(parameter)) {
                case "ID":
                    page = repository.searchByPrimaryKey(parseInteger(search), pageable);
                    break;
                case "KEY":
                    page = repository.searchByIdentifKey(search, pageable);
                    break;
                case "NIT":
                    page = repository.searchByNit(search, pageable);
                    break;
                case "RAZON_SOCIAL":
                    page = repository.searchByRazonSocial(search, pageable);
                    break;
                case "CORREO":
                    page = repository.searchByCorreo(search, pageable);
                    break;
                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;
                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyPrvmaeproveedoresmaDto> data = page.getContent()
                    .stream()
                    .map(entity -> toDto(entity, EntyPrvmaeproveedoresmaDto.class))
                    .collect(Collectors.toList());

            EntyPrvmaeproveedoresmaResponse response = new EntyPrvmaeproveedoresmaResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-proveedores");
            response.setRspData(data);
            response.setRspPagination(buildPagination(pageNumber + 1, size, page));

            return response;

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando proveedores", e);
        }
    }

    @Override
    public EntyPrvmaeproveedoresmaDto get(
            Integer id
    ) throws EBusinessException {
        try {
            EntyPrvmaeproveedoresma entity = repository.findById(id).orElse(null);

            return entity == null
                    ? new EntyPrvmaeproveedoresmaDto()
                    : toDto(entity, EntyPrvmaeproveedoresmaDto.class);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando proveedor", e);
        }
    }

    @Override
    public EntyPrvmaeproveedoresmaDto save(
            EntyPrvmaeproveedoresmaDto dto
    ) throws EBusinessException {
        try {
            dto.setPrvPrimarykeyMprv(null);

            if (dto.getPrvEstadoregMprv() == null || dto.getPrvEstadoregMprv().isBlank()) {
                dto.setPrvEstadoregMprv("1");
            }

            EntyPrvmaeproveedoresma entity = toEntity(
                    dto,
                    EntyPrvmaeproveedoresma.class
            );

            return toDto(
                    repository.save(entity),
                    EntyPrvmaeproveedoresmaDto.class
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error creando proveedor", e);
        }
    }

    @Override
    public List<EntyPrvmaeproveedoresmaDto> save(
            List<EntyPrvmaeproveedoresmaDto> dtos
    ) throws EBusinessException {
        List<EntyPrvmaeproveedoresmaDto> result = new ArrayList<>();

        for (EntyPrvmaeproveedoresmaDto dto : dtos) {
            result.add(save(dto));
        }

        return result;
    }

    @Override
    public EntyPrvmaeproveedoresmaDto update(
            Integer id,
            EntyPrvmaeproveedoresmaDto dto
    ) throws EBusinessException {
        try {
            EntyPrvmaeproveedoresma old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyPrvmaeproveedoresmaDto();
            }

            dto.setPrvPrimarykeyMprv(id);
            BeanUtils.copyProperties(dto, old);

            return toDto(
                    repository.save(old),
                    EntyPrvmaeproveedoresmaDto.class
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error actualizando proveedor", e);
        }
    }

    @Override
    public void delete(
            Integer id
    ) throws EBusinessException {
        try {
            repository.deleteById(id);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error eliminando proveedor", e);
        }
    }

    @Override
    public EntyPrvmaeproveedoresmaDto findByKey(
            String proveedorKey
    ) throws EBusinessException {
        try {
            EntyPrvmaeproveedoresma entity = repository.findByPrvIdentifkeyMprv(proveedorKey)
                    .orElse(null);

            return entity == null
                    ? new EntyPrvmaeproveedoresmaDto()
                    : toDto(entity, EntyPrvmaeproveedoresmaDto.class);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando proveedor por key", e);
        }
    }

    @Override
    public EntyPrvmaeproveedoresmaDto findByNit(
            final String numeroNit
    ) throws EBusinessException {

        return repository.findByPrvNumeronitMprv(numeroNit)
                .map(entity -> toDto(entity, EntyPrvmaeproveedoresmaDto.class))
                .orElseGet(EntyPrvmaeproveedoresmaDto::new);
    }

    @Override
    public List<EntyPrvmaeproveedoresmaDto> findByEstado(
            String estado
    ) throws EBusinessException {
        try {
            return repository.findByPrvEstadoregMprv(estado)
                    .stream()
                    .map(entity -> toDto(entity, EntyPrvmaeproveedoresmaDto.class))
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando proveedores por estado", e);
        }
    }
}