package com.cjrequena.sample.fooserverservice.ws.graphql.query;

import com.cjrequena.sample.fooserverservice.dto.BooDTOV1;
import com.cjrequena.sample.fooserverservice.exception.ServiceException;
import com.cjrequena.sample.fooserverservice.service.BooServiceV1;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
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

  /**
   *
   * @param filters
   * @param offset
   * @param limit
   * @return
   * @throws ServiceException
   */
  public Iterable<BooDTOV1> booes(String filters, Integer offset, Integer limit) throws ServiceException {
    return this.booServiceV1.retrieve(filters, null, limit, offset).getContent();
  }

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  public BooDTOV1 booById(Long id) throws ServiceException {
    return this.booServiceV1.retrieveById(id);
  }
}