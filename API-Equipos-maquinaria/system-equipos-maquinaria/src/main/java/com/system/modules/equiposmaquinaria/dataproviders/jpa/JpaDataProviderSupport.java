package com.system.modules.equiposmaquinaria.dataproviders.jpa;

import org.springframework.data.domain.Page;

import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;

public abstract class JpaDataProviderSupport {

    protected PaginationResponse buildPagination(
            final int currentPage,
            final int pageSize,
            final Page<?> page
    ) {
        return PaginationResponse.builder()
                .currentPage(currentPage)
                .totalPageSize(pageSize)
                .totalResults(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .hasNextPage(page.hasNext())
                .hasPreviousPage(page.hasPrevious())
                .nextPageUrl("LocalHost")
                .previousPageUrl("LocalHost")
                .build();
    }

    protected EBusinessException buildException(
            final String message,
            final Exception e
    ) {
        return ExceptionBuilder.builder()
                .withMessage(message)
                .withCode("500")
                .withParentException(e)
                .buildBusinessException();
    }

    protected Integer parseInteger(
            final String value
    ) {
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            return 0;
        }
    }

    protected String safeFilter(
            final String filter
    ) {
        return filter == null ? "" : filter;
    }

    protected String safeParameter(
            final String parameter
    ) {
        return parameter == null ? "TEXT" : parameter.toUpperCase();
    }

    protected int safeCurrentPage(
            final int currentPage
    ) {
        return currentPage <= 0 ? 0 : currentPage - 1;
    }

    protected int safePageSize(
            final int pageSize
    ) {
        return pageSize <= 0 ? 10 : pageSize;
    }
}