package edu.java.bot.controller.exception;

import edu.java.bot.controller.dto.ApiErrorResponse;

public class ApiException extends RuntimeException {
    public ApiException(ApiErrorResponse apiErrorResponse) {
        super(apiErrorResponse.getExceptionMessage());
    }
}
