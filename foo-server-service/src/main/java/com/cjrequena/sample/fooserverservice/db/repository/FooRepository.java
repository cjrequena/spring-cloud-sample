package com.cjrequena.sample.fooserverservice.db.repository;

import com.cjrequena.sample.fooserverservice.db.entity.FooEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 * @author cjrequena
 * @version 1.0
 * @since JDK1.8
 * @see
 *
 */
@Repository
public interface FooRepository extends JpaRepository<FooEntity, Long>, JpaSpecificationExecutor<FooEntity> {
  Optional<FooEntity> findByName(String name);
}
