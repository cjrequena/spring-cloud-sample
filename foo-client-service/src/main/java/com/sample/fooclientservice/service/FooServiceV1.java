package com.sample.fooclientservice.service;

import com.sample.fooclientservice.dto.FooDTOV1;
import com.sample.fooclientservice.exception.EErrorCode;
import com.sample.fooclientservice.exception.ServiceException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

  @Autowired
  FooServerServiceV1Feign fooServerServiceV1Feign;
  private static final String FOO_SERVER_SERVICE = "foo-server-service";

  /**
   *
   * @param dto
   * @return
   * @throws ServiceException
   */
  //@TimeLimiter(name = FOO_SERVER_SERVICE)
  @CircuitBreaker(name = FOO_SERVER_SERVICE, fallbackMethod = "createFallbackMethod")
  @Bulkhead(name = FOO_SERVER_SERVICE)
  @Retry(name = FOO_SERVER_SERVICE)
  public ResponseEntity<Void> create(FooDTOV1 dto) throws ServiceException {
    //--
    try {
      return fooServerServiceV1Feign.create(dto);
    } catch (ServiceException ex) {
      log.error("{}", ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      log.error("{}", ex.getMessage());
      throw new ServiceException(EErrorCode.BAD_REQUEST_ERROR.getErrorCode(), ex);
    }
    //--
  }

  /**
   *
   * @param dto
   * @return
   * @throws ServiceException
   */
  public ResponseEntity<Void> createFallbackMethod(FooDTOV1 dto, Throwable ex) throws ServiceException {
    //--
    log.debug("createFallbackMethod");
    throw (ServiceException) ex;
    //--
  }


  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  //@TimeLimiter(name = FOO_SERVER_SERVICE)
  @CircuitBreaker(name = FOO_SERVER_SERVICE, fallbackMethod = "retrieveByIdFallbackMethod")
  @Bulkhead(name = FOO_SERVER_SERVICE)
  @Retry(name = FOO_SERVER_SERVICE)
  public ResponseEntity<FooDTOV1> retrieveById(Long id) throws ServiceException {
    //--
    try {
      return fooServerServiceV1Feign.retrieveById(id);
    } catch (ServiceException ex) {
      log.error("{}", ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      log.error("{}", ex.getMessage());
      throw new ServiceException(EErrorCode.BAD_REQUEST_ERROR.getErrorCode(), ex);
    }
    //--
  }

  /**
   *
   * @param id
   * @param ex
   * @return
   * @throws ServiceException
   */
  public ResponseEntity<FooDTOV1> retrieveByIdFallbackMethod(Long id, Throwable ex) throws ServiceException {
    //--
    log.debug("retrieveByIdFallbackMethod");
    throw (ServiceException) ex;
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
  public ResponseEntity<List<FooDTOV1>> retrieve(String fields, String filters, String sort, Integer offset, Integer limit) throws ServiceException {
    //--
    try {
      return fooServerServiceV1Feign.retrieve(fields, filters, sort, offset, limit);
    } catch (ServiceException ex) {
      log.error("{}", ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      log.error("{}", ex.getMessage());
      throw new ServiceException(EErrorCode.BAD_REQUEST_ERROR.getErrorCode(), ex);
    }
    //--
  }

  /**
   *
   * @param id
   * @param dto
   * @return
   * @throws ServiceException
   */
  public ResponseEntity<Void> update(Long id, FooDTOV1 dto) throws ServiceException {
    //--
    try {
      return fooServerServiceV1Feign.update(id, dto);
    } catch (ServiceException ex) {
      log.error("{}", ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      log.error("{}", ex.getMessage());
      throw new ServiceException(EErrorCode.BAD_REQUEST_ERROR.getErrorCode(), ex);
    }
    //--
  }

  /**
   *
   * @param id
   * @param patchDocument
   * @return
   * @throws ServiceException
   */
  public ResponseEntity<Void> patch(Long id, JsonPatch patchDocument) throws ServiceException {
    //--
    try {
      return fooServerServiceV1Feign.patch(id, patchDocument);
    } catch (ServiceException ex) {
      log.error("{}", ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      log.error("{}", ex.getMessage());
      throw new ServiceException(EErrorCode.BAD_REQUEST_ERROR.getErrorCode(), ex);
    }
  }

  /**
   *
   * @param id
   * @param mergePatchDocument
   * @return
   * @throws ServiceException
   */
  public ResponseEntity<Void> patch(Long id, JsonMergePatch mergePatchDocument) throws ServiceException {
    //--
    try {
      return fooServerServiceV1Feign.patch(id, mergePatchDocument);
    } catch (ServiceException ex) {
      log.error("{}", ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      log.error("{}", ex.getMessage());
      throw new ServiceException(EErrorCode.BAD_REQUEST_ERROR.getErrorCode(), ex);
    }
  }

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  public ResponseEntity<Void> delete(Long id) throws ServiceException {
    //--
    try {
      return fooServerServiceV1Feign.delete(id);
    } catch (ServiceException ex) {
      log.error("{}", ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      log.error("{}", ex.getMessage());
      throw new ServiceException(EErrorCode.BAD_REQUEST_ERROR.getErrorCode(), ex);
    }
    //--
  }


  /**
   *
   */
  //@FeignClient(url = "http://localhost:9080/foo-server-service")
  @FeignClient(name = "foo-server-service")
  @RequestMapping(value = "/foo-server-service")
  public interface FooServerServiceV1Feign {

    /**
     *
     * @param dto
     * @return
     * @throws ServiceException
     */
    @PostMapping(value = "/fooes",
      produces = {
        MediaType.APPLICATION_JSON_VALUE
      },
      headers = "Accept-Version=vnd.foo-service.v1"
    )
    ResponseEntity<Void> create(@RequestBody FooDTOV1 dto) throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    @GetMapping(
      path = "/fooes/{id}",
      produces = {
        MediaType.APPLICATION_JSON_VALUE
      },
      headers = "Accept-Version=vnd.foo-service.v1"
    )
    ResponseEntity<FooDTOV1> retrieveById(@PathVariable(value = "id") Long id) throws ServiceException;

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
    @GetMapping(
      path = "/fooes",
      produces = {
        MediaType.APPLICATION_JSON_VALUE
      },
      headers = "Accept-Version=vnd.foo-service.v1"
    )
    ResponseEntity<List<FooDTOV1>> retrieve(
      @RequestParam(value = "fields") String fields,
      @RequestParam(value = "filters") String filters,
      @RequestParam(value = "sort") String sort,
      @RequestParam(value = "offset") Integer offset,
      @RequestParam(value = "limit") Integer limit) throws ServiceException;

    /**
     *
     * @param id
     * @param dto
     * @return
     * @throws ServiceException
     */
    @PutMapping(
      path = "/fooes/{id}",
      produces = {
        MediaType.APPLICATION_JSON_VALUE
      },
      headers = "Accept-Version=vnd.foo-service.v1"
    )
    ResponseEntity<Void> update(
      @PathVariable(value = "id") Long id,
      @RequestBody FooDTOV1 dto
    ) throws ServiceException;

    /**
     *
     * @param id
     * @param patchDocument
     * @return
     * @throws ServiceException
     */
    @PatchMapping(
      path = "/fooes/{id}",
      produces = {MediaType.APPLICATION_JSON_VALUE},
      consumes = {"application/json-patch+json"},
      headers = "Accept-Version=vnd.foo-service.v1"
    )
    ResponseEntity<Void> patch(@PathVariable(value = "id") Long id, @RequestBody JsonPatch patchDocument) throws ServiceException;

    /**
     *
     * @param id
     * @param mergePatchDocument
     * @return
     * @throws ServiceException
     */
    @PatchMapping(
      path = "/fooes/{id}",
      produces = {MediaType.APPLICATION_JSON_VALUE},
      consumes = {"application/merge-patch+json"},
      headers = "Accept-Version=vnd.foo-service.v1"
    )
    ResponseEntity<Void> patch(@PathVariable(value = "id") Long id, @RequestBody JsonMergePatch mergePatchDocument) throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    @DeleteMapping(
      path = "/fooes/{id}",
      produces = {
        MediaType.APPLICATION_JSON_VALUE
      },
      headers = "Accept-Version=vnd.foo-service.v1"
    )
    ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) throws ServiceException;
  }
}
