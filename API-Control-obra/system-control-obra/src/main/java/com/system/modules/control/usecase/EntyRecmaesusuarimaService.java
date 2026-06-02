package com.system.modules.users.usecase;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.system.crosscutting.domain.model.EntyDeleteDto;
import com.system.crosscutting.domain.model.EntyRecmaesusuarimaDto;
import com.system.crosscutting.domain.model.EntyRecmaesusuarimaResponse;
import com.system.crosscutting.domain.model.EntyUsersUtiliDto;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.messages.SearchMessages;
import com.system.modules.users.dataproviders.jpa.JpaEntyRecmaesusuarimaDataProviders;
import com.system.modules.users.services.UseCase;
import com.system.modules.users.services.UsecaseServices;

@UseCase
public class EntyRecmaesusuarimaService
        extends UsecaseServices<EntyRecmaesusuarimaDto, JpaEntyRecmaesusuarimaDataProviders> {

    @Autowired
    private JpaEntyRecmaesusuarimaDataProviders jpaDataProviders;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyRecmaesusuarimaResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.ijpaDataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyRecmaesusuarimaResponse saveBefore(
            EntyRecmaesusuarimaResponse dto
    ) throws EBusinessException {
        try {
            for (EntyRecmaesusuarimaDto dtox : dto.getRspData()) {
                dtox.setSisCountaRkey(0);
                dtox.setSisCountbRkey(0);
                dtox.setSisCountcRkey(0);
                dtox.setSisCountdRkey(0);
                dtox.setSisCounteRkey(0);
                dtox.setSisCountfRkey(0);
                dtox.setRecEstregReus("1");
            }

            List<EntyRecmaesusuarimaDto> dtoAux = this.ijpaDataProvider.save(dto.getRspData());

            for (EntyRecmaesusuarimaDto dtox : dtoAux) {
                if (dtox.getRecNroregReus().equals("NA")) {
                    String idItem = dtox.getSisIdedptSidp()
                            + "I"
                            + String.format("%010d", dtox.getRecIdeunikeyReus());

                    dtox.setRecNroregReus(idItem);
                }
            }

            dtoAux = this.ijpaDataProvider.save(dtoAux);

            dto.setRspMessage("OK");
            dto.setRspValue("OK");
            dto.setRspParentKey("NA");
            dto.setRspAppKey("NA");
            dto.setRspData(dtoAux);

            return dto;
        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.CREATE_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.CREATE_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    public EntyRecmaesusuarimaResponse updateAll(
            EntyRecmaesusuarimaResponse dto
    ) throws EBusinessException {
        try {
            List<EntyRecmaesusuarimaDto> dtoAux = new ArrayList<>();

            for (EntyRecmaesusuarimaDto dtox : dto.getRspData()) {
                dtox.setSisCountaRkey(dtox.getSisCountaRkey() == null ? 0 : dtox.getSisCountaRkey());
                dtox.setSisCountbRkey(dtox.getSisCountbRkey() == null ? 0 : dtox.getSisCountbRkey());
                dtox.setSisCountcRkey(dtox.getSisCountcRkey() == null ? 0 : dtox.getSisCountcRkey());
                dtox.setSisCountdRkey(dtox.getSisCountdRkey() == null ? 0 : dtox.getSisCountdRkey());
                dtox.setSisCounteRkey(dtox.getSisCounteRkey() == null ? 0 : dtox.getSisCounteRkey());
                dtox.setSisCountfRkey(dtox.getSisCountfRkey() == null ? 0 : dtox.getSisCountfRkey());

                if (dtox.getRecEstregReus() == null || dtox.getRecEstregReus().trim().isEmpty()) {
                    dtox.setRecEstregReus("1");
                }

                dtoAux.add(this.ijpaDataProvider.update(dtox.getRecIdeunikeyReus(), dtox));
            }

            dto.setRspMessage("OK");
            dto.setRspValue("OK");
            dto.setRspParentKey("NA");
            dto.setRspAppKey("NA");
            dto.setRspData(dtoAux);

            return dto;
        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.UPDATE_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.UPDATE_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    public EntyRecmaesusuarimaResponse updateImage(
            EntyRecmaesusuarimaResponse dto
    ) throws EBusinessException {
        try {
            List<EntyRecmaesusuarimaDto> dtoAux = new ArrayList<>();

            for (EntyRecmaesusuarimaDto dtox : dto.getRspData()) {
                dtoAux.add(this.ijpaDataProvider.updateImage(dtox.getRecIdeunikeyReus(), dtox));
            }

            dto.setRspMessage("OK");
            dto.setRspValue("OK");
            dto.setRspParentKey("NA");
            dto.setRspAppKey("NA");
            dto.setRspData(dtoAux);

            return dto;
        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.UPDATE_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.UPDATE_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    public String changestatusAll(
            List<EntyUsersUtiliDto> dto
    ) throws EBusinessException {
        try {
            if (dto == null || dto.isEmpty()) {
                return "OK";
            }

            for (EntyUsersUtiliDto dtox : dto) {
                if (dtox.getRecPKey() <= 0) {
                    continue;
                }

                EntyRecmaesusuarimaDto user = this.ijpaDataProvider.get(dtox.getRecPKey());

                int receivedStatus = dtox.getRecEstreg();

                String nextStatus;

                if (receivedStatus == 1 || receivedStatus == 2 || receivedStatus == 3) {
                    nextStatus = String.valueOf(receivedStatus);
                } else {
                    nextStatus = "2";
                }

                user.setRecEstregReus(nextStatus);

                this.ijpaDataProvider.update(dtox.getRecPKey(), user);
            }

            return "OK";
        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.UPDATE_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.UPDATE_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }
    public String deleteAll(
            List<EntyDeleteDto> dto
    ) throws EBusinessException {
        try {
            for (EntyDeleteDto dtox : dto) {
                EntyRecmaesusuarimaDto user = this.ijpaDataProvider.get(dtox.getRecPKey());

                user.setRecEstregReus("3");

                this.ijpaDataProvider.update(dtox.getRecPKey(), user);
            }

            return "OK";
        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.DELETE_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.DELETE_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }
}