package org.nooon.core.model.provider;


import org.nooon.core.model.entity.base.BaseEntity;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
@Target({ TYPE, METHOD, FIELD, PARAMETER })
public @interface EntityType {

    @Nonbinding
    Class<? extends BaseEntity> entityClass() default BaseEntity.class;
}
