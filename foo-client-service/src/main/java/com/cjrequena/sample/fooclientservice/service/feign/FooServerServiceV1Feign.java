package com.cjrequena.sample.fooclientservice.service.feign;

import com.cjrequena.sample.fooclientservice.dto.FooDTOV1;
import com.cjrequena.sample.fooclientservice.exception.service.FeignBadRequestServiceException;
import com.cjrequena.sample.fooclientservice.exception.service.FeignConflictServiceException;
import com.cjrequena.sample.fooclientservice.exception.service.FeignNotFoundServiceException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import static com.cjrequena.sample.fooclientservice.common.Constant.VND_FOO_SERVICE_V1;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author cjrequena
 * @version 1.0
 */
@FeignClient(name = "foo-server-service", url = "${foo-server-service.url}", contextId = "foo-server-service")
@RequestMapping(value = "/foo-server-service", headers = {"Accept-Version=" + VND_FOO_SERVICE_V1})
public interface FooServerServiceV1Feign {

  @PostMapping(value = "/fooes",
    produces = {
      MediaType.APPLICATION_JSON_VALUE
    }
  )
  ResponseEntity<Void> create(@RequestBody FooDTOV1 dto) throws FeignConflictServiceException, FeignBadRequestServiceException;

  @GetMapping(
    path = "/fooes/{id}",
    produces = {
      MediaType.APPLICATION_JSON_VALUE
    }
  )
  ResponseEntity<FooDTOV1> retrieveById(@PathVariable(value = "id") Long id) throws FeignNotFoundServiceException;

  @GetMapping(
    path = "/fooes",
    produces = {
      MediaType.APPLICATION_JSON_VALUE
    }
  )
  ResponseEntity<List<FooDTOV1>> retrieve(
    @RequestParam(value = "fields") String fields,
    @RequestParam(value = "filters") String filters,
    @RequestParam(value = "sort") String sort,
    @RequestParam(value = "offset") Integer offset,
    @RequestParam(value = "limit") Integer limit
  ) throws FeignBadRequestServiceException;

  @PutMapping(
    path = "/fooes/{id}",
    produces = {
      MediaType.APPLICATION_JSON_VALUE
    }
  )
  ResponseEntity<Void> update(
    @PathVariable(value = "id") Long id,
    @RequestBody FooDTOV1 dto
  ) throws FeignNotFoundServiceException;

  @PatchMapping(
    path = "/fooes/{id}",
    produces = {MediaType.APPLICATION_JSON_VALUE},
    consumes = {"application/json-patch+json"}
  )
  ResponseEntity<Void> patch(
    @PathVariable(value = "id") Long id,
    @RequestBody JsonPatch patchDocument
  ) throws FeignNotFoundServiceException;

  @PatchMapping(
    path = "/fooes/{id}",
    produces = {MediaType.APPLICATION_JSON_VALUE},
    consumes = {"application/merge-patch+json"}
  )
  ResponseEntity<Void> patch(
    @PathVariable(value = "id") Long id,
    @RequestBody JsonMergePatch mergePatchDocument
  ) throws FeignNotFoundServiceException;

  @DeleteMapping(
    path = "/fooes/{id}",
    produces = {
      MediaType.APPLICATION_JSON_VALUE
    }
  )
  ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) throws FeignNotFoundServiceException;
}
