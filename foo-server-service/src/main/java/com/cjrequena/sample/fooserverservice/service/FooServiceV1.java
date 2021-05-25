package com.cjrequena.sample.fooserverservice.service;

import com.cjrequena.sample.fooserverservice.common.patch.PatchHelper;
import com.cjrequena.sample.fooserverservice.db.OffsetLimitRequestBuilder;
import com.cjrequena.sample.fooserverservice.db.entity.FooEntity;
import com.cjrequena.sample.fooserverservice.db.repository.FooRepository;
import com.cjrequena.sample.fooserverservice.db.rsql.CustomRsqlVisitor;
import com.cjrequena.sample.fooserverservice.db.rsql.RsqlSearchOperation;
import com.cjrequena.sample.fooserverservice.dto.FooDTOV1;
import com.cjrequena.sample.fooserverservice.exception.service.DBBadRequestServiceException;
import com.cjrequena.sample.fooserverservice.exception.service.DBConflictServiceException;
import com.cjrequena.sample.fooserverservice.exception.service.DBNotFoundServiceException;
import com.cjrequena.sample.fooserverservice.mapper.FooDtoEntityMapper;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
 * @author
 * @version 1.0
 * @see
 * @since JDK1.8
 */
@Log4j2
@Service
@Transactional
public class FooServiceV1 {

  private FooDtoEntityMapper fooDtoEntityMapper;
  private FooRepository fooRepository;
  private ApplicationEventPublisher eventPublisher;
  private final PatchHelper patchHelper;

  /**
   *
   * @param fooRepository
   */
  @Autowired
  public FooServiceV1(FooRepository fooRepository, FooDtoEntityMapper fooDtoEntityMapper, ApplicationEventPublisher eventPublisher, PatchHelper patchHelper) {
    this.fooRepository = fooRepository;
    this.fooDtoEntityMapper = fooDtoEntityMapper;
    this.eventPublisher = eventPublisher;
    this.patchHelper = patchHelper;
  }

  public FooDTOV1 create(FooDTOV1 dto) throws DBConflictServiceException {
    final Optional<FooEntity> optionalEntity = this.fooRepository.findByName(dto.getName());
    if (optionalEntity.isPresent()) {
      throw new DBConflictServiceException();
    } else {
      FooEntity entity = fooDtoEntityMapper.toEntity(dto);
      entity = fooRepository.saveAndFlush(entity);
      dto = fooDtoEntityMapper.toDTO(entity);
      return dto;
    }
  }

  public FooDTOV1 retrieveById(Long id) throws DBNotFoundServiceException {
    //--
    Optional<FooEntity> entity = this.fooRepository.findById(id);
    if (!entity.isPresent()) {
      throw new DBNotFoundServiceException();
    }
    return fooDtoEntityMapper.toDTO(entity.get());
    //--
  }

  public Page retrieve(String search, String sort, Integer offset, Integer limit) throws DBBadRequestServiceException {
    //--
    try {
      Page<FooEntity> page;
      Specification<FooEntity> specification;
      Pageable pageable = OffsetLimitRequestBuilder.create(offset, limit, sort);
      if (search != null) {
        Node rootNode = new RSQLParser(RsqlSearchOperation.defaultOperators()).parse(search);
        specification = rootNode.accept(new CustomRsqlVisitor<>());
        page = this.fooRepository.findAll(specification, pageable);
      } else {
        page = this.fooRepository.findAll(pageable);
      }
      return page.map(entity -> fooDtoEntityMapper.toDTO(entity));
    } catch (PropertyReferenceException ex) {
      log.error("{}", ex.getMessage());
      throw new DBBadRequestServiceException();
    }
    //--
  }

  public FooDTOV1 update(FooDTOV1 dto) throws DBNotFoundServiceException {
    Optional<FooEntity> optionalEntity = fooRepository.findById(dto.getId());
    optionalEntity.ifPresent(
      entity -> {
        entity = fooDtoEntityMapper.toEntity(dto);
        fooRepository.saveAndFlush(entity);
        log.debug("Updated User: {}", entity);
      }
    );
    optionalEntity.orElseThrow(() -> new DBNotFoundServiceException());
    return fooDtoEntityMapper.toDTO(optionalEntity.get());
  }

  public FooDTOV1 patch(Long id, JsonPatch patchDocument) throws DBNotFoundServiceException {
    // --
    FooDTOV1 originalDTO = retrieveById(id);
    if (originalDTO == null) {
      throw new DBNotFoundServiceException();
    }
    FooEntity entity = fooDtoEntityMapper.toEntity(originalDTO);
    FooDTOV1 dtoPatched = patchHelper.patch(patchDocument, originalDTO, FooDTOV1.class);
    fooDtoEntityMapper.toEntity(dtoPatched, entity);
    this.fooRepository.save(entity);
    return dtoPatched;
  }

  public FooDTOV1 patch(Long id, JsonMergePatch mergePatchDocument) throws DBNotFoundServiceException {
    FooDTOV1 fooDTO = retrieveById(id);
    if (fooDTO == null) {
      throw new DBNotFoundServiceException();
    }
    FooEntity entity = fooDtoEntityMapper.toEntity(fooDTO);
    FooDTOV1 dtoMergePatched = patchHelper.mergePatch(mergePatchDocument, fooDTO, FooDTOV1.class);
    fooDtoEntityMapper.toEntity(dtoMergePatched, entity);
    this.fooRepository.save(entity);
    return dtoMergePatched;
  }

  public void delete(Long id) throws DBNotFoundServiceException {
    Optional<FooEntity> optionalEntity = fooRepository.findById(id);
    optionalEntity.ifPresent(
      foo -> {
        fooRepository.delete(foo);
        log.debug("Deleted User: {}", foo);
      }
    );
    optionalEntity.orElseThrow(() -> new DBNotFoundServiceException());
  }
}
