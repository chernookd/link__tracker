package edu.java.controller.exception;

import edu.java.controller.dto.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = CustomExceptionHandler.class)
public class ControllerExceptionHandler {

    @ExceptionHandler({AddChatException.class})
    public ResponseEntity<ApiErrorResponse> handleAddChatException(AddChatException e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(new ApiErrorResponse(
                e.getMessage(),
                String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()),
                e.getClass().getSimpleName(),
                e.getMessage(),
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList()));
    }

    @ExceptionHandler({AddLinkException.class})
    public ResponseEntity<ApiErrorResponse> handleAddLinkException(AddLinkException e) {

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(new ApiErrorResponse(
                e.getMessage(),
                String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()),
                e.getClass().getSimpleName(),
                e.getMessage(),
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList()));
    }

    @ExceptionHandler({DeleteChatException.class})
    public ResponseEntity<ApiErrorResponse> handleDeleteChatException(DeleteChatException e) {

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(new ApiErrorResponse(
                e.getMessage(),
                String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()),
                e.getClass().getSimpleName(),
                e.getMessage(),
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList()));
    }

    @ExceptionHandler({DeleteLinkException.class})
    public ResponseEntity<ApiErrorResponse> handleDeleteLinkException(DeleteLinkException e) {

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(new ApiErrorResponse(
                e.getMessage(),
                String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()),
                e.getClass().getSimpleName(),
                e.getMessage(),
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList()));
    }

    @ExceptionHandler({FindLinkException.class})
    public ResponseEntity<ApiErrorResponse> handleFindLinkException(FindLinkException e) {

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(new ApiErrorResponse(
                e.getMessage(),
                String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()),
                e.getClass().getSimpleName(),
                e.getMessage(),
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList()));
    }

}

