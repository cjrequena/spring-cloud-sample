package com.cjrequena.sample.fooserverservice.mapper;

import com.cjrequena.sample.fooserverservice.db.entity.FooEntity;
import com.cjrequena.sample.fooserverservice.dto.FooDTOV1;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

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
@Mapper(
  componentModel = "spring",
  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface FooDtoEntityMapper extends DTOEntityMapper<FooDTOV1, FooEntity> {
}
