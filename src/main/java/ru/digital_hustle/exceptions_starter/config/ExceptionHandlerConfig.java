package ru.digital_hustle.exceptions_starter.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.digital_hustle.exceptions_starter.handler.BaseExceptionHandler;

import java.time.Clock;
import java.time.ZoneId;

//@AutoConfiguration
//@ComponentScan("ru.digital_hustle.exceptions_starter")
@Configuration
@ConditionalOnProperty(name = "ru.digital-hustle.exception.handler.enabled", havingValue = "true")
public class ExceptionHandlerConfig {

    @Bean
    @ConditionalOnMissingBean
    public BaseExceptionHandler customExceptionHandler(Clock clock) {
        return new BaseExceptionHandler(clock);
    }

    @Bean
    @ConditionalOnMissingBean
    public Clock clock() {
        return Clock.system(ZoneId.of("Europe/Moscow"));
    }
}
