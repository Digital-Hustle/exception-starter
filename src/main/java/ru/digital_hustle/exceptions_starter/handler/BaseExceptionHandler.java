package ru.digital_hustle.exceptions_starter.handler;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
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

import java.time.Clock;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class BaseExceptionHandler {
    private final Clock clock;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionRs handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        Map<String, String> errors = fieldErrors.stream()
                .filter(fieldError -> fieldError.getDefaultMessage() != null)
                .collect(
                        Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,
                                (msg1, msg2) -> msg1 + ErrorMessages.ERROR_MESSAGES_SEPARATOR + msg2
                        )
                );

        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage(), exception);
        return ExceptionResponseFactory.newBadRequest(ErrorMessages.VALIDATION_FAILED, clock, errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ExceptionRs handleConstraintViolationException(HandlerMethodValidationException exception) {
        Map<String, String> errors = exception.getParameterValidationResults().stream()
                .collect(Collectors.toMap(
                        result -> result.getMethodParameter().getParameterName(),
                        result -> result.getResolvableErrors().stream()
                                .map(MessageSourceResolvable::getDefaultMessage)
                                .filter(Objects::nonNull)
                                .collect(Collectors.joining(ErrorMessages.ERROR_MESSAGES_SEPARATOR))
                ))
                .entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage(), exception);
        return ExceptionResponseFactory.newBadRequest(ErrorMessages.VALIDATION_FAILED, clock, errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ExceptionRs handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage(), exception);
        return ExceptionResponseFactory.newBadRequest(ErrorMessages.VALIDATION_FAILED + " Invalid value.", clock);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ExceptionRs handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage(), exception);
        return ExceptionResponseFactory.newBadRequest(exception.getMessage(), clock);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ExceptionRs handleEntityNotFoundException(EntityNotFoundException exception) {
        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage(), exception);
        return ExceptionResponseFactory.newNotFound(exception.getMessage(), clock);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionRs handleException(Exception exception) {
        log.error(ExceptionConstants.LOG_MESSAGE, exception.getMessage());
        return ExceptionResponseFactory.newInternalServerError(clock);
    }
}
