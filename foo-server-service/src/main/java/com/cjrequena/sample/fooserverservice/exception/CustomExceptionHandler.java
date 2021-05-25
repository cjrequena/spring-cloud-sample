package com.cjrequena.sample.fooserverservice.exception;

import com.cjrequena.sample.fooserverservice.exception.web.WebException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author cjrequena
 */
@SuppressWarnings("unchecked")
@ControllerAdvice
@Log4j2
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String EXCEPTION_LOG = "Exception {}";
  public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

  @ExceptionHandler({WebException.class})
  @ResponseBody
  public ResponseEntity<Object> handleWebException(WebException ex) {
    log.warn(EXCEPTION_LOG, ex.getMessage());
    ErrorDTO errorDTO = new ErrorDTO();
    errorDTO.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
    errorDTO.setStatus(ex.getHttpStatus().value());
    errorDTO.setErrorCode(ex.getClass().getSimpleName());
    errorDTO.setMessage(ex.getMessage());
    return ResponseEntity.status(ex.getHttpStatus()).contentType(MediaType.APPLICATION_JSON).body(errorDTO);
  }

}
