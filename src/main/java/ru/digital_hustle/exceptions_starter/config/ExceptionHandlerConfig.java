package ru.digital_hustle.exceptions_starter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.digital_hustle.exceptions_starter.factory.ExceptionResponseFactory;
import ru.digital_hustle.exceptions_starter.handler.BaseExceptionHandler;
import ru.digital_hustle.exceptions_starter.helper.ExceptionHandlerHelper;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
@ConditionalOnProperty(
        name = "digital-hustle.exception.handler.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class ExceptionHandlerConfig {

    @Bean
    @ConditionalOnMissingBean
    public BaseExceptionHandler customExceptionHandler(
            ExceptionHandlerHelper exceptionHandlerHelper,
            ExceptionResponseFactory exceptionResponseFactory
    ) {
        return new BaseExceptionHandler(exceptionHandlerHelper, exceptionResponseFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    public ExceptionResponseFactory exceptionResponseFactory(Clock clock) {
        return new ExceptionResponseFactory(clock);
    }

    @Bean
    @ConditionalOnMissingBean
    public ExceptionHandlerHelper exceptionHandlerHelper(Clock clock) {
        return new ExceptionHandlerHelper();
    }

    @Bean
    @ConditionalOnMissingBean
    public Clock clock() {
        return Clock.system(ZoneId.of("Europe/Moscow"));
    }
}
