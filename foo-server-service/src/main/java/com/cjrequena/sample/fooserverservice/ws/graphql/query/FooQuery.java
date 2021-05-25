package com.cjrequena.sample.fooserverservice.ws.graphql.query;

import com.cjrequena.sample.fooserverservice.dto.FooDTOV1;
import com.cjrequena.sample.fooserverservice.exception.service.DBBadRequestServiceException;
import com.cjrequena.sample.fooserverservice.exception.service.DBNotFoundServiceException;
import com.cjrequena.sample.fooserverservice.exception.web.BadRequestWebException;
import com.cjrequena.sample.fooserverservice.exception.web.NotFoundWebException;
import com.cjrequena.sample.fooserverservice.service.FooServiceV1;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component
public class FooQuery implements GraphQLQueryResolver {

  private FooServiceV1 fooServiceV1;

  /**
   *
   * @param fooServiceV1
   */
  public FooQuery(FooServiceV1 fooServiceV1) {
    this.fooServiceV1 = fooServiceV1;
  }

  public Iterable<FooDTOV1> fooes(String filters, Integer offset, Integer limit) throws BadRequestWebException {
    try {
      return this.fooServiceV1.retrieve(filters, null, limit, offset).getContent();
    } catch (DBBadRequestServiceException e) {
      StringBuilder sb = new StringBuilder();
      sb.append("FooServerService::FooControllerV1::retrieve::").append(HttpStatus.BAD_REQUEST.getReasonPhrase());
      throw new BadRequestWebException(sb.toString());
    }
  }

  public FooDTOV1 fooById(Long id) throws NotFoundWebException {
    try {
      return this.fooServiceV1.retrieveById(id);
    } catch (DBNotFoundServiceException e) {
      StringBuilder sb = new StringBuilder();
      sb.append("FooServerService::FooControllerV1::retrieve::id::").append(id).append("::").append(HttpStatus.NOT_FOUND.getReasonPhrase());
      throw new NotFoundWebException(sb.toString());
    }
  }
}
