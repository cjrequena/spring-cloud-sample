package com.cjrequena.sample.fooserverservice.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.core.util.Json;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.zalando.jackson.datatype.money.MoneyModule;

import java.text.SimpleDateFormat;

import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;

/**
 *
 * @author cjrequena
 * @version 1.0
 * @since JDK1.8
 * @see
 *
 */
@Configuration
public class JacksonConfiguration {
  /**
   * Jackson builder.
   * @return the jackson2 object mapper builder
   */
  @Bean
  public Jackson2ObjectMapperBuilder jacksonBuilder() {
    final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
    builder.indentOutput(true);
    builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    return builder;
  }

  /**
   *
   * @return
   */
  @Bean
  public ObjectMapper objectMapper() {
    final ObjectMapper objectMapper = new ObjectMapper();

    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

    objectMapper.setPropertyNamingStrategy(SNAKE_CASE);
    Json.mapper().setPropertyNamingStrategy(SNAKE_CASE);

    objectMapper.disable(MapperFeature.AUTO_DETECT_IS_GETTERS);
    Json.mapper().disable(MapperFeature.AUTO_DETECT_IS_GETTERS);

    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    Json.mapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    Json.mapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    objectMapper.configure(MapperFeature.ALLOW_EXPLICIT_PROPERTY_RENAMING, true);
    Json.mapper().configure(MapperFeature.ALLOW_EXPLICIT_PROPERTY_RENAMING, true);

    objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
    Json.mapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);

    objectMapper.registerModule(new JavaTimeModule());
    Json.mapper().registerModule(new JavaTimeModule());

    objectMapper.registerModule(new MoneyModule().withFastMoney().withDefaultFormatting());
    Json.mapper().registerModule(new MoneyModule().withFastMoney().withDefaultFormatting());

    return objectMapper;
  }
}
