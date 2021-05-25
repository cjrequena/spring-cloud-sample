package com.cjrequena.sample.fooserverservice.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 * @author cjrequena
 *
 */
@Service
@Log4j2
@Data
public class DefaultFeignErrorDecoder implements ErrorDecoder {

  /**
   *
   */
  @Autowired(required = false)
  private JacksonDecoder jacksonDecoder = new JacksonDecoder();

  /**
   *
   */
  private ErrorDecoder delegate = new Default();

  /**
   * @param methodKey
   * @param response
   * @return
   */
  @Override
  public Exception decode(String methodKey, Response response) {
    return delegate.decode(methodKey, response);
  }

}
