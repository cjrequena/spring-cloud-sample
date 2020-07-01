package com.cjrequena.sample.fooclientservice.configuration;

import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author cjrequena
 */
@Log4j2
@Configuration
public class CircuitBreakerConfiguration {

  @Bean
  public Map<String, Number>  checkCircuitBreakerConfiguration() {

    BulkheadRegistry bulkheadRegistry = BulkheadRegistry.ofDefaults();
    ThreadPoolBulkheadRegistry threadPoolBulkheadRegistry = ThreadPoolBulkheadRegistry.ofDefaults();
    CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();
    RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.ofDefaults();
    RetryRegistry retryRegistry = RetryRegistry.ofDefaults();

    log.info("Default Bulkhead maxConcurrentCalls {}", bulkheadRegistry.getDefaultConfig().getMaxConcurrentCalls());
    log.info("Default CircuitBreaker failureRateThreshold {}", circuitBreakerRegistry.getDefaultConfig().getFailureRateThreshold());
    log.info("Default RateLimiter limitForPeriod {}", rateLimiterRegistry.getDefaultConfig().getLimitForPeriod());
    log.info("Default ThreadPoolBulkhead max thread pool {}", threadPoolBulkheadRegistry.getDefaultConfig().getMaxThreadPoolSize());
    log.info("Default Retry max retry {}", retryRegistry.getDefaultConfig().getMaxAttempts());

    Map<String, Number> result = new HashMap<>();
    result.put("Default Bulkhead maxConcurrentCalls", bulkheadRegistry.getDefaultConfig().getMaxConcurrentCalls());
    result.put("Default CircuitBreaker failureRateThreshold", circuitBreakerRegistry.getDefaultConfig().getFailureRateThreshold());
    result.put("Default RateLimiter limitForPeriod", rateLimiterRegistry.getDefaultConfig().getLimitForPeriod());
    result.put("Default ThreadPoolBulkhead max thread pool", threadPoolBulkheadRegistry.getDefaultConfig().getMaxThreadPoolSize());
    result.put("Default Retry max retry", retryRegistry.getDefaultConfig().getMaxAttempts());
    return result;
  }

}
