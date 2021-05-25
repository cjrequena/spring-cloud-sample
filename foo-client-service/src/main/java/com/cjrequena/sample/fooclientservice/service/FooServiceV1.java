package com.cjrequena.sample.fooclientservice.service;

import com.cjrequena.sample.fooclientservice.dto.FooDTOV1;
import com.cjrequena.sample.fooclientservice.exception.service.FeignBadRequestServiceException;
import com.cjrequena.sample.fooclientservice.exception.service.FeignConflictServiceException;
import com.cjrequena.sample.fooclientservice.exception.service.FeignNotFoundServiceException;
import com.cjrequena.sample.fooclientservice.service.feign.FooServerServiceV1Feign;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.json.JsonMergePatch;
import javax.json.JsonPatch;
import java.util.List;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author cjrequena
 * @version 1.0
 */
@Log4j2
@Service
public class FooServiceV1 {

  private static final String FOO_SERVICE = "foo-service";

  private CircuitBreakerFactory circuitBreakerFactory;
  private FooServerServiceV1Feign fooServerServiceV1Feign;

  @Autowired
  public FooServiceV1(FooServerServiceV1Feign fooServerServiceV1Feign, CircuitBreakerFactory circuitBreakerFactory) {
    this.fooServerServiceV1Feign = fooServerServiceV1Feign;
    this.circuitBreakerFactory = circuitBreakerFactory;
  }

  //@TimeLimiter(name = FOO_SERVICE)
  @CircuitBreaker(name = FOO_SERVICE, fallbackMethod = "createFallbackMethod")
  @Bulkhead(name = FOO_SERVICE)
  @Retry(name = FOO_SERVICE)
  public ResponseEntity<Void> create(FooDTOV1 dto) throws FeignConflictServiceException, FeignBadRequestServiceException {
    return fooServerServiceV1Feign.create(dto);
  }

  /**
   *
   * @param dto
   * @return
   * @throws ServiceException
   */
  public ResponseEntity<Void> createFallbackMethod(FooDTOV1 dto, Throwable ex) throws FeignConflictServiceException, FeignBadRequestServiceException {
    //--
    log.debug("createFallbackMethod");
    if (ex instanceof FeignConflictServiceException) {
      throw (FeignConflictServiceException) ex;
    } else {
      throw (FeignBadRequestServiceException) ex;
    }
    //--
  }

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  //@TimeLimiter(name = FOO_SERVICE)
  @CircuitBreaker(name = FOO_SERVICE, fallbackMethod = "retrieveByIdFallbackMethod")
  @Bulkhead(name = FOO_SERVICE)
  @Retry(name = FOO_SERVICE)
  public ResponseEntity<FooDTOV1> retrieveById(Long id) throws FeignNotFoundServiceException {
    //--
    return fooServerServiceV1Feign.retrieveById(id);
    //--
  }

  /**
   *
   * @param id
   * @param ex
   * @return
   * @throws ServiceException
   */
  public ResponseEntity<FooDTOV1> retrieveByIdFallbackMethod(Long id, Throwable ex) throws FeignNotFoundServiceException {
    //--
    log.debug("retrieveByIdFallbackMethod");
    throw (FeignNotFoundServiceException) ex;
    //--
  }

  /**
   *
   * @param fields
   * @param filters
   * @param sort
   * @param offset
   * @param limit
   * @return
   * @throws ServiceException
   */
  @CircuitBreaker(name = FOO_SERVICE, fallbackMethod = "retrieveFallbackMethod")
  @Bulkhead(name = FOO_SERVICE)
  @Retry(name = FOO_SERVICE)
  public ResponseEntity<List<FooDTOV1>> retrieve(String fields, String filters, String sort, Integer offset, Integer limit) throws FeignBadRequestServiceException {
    //--
    return fooServerServiceV1Feign.retrieve(fields, filters, sort, offset, limit);
    //--
  }

