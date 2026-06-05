package com.system.modules.proveedores.usecase;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaDto;
import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyPrvmaeproveedoresma;
import com.system.crosscutting.persistence.repository.EntyPrvmaeproveedoresmaRepository;
import com.system.crosscutting.translate.EntyPrvmaeproveedoresmaDtoToEntityTranslate;
import com.system.crosscutting.translate.EntyPrvmaeproveedoresmaEntityToDtoTranslate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final EntyPrvmaeproveedoresmaRepository repository;
    private final EntyPrvmaeproveedoresmaEntityToDtoTranslate entityToDtoTranslate;
    private final EntyPrvmaeproveedoresmaDtoToEntityTranslate dtoToEntityTranslate;

    public EntyPrvmaeproveedoresmaResponse findAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        try {
            int safeCurrentPage = currentPage <= 0 ? 0 : currentPage - 1;
            int safePageSize = pageSize <= 0 ? 10 : pageSize;

            String safeParameter = parameter == null || parameter.trim().isEmpty()
                    ? "TEXT"
                    : parameter.trim().toUpperCase();

            String safeFilter = filter == null ? "" : filter.trim();

            Pageable pageable = PageRequest.of(safeCurrentPage, safePageSize);
            Page<EntyPrvmaeproveedoresma> page;

            if (safeFilter.isEmpty()
                    && !"STATUS".equals(safeParameter)
                    && !"ESTADO".equals(safeParameter)) {
                page = repository.findAll(pageable);
            } else {
                switch (safeParameter) {
                    case "PKEY":
                        try {
                            page = repository.searchByPrimaryKey(Integer.parseInt(safeFilter), pageable);
                        } catch (NumberFormatException e) {
                            page = repository.searchByPrimaryKey(-1, pageable);
                        }
                        break;

                    case "CODIGO":
                    case "REGKEY":
                    case "IDENTIFKEY":
                    case "PROVEEDOR":
                        page = repository.searchByIdentifKey(safeFilter, pageable);
                        break;

                    case "NIT":
                        page = repository.searchByNit(safeFilter, pageable);
                        break;

                    case "TIPO":
                    case "TIPO_PROVEEDOR":
                        page = repository.searchByTipoProveedor(safeFilter, pageable);
                        break;

                    case "STATUS":
                    case "ESTADO":
                        if (safeFilter.isEmpty() || "ALL".equalsIgnoreCase(safeFilter)) {
                            page = repository.findAll(pageable);
                        } else {
                            page = repository.searchByStatus(safeFilter, pageable);
                        }
                        break;

                    case "TEXT":
                    case "SEARCH":
                    default:
                        page = repository.searchByText(safeFilter, pageable);
                        break;
                }
            }

            List<EntyPrvmaeproveedoresmaDto> data = page.getContent()
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            EntyPrvmaeproveedoresmaResponse response = new EntyPrvmaeproveedoresmaResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-proveedores");
            response.setRspData(data);
            response.setRspPagination(
                    PaginationResponse.builder()
                            .currentPage(safeCurrentPage + 1)
                            .totalPageSize(safePageSize)
                            .totalResults(page.getTotalElements())
                            .totalPages(page.getTotalPages())
                            .hasNextPage(page.hasNext())
                            .hasPreviousPage(page.hasPrevious())
                            .nextPageUrl("localhost")
                            .previousPageUrl("localhost")
                            .build()
            );

            return response;

        } catch (DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage("Error consultando proveedores")
                    .withCode("500")
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    public EntyPrvmaeproveedoresmaDto save(
            EntyPrvmaeproveedoresmaDto dto
    ) throws EBusinessException {
        validarProveedor(dto, true);

        if (dto.getPrvFecharegistroMprv() == null) {
            dto.setPrvFecharegistroMprv(LocalDate.now());
        }

        if (dto.getPrvEstadoregMprv() == null || dto.getPrvEstadoregMprv().isBlank()) {
            dto.setPrvEstadoregMprv("1");
        }

        EntyPrvmaeproveedoresma entity = toEntity(dto);
        EntyPrvmaeproveedoresma saved = repository.save(entity);

        return toDto(saved);
    }

    public EntyPrvmaeproveedoresmaDto update(
            Integer id,
            EntyPrvmaeproveedoresmaDto dto
    ) throws EBusinessException {
        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del proveedor es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        validarProveedor(dto, false);

        EntyPrvmaeproveedoresma old = repository.findById(id).orElse(null);

        if (old == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El proveedor no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        old.setPrvIdentifkeyMprv(dto.getPrvIdentifkeyMprv());
        old.setPrvNumeronitMprv(dto.getPrvNumeronitMprv());
        old.setPrvRazonsocialMprv(dto.getPrvRazonsocialMprv());
        old.setPrvNombrecomercialMprv(dto.getPrvNombrecomercialMprv());
        old.setPrvTipoproveedorMprv(dto.getPrvTipoproveedorMprv());
        old.setPrvContactoMprv(dto.getPrvContactoMprv());
        old.setPrvTelefonoMprv(dto.getPrvTelefonoMprv());
        old.setPrvCorreoMprv(dto.getPrvCorreoMprv());
        old.setPrvDireccionMprv(dto.getPrvDireccionMprv());
        old.setPrvCiudadMprv(dto.getPrvCiudadMprv());
        old.setPrvDepartamentoMprv(dto.getPrvDepartamentoMprv());
        old.setPrvFecharegistroMprv(dto.getPrvFecharegistroMprv());
        old.setPrvObservacionMprv(dto.getPrvObservacionMprv());
        old.setPrvEstadoregMprv(dto.getPrvEstadoregMprv());

        return toDto(repository.save(old));
    }

    public String changeStatus(
            Integer id,
            String estado
    ) throws EBusinessException {
        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del proveedor es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyPrvmaeproveedoresma proveedor = repository.findById(id).orElse(null);

        if (proveedor == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El proveedor no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        if ("1".equals(estado) || "2".equals(estado)) {
            proveedor.setPrvEstadoregMprv(estado);
        } else {
            proveedor.setPrvEstadoregMprv("2");
        }

        repository.save(proveedor);

        return "OK";
    }

    public String deleteLogic(
            Integer id
    ) throws EBusinessException {
        return changeStatus(id, "2");
    }

    private void validarProveedor(
            EntyPrvmaeproveedoresmaDto dto,
            boolean validarDuplicado
    ) throws EBusinessException {
        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El proveedor es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getPrvIdentifkeyMprv() == null || dto.getPrvIdentifkeyMprv().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional del proveedor es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getPrvRazonsocialMprv() == null || dto.getPrvRazonsocialMprv().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("La razón social del proveedor es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (validarDuplicado
                && repository.findByPrvIdentifkeyMprv(dto.getPrvIdentifkeyMprv()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe un proveedor con el código "
                            + dto.getPrvIdentifkeyMprv())
                    .withCode("409")
                    .buildBusinessException();
        }
    }

    private EntyPrvmaeproveedoresmaDto toDto(
            EntyPrvmaeproveedoresma entity
    ) {
        try {
            return entityToDtoTranslate.translate(entity);
        } catch (Exception e) {
            return new EntyPrvmaeproveedoresmaDto();
        }
    }

    private EntyPrvmaeproveedoresma toEntity(
            EntyPrvmaeproveedoresmaDto dto
    ) {
        try {
            return dtoToEntityTranslate.translate(dto);
        } catch (Exception e) {
            return new EntyPrvmaeproveedoresma();
        }
    }
}