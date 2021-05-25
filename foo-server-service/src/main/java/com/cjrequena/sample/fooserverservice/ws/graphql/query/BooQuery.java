package com.cjrequena.sample.fooserverservice.ws.graphql.query;

import com.cjrequena.sample.fooserverservice.dto.BooDTOV1;
import com.cjrequena.sample.fooserverservice.exception.service.DBBadRequestServiceException;
import com.cjrequena.sample.fooserverservice.exception.service.DBNotFoundServiceException;
import com.cjrequena.sample.fooserverservice.exception.web.BadRequestWebException;
import com.cjrequena.sample.fooserverservice.exception.web.NotFoundWebException;
import com.cjrequena.sample.fooserverservice.service.BooServiceV1;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component
public class BooQuery implements GraphQLQueryResolver {

  private BooServiceV1 booServiceV1;

  /**
   *
   * @param booServiceV1
   */
  public BooQuery(BooServiceV1 booServiceV1) {
    this.booServiceV1 = booServiceV1;
  }

  public Iterable<BooDTOV1> booes(String filters, Integer offset, Integer limit) throws BadRequestWebException {
    try {
      return this.booServiceV1.retrieve(filters, null, limit, offset).getContent();
    } catch (DBBadRequestServiceException e) {
      StringBuilder sb = new StringBuilder();
      sb.append("FooServerService::FooControllerV1::retrieve::").append(HttpStatus.BAD_REQUEST.getReasonPhrase());
      throw new BadRequestWebException(sb.toString());
    }
  }

  public BooDTOV1 booById(Long id) throws NotFoundWebException {
    try {
      return this.booServiceV1.retrieveById(id);
    } catch (DBNotFoundServiceException e) {
      StringBuilder sb = new StringBuilder();
      sb.append("FooServerService::FooControllerV1::retrieve::id::").append(id).append("::").append(HttpStatus.NOT_FOUND.getReasonPhrase());
      throw new NotFoundWebException(sb.toString());
    }
  }
}
