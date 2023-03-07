package com.ffhanyang.kiosk.configure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.ffhanyang.kiosk.security.Jwt;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ServiceConfigure {

    @Bean
    public Jwt jwt(JwtTokenConfigure jwtTokenConfigure) {
        return new Jwt(jwtTokenConfigure.getIssuer(), jwtTokenConfigure.getClientSecret(), jwtTokenConfigure.getExpirySeconds());
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        // Jackson 설정 처리
        return builder -> {
            JavaTimeModule jtm = new JavaTimeModule();
            jtm.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));

            builder.visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
            builder.visibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
            builder.visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            builder.modulesToInstall(jtm);
        };
    }

}
