package ru.digital_hustle.exceptions_starter.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import ru.digital_hustle.exceptions_starter.dto.response.ExceptionRs;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionResponseFactory {

    public static ExceptionRs newBadRequest(String message, Clock clock) {
        return ExceptionRs.builder()
                .message(message)
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .timestamp(OffsetDateTime.now(clock))
                .build();
    }

    public static ExceptionRs newBadRequest(String message, Clock clock, Map<String, String> errors) {
        return ExceptionRs.builder()
                .message(message)
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .timestamp(OffsetDateTime.now(clock))
                .errors(errors)
                .build();
    }

    public static ExceptionRs newForbidden(String message, Clock clock) {
        return ExceptionRs.builder()
                .message(message)
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .timestamp(OffsetDateTime.now(clock))
                .build();
    }

    public static ExceptionRs newNotFound(String message, Clock clock) {
        return ExceptionRs.builder()
                .message(message)
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .timestamp(OffsetDateTime.now(clock))
                .build();
    }

    public static ExceptionRs newConflict(String message, Clock clock) {
        return ExceptionRs.builder()
                .message(message)
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .timestamp(OffsetDateTime.now(clock))
                .build();
    }

    public static ExceptionRs newInternalServerError(Clock clock) {
        return ExceptionRs.builder()
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .timestamp(OffsetDateTime.now(clock))
                .build();
    }

    public static ExceptionRs newServiceUnavailable(String message, Clock clock) {
        return ExceptionRs.builder()
                .message(message)
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .error(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
                .timestamp(OffsetDateTime.now(clock))
                .build();
    }
}
