package com.cjrequena.sample.fooserverservice.service;

import com.cjrequena.sample.fooserverservice.common.patch.PatchHelper;
import com.cjrequena.sample.fooserverservice.db.OffsetLimitRequestBuilder;
import com.cjrequena.sample.fooserverservice.db.entity.BooEntity;
import com.cjrequena.sample.fooserverservice.db.repository.BooRepository;
import com.cjrequena.sample.fooserverservice.db.rsql.CustomRsqlVisitor;
import com.cjrequena.sample.fooserverservice.db.rsql.RsqlSearchOperation;
import com.cjrequena.sample.fooserverservice.dto.BooDTOV1;
import com.cjrequena.sample.fooserverservice.exception.service.DBBadRequestServiceException;
import com.cjrequena.sample.fooserverservice.exception.service.DBConflictServiceException;
import com.cjrequena.sample.fooserverservice.exception.service.DBConstraintViolationServiceException;
import com.cjrequena.sample.fooserverservice.exception.service.DBNotFoundServiceException;
import com.cjrequena.sample.fooserverservice.mapper.BooDtoEntityMapper;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.json.JsonMergePatch;
import javax.json.JsonPatch;
import java.util.Optional;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author cjrequena
 */
@Log4j2
@Service
@Transactional
public class BooServiceV1 {

  private BooDtoEntityMapper booDtoEntityMapper;
  private BooRepository booRepository;
  private ApplicationEventPublisher eventPublisher;
  private final PatchHelper patchHelper;

  /**
   *
   * @param booRepository
   */
  @Autowired
  public BooServiceV1(BooRepository booRepository, BooDtoEntityMapper booDtoEntityMapper, ApplicationEventPublisher eventPublisher, PatchHelper patchHelper) {
    this.booRepository = booRepository;
    this.booDtoEntityMapper = booDtoEntityMapper;
    this.eventPublisher = eventPublisher;
    this.patchHelper = patchHelper;
  }

  /**
   *
   * @param dto
   * @return
   * @throws DBConstraintViolationServiceException
   */
  public BooDTOV1 create(BooDTOV1 dto) throws DBConstraintViolationServiceException, DBConflictServiceException {
    final Optional<BooEntity> optionalEntity = this.booRepository.findByName(dto.getName());
    if (optionalEntity.isPresent()) {
      throw new DBConflictServiceException();
    } else {
      BooEntity entity = booDtoEntityMapper.toEntity(dto);
      try {
        entity = booRepository.saveAndFlush(entity);
      } catch (DataIntegrityViolationException ex) {
        if ((ex.getCause() != null) && (ex.getCause() instanceof ConstraintViolationException)) {
          throw new DBConstraintViolationServiceException();
        }
      }
      dto = booDtoEntityMapper.toDTO(entity);
      return dto;
    }
  }

  public BooDTOV1 retrieveById(Long id) throws DBNotFoundServiceException {
    //--
    Optional<BooEntity> entity = this.booRepository.findById(id);
    if (!entity.isPresent()) {
      throw new DBNotFoundServiceException();
    }
    return booDtoEntityMapper.toDTO(entity.get());
    //--
  }

  public Page retrieve(String search, String sort, Integer offset, Integer limit) throws DBBadRequestServiceException {
    //--
    try {
      Page<BooEntity> page;
      Specification<BooEntity> specification;
      Pageable pageable = OffsetLimitRequestBuilder.create(offset, limit, sort);
      if (search != null) {
        Node rootNode = new RSQLParser(RsqlSearchOperation.defaultOperators()).parse(search);
        specification = rootNode.accept(new CustomRsqlVisitor<>());
        page = this.booRepository.findAll(specification, pageable);
      } else {
        page = this.booRepository.findAll(pageable);
      }
      return page.map(entity -> booDtoEntityMapper.toDTO(entity));
    } catch (PropertyReferenceException ex) {
      log.error("{}", ex.getMessage());
      throw new DBBadRequestServiceException();
    }
    //--
  }

  public BooDTOV1 update(BooDTOV1 dto) throws DBNotFoundServiceException {
    Optional<BooEntity> optionalEntity = booRepository.findById(dto.getId());
    optionalEntity.ifPresent(
      entity -> {
        entity = booDtoEntityMapper.toEntity(dto);
        booRepository.saveAndFlush(entity);
        log.debug("Updated User: {}", entity);
      }
    );
    optionalEntity.orElseThrow(() -> new DBNotFoundServiceException());
    return booDtoEntityMapper.toDTO(optionalEntity.get());
  }

  public BooDTOV1 patch(Long id, JsonPatch patchDocument) throws DBNotFoundServiceException {
    // --
    BooDTOV1 originalDTO = retrieveById(id);
    if (originalDTO == null) {
      throw new DBNotFoundServiceException();
    }
    BooEntity entity = booDtoEntityMapper.toEntity(originalDTO);
    BooDTOV1 dtoPatched = patchHelper.patch(patchDocument, originalDTO, BooDTOV1.class);
    booDtoEntityMapper.toEntity(dtoPatched, entity);
    this.booRepository.save(entity);
    return dtoPatched;
  }

  public BooDTOV1 patch(Long id, JsonMergePatch mergePatchDocument) throws DBNotFoundServiceException {
    BooDTOV1 booDTO = retrieveById(id);
    if (booDTO == null) {
      throw new DBNotFoundServiceException();
    }
    BooEntity entity = booDtoEntityMapper.toEntity(booDTO);
    BooDTOV1 dtoMergePatched = patchHelper.mergePatch(mergePatchDocument, booDTO, BooDTOV1.class);
    booDtoEntityMapper.toEntity(dtoMergePatched, entity);
    this.booRepository.save(entity);
    return dtoMergePatched;
  }

  public void delete(Long id) throws DBNotFoundServiceException {
    Optional<BooEntity> optionalEntity = booRepository.findById(id);
    optionalEntity.ifPresent(
      foo -> {
        booRepository.delete(foo);
        log.debug("Deleted User: {}", foo);
      }
    );
    optionalEntity.orElseThrow(() -> new DBNotFoundServiceException());
  }
}
