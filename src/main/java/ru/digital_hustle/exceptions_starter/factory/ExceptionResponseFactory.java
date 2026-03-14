package ru.digital_hustle.exceptions_starter.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.digital_hustle.exceptions_starter.dto.response.ExceptionRs;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public final class ExceptionResponseFactory {

    private final Clock clock;

    public ExceptionRs newBadRequest(String message) {
        return ExceptionRs.builder()
                .message(message)
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .timestamp(OffsetDateTime.now(clock))
                .build();
    }

    public ExceptionRs newBadRequest(String message, Map<String, String> errors) {
        return ExceptionRs.builder()
                .message(message)
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .timestamp(OffsetDateTime.now(clock))
                .errors(errors)
                .build();
    }

    public ExceptionRs newForbidden(String message) {
        return ExceptionRs.builder()
                .message(message)
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .timestamp(OffsetDateTime.now(clock))
                .build();
    }

    public ExceptionRs newNotFound(String message) {
        return ExceptionRs.builder()
                .message(message)
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .timestamp(OffsetDateTime.now(clock))
                .build();
    }

    public ExceptionRs newConflict(String message) {
        return ExceptionRs.builder()
                .message(message)
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .timestamp(OffsetDateTime.now(clock))
                .build();
    }

    public ExceptionRs newInternalServerError() {
        return ExceptionRs.builder()
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .timestamp(OffsetDateTime.now(clock))
                .build();
    }

    public ExceptionRs newServiceUnavailable(String message) {
        return ExceptionRs.builder()
                .message(message)
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .error(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
                .timestamp(OffsetDateTime.now(clock))
                .build();
    }
}
