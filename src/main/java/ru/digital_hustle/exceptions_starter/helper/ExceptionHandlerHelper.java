package ru.digital_hustle.exceptions_starter.helper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import ru.digital_hustle.exceptions_starter.constant.ErrorMessages;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ExceptionHandlerHelper {
    public Map<String, String> extractFieldErrors(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        return fieldErrors.stream()
                .filter(fieldError -> StringUtils.isNotBlank(fieldError.getDefaultMessage()))
                .collect(
                        Collectors.toMap(
                                FieldError::getField, FieldError::getDefaultMessage,
                                "%s; %s"::formatted
                        )
                );
    }

    public Map<String, String> extractFieldErrors(HandlerMethodValidationException exception) {
        List<ParameterValidationResult> parameterErrors = exception.getParameterValidationResults();

        return parameterErrors.stream()
                .collect(Collectors.toMap(
                        result -> result.getMethodParameter().getParameterName(),
                        result -> result.getResolvableErrors().stream()
                                .map(MessageSourceResolvable::getDefaultMessage)
                                .filter(StringUtils::isNotBlank)
                                .collect(Collectors.joining(ErrorMessages.ERROR_MESSAGES_SEPARATOR))
                ))
                .entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