  /**
   *
   * @param fields
   * @param filters
   * @param sort
   * @param offset
   * @param limit
   * @param ex
   * @return
   * @throws ServiceException
   */
  public ResponseEntity<List<FooDTOV1>> retrieveFallbackMethod(String fields, String filters, String sort, Integer offset, Integer limit, Throwable ex)
    throws FeignBadRequestServiceException {
    //--
    log.debug("retrieveFallbackMethod");
    throw (FeignBadRequestServiceException) ex;
    //--
  }

  /**
   *
   * @param id
   * @param dto
   * @return
   * @throws ServiceException
   */
  @CircuitBreaker(name = FOO_SERVICE, fallbackMethod = "updateFallbackMethod")
  @Bulkhead(name = FOO_SERVICE)
  @Retry(name = FOO_SERVICE)
  public ResponseEntity<Void> update(Long id, FooDTOV1 dto) throws FeignNotFoundServiceException {
    //--
    return fooServerServiceV1Feign.update(id, dto);
    //--
  }

  /**
   *
   * @param id
   * @param dto
   * @param ex
   * @return
   * @throws ServiceException
   */
  public ResponseEntity<Void> updateFallbackMethod(Long id, FooDTOV1 dto, Throwable ex) throws FeignNotFoundServiceException {
    //--
    log.debug("updateFallbackMethod");
    throw (FeignNotFoundServiceException) ex;
    //--
  }

  /**
   *
   * @param id
   * @param patchDocument
   * @return
   * @throws ServiceException
   */
  @CircuitBreaker(name = FOO_SERVICE, fallbackMethod = "patchFallbackMethod")
  @Bulkhead(name = FOO_SERVICE)
  @Retry(name = FOO_SERVICE)
  public ResponseEntity<Void> patch(Long id, JsonPatch patchDocument) throws FeignNotFoundServiceException {
    //--
    return fooServerServiceV1Feign.patch(id, patchDocument);
    //--
  }

  /**
   *
   * @param id
   * @param patchDocument
   * @param ex
   * @return
   * @throws ServiceException
   */
  public ResponseEntity<Void> patchFallbackMethod(Long id, JsonPatch patchDocument, Throwable ex) throws FeignNotFoundServiceException {
    //--
    log.debug("patchFallbackMethod");
    throw (FeignNotFoundServiceException) ex;
    //--
  }

  /**
   *
   * @param id
   * @param mergePatchDocument
   * @return
   * @throws ServiceException
   */
  @CircuitBreaker(name = FOO_SERVICE, fallbackMethod = "mergeFallbackMethod")
  @Bulkhead(name = FOO_SERVICE)
  @Retry(name = FOO_SERVICE)
  public ResponseEntity<Void> merge(Long id, JsonMergePatch mergePatchDocument) throws FeignNotFoundServiceException {
    return fooServerServiceV1Feign.patch(id, mergePatchDocument);
  }

  public ResponseEntity<Void> mergeFallbackMethod(Long id, JsonMergePatch mergePatchDocument, Throwable ex) throws FeignNotFoundServiceException {
    //--
    log.debug("mergeFallbackMethod");
    throw (FeignNotFoundServiceException) ex;
    //--
  }

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  @CircuitBreaker(name = FOO_SERVICE, fallbackMethod = "deleteFallbackMethod")
  @Bulkhead(name = FOO_SERVICE)
  @Retry(name = FOO_SERVICE)
  public ResponseEntity<Void> delete(Long id) throws FeignNotFoundServiceException {
    return fooServerServiceV1Feign.delete(id);
  }

  /**
   *
   * @param id
   * @param ex
   * @return
   * @throws ServiceException
   */
  public ResponseEntity<Void> deleteFallbackMethod(Long id, Throwable ex) throws FeignNotFoundServiceException {
    //--
    log.debug("deleteFallbackMethod");
    throw (FeignNotFoundServiceException) ex;
    //--
  }

}
