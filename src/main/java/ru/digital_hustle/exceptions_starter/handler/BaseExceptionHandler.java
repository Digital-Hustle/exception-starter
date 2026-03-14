package ru.digital_hustle.exceptions_starter.handler;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.digital_hustle.exceptions_starter.constant.ErrorMessages;
import ru.digital_hustle.exceptions_starter.constant.ExceptionConstants;
import ru.digital_hustle.exceptions_starter.dto.response.ExceptionRs;
import ru.digital_hustle.exceptions_starter.factory.ExceptionResponseFactory;
import ru.digital_hustle.exceptions_starter.helper.ExceptionHandlerHelper;

import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class BaseExceptionHandler {

    private final ExceptionHandlerHelper exceptionHandlerHelper;
    private final ExceptionResponseFactory exceptionResponseFactory;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionRs handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = exceptionHandlerHelper.extractFieldErrors(exception);

        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage(), exception);
        return exceptionResponseFactory.newBadRequest(ErrorMessages.VALIDATION_FAILED, errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ExceptionRs handleConstraintViolationException(HandlerMethodValidationException exception) {
        Map<String, String> errors = exceptionHandlerHelper.extractFieldErrors(exception);

        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage(), exception);
        return exceptionResponseFactory.newBadRequest(ErrorMessages.VALIDATION_FAILED, errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ExceptionRs handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage(), exception);
        return exceptionResponseFactory.newBadRequest(ErrorMessages.VALIDATION_FAILED + " Invalid value.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ExceptionRs handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage(), exception);
        return exceptionResponseFactory.newBadRequest(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ExceptionRs handleEntityNotFoundException(EntityNotFoundException exception) {
        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage(), exception);
        return exceptionResponseFactory.newNotFound(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionRs handleException(Exception exception) {
        log.error(ExceptionConstants.LOG_MESSAGE, exception.getMessage());
        return exceptionResponseFactory.newInternalServerError();
    }
}
