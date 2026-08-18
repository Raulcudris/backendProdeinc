package com.system.modules.workcontrol.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.system.crosscutting.domain.enums.ProcessName;
import com.system.crosscutting.domain.enums.Status;
import com.system.crosscutting.domain.model.traceability.TransactionFile;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.contracts.IjpaDataProviders;

public class UsecaseServices<T, K> {

    protected K ijpaDataProvider;

    @Autowired
    private TraceabilityService traceabilityService;

    public Map<String, String> getAllTraceability() {
        return new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public Object getAll() throws EBusinessException, MicroEventException {
        String transactionalId = UUID.randomUUID().toString();

        try {
            traceabilityService.createTraceabilityEventDocument(
                    transactionalId,
                    Status.INITIAL.getDescription(),
                    TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(),
                    getAllTraceability()
            );

            return ((IjpaDataProviders<T, ?>) ijpaDataProvider).getAll();

        } catch (EBusinessException e) {
            traceabilityService.createTraceabilityEventDocument(
                    transactionalId,
                    Status.FAIL.getDescription(),
                    TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(),
                    getAllTraceability()
            );

            throw ExceptionBuilder.builder()
                    .withMessage(e.getMessage())
                    .withCode(e.getCode())
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    @SuppressWarnings("unchecked")
    public Object getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException, MicroEventException {
        String transactionalId = UUID.randomUUID().toString();

        try {
            traceabilityService.createTraceabilityEventDocument(
                    transactionalId,
                    Status.INITIAL.getDescription(),
                    TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(),
                    getAllTraceability()
            );

            return ((IjpaDataProviders<T, ?>) ijpaDataProvider)
                    .getAll(currentPage, pageSize, parameter, filter);

        } catch (EBusinessException e) {
            traceabilityService.createTraceabilityEventDocument(
                    transactionalId,
                    Status.FAIL.getDescription(),
                    TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(),
                    getAllTraceability()
            );

            throw ExceptionBuilder.builder()
                    .withMessage(e.getMessage())
                    .withCode(e.getCode())
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    public Map<String, String> getTraceability() {
        return new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public T get(Integer id) throws EBusinessException, MicroEventException {
        String transactionalId = UUID.randomUUID().toString();

        try {
            traceabilityService.createTraceabilityEventDocument(
                    transactionalId,
                    Status.INITIAL.getDescription(),
                    TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(),
                    getTraceability()
            );

            return ((IjpaDataProviders<T, ?>) ijpaDataProvider).get(id);

        } catch (EBusinessException e) {
            traceabilityService.createTraceabilityEventDocument(
                    transactionalId,
                    Status.FAIL.getDescription(),
                    TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(),
                    getTraceability()
            );

            throw ExceptionBuilder.builder()
                    .withMessage(e.getMessage())
                    .withCode(e.getCode())
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    public Map<String, String> createTraceability() {
        return new HashMap<>();
    }

    public T beforeCreate(T dto) throws EBusinessException {
        return dto;
    }

    public T afterCreate(T dto) throws EBusinessException {
        return dto;
    }

    @SuppressWarnings("unchecked")
    public T create(T dto) throws EBusinessException, MicroEventException {
        String transactionalId = UUID.randomUUID().toString();

        try {
            traceabilityService.createTraceabilityEventDocument(
                    transactionalId,
                    Status.INITIAL.getDescription(),
                    TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(),
                    createTraceability()
            );

            return afterCreate(
                    ((IjpaDataProviders<T, ?>) ijpaDataProvider).save(beforeCreate(dto))
            );

        } catch (EBusinessException e) {
            traceabilityService.createTraceabilityEventDocument(
                    transactionalId,
                    Status.FAIL.getDescription(),
                    TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(),
                    createTraceability()
            );

            throw ExceptionBuilder.builder()
                    .withMessage(e.getMessage())
                    .withCode(e.getCode())
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> create(List<T> dtos) throws EBusinessException {
        return ((IjpaDataProviders<T, ?>) ijpaDataProvider).save(dtos);
    }

    public Map<String, String> updateTraceability() {
        return new HashMap<>();
    }

    public T beforeUpdate(T dto) throws EBusinessException {
        return dto;
    }

    @SuppressWarnings("unchecked")
    public T update(Integer id, T dto) throws EBusinessException, MicroEventException {
        String transactionalId = UUID.randomUUID().toString();

        try {
            traceabilityService.createTraceabilityEventDocument(
                    transactionalId,
                    Status.INITIAL.getDescription(),
                    TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(),
                    updateTraceability()
            );

            return ((IjpaDataProviders<T, ?>) ijpaDataProvider).update(id, beforeUpdate(dto));

        } catch (EBusinessException e) {
            traceabilityService.createTraceabilityEventDocument(
                    transactionalId,
                    Status.FAIL.getDescription(),
                    TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(),
                    updateTraceability()
            );

            throw ExceptionBuilder.builder()
                    .withParentException(e)
                    .withMessage(e.getMessage())
                    .withCode(e.getCode())
                    .buildBusinessException();
        }
    }

    public Map<String, String> deleteTraceability() {
        return new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public void delete(Integer id) throws MicroEventException, EBusinessException {
        String transactionalId = UUID.randomUUID().toString();

        try {
            traceabilityService.createTraceabilityEventDocument(
                    transactionalId,
                    Status.INITIAL.getDescription(),
                    TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(),
                    deleteTraceability()
            );

            ((IjpaDataProviders<T, ?>) ijpaDataProvider).delete(id);

        } catch (EBusinessException e) {
            traceabilityService.createTraceabilityEventDocument(
                    transactionalId,
                    Status.FAIL.getDescription(),
                    TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(),
                    deleteTraceability()
            );

            throw ExceptionBuilder.builder()
                    .withParentException(e)
                    .withMessage(e.getMessage())
                    .withCode(e.getCode())
                    .buildBusinessException();
        }
    }
}