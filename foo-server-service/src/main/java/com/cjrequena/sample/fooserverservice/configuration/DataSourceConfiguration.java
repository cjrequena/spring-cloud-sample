package com.cjrequena.sample.fooserverservice.configuration;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.annotation.Validated;

/**
 * <p>
 * <p>
 *
 * @author cjrequena
 * @version 1.0
 * @see
 * @since JDK1.8
 */
@Log4j2
@Configuration
public class DataSourceConfiguration {

  private ConexionType conexionType;

  @Primary
  @Bean(name = "dataSource", destroyMethod = "")
  @Validated
  @ConfigurationProperties(prefix = "spring.datasource.postgres")
  @ConditionalOnClass({HikariDataSource.class})
  public HikariDataSource dataSource() {
    conexionType = ConexionType.POSTGRES;
    return new HikariDataSource();
  }

  private enum ConexionType {
    POSTGRES,
    HSQL
  }
}
