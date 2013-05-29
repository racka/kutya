package org.nooon.core.model.provider;

import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.addon.jpacontainer.provider.CachingLocalEntityProvider;
import org.nooon.core.model.entity.base.BaseEntity;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.lang.annotation.Annotation;

@RequestScoped
public class EntityProviderFactory {

    @Inject
    @KutyaDatabase
    private EntityManager entityManager;

    @Produces
    @EntityType
    public EntityProvider getEntityProvider(InjectionPoint injectionPoint) {

        Annotation entityType = injectionPoint.getAnnotated().getAnnotation(EntityType.class);
        Class<? extends BaseEntity> clazz = entityType != null ? ((EntityType) entityType).entityClass() : null;

        if (clazz != null && !BaseEntity.class.equals(clazz)) {
            return new CachingLocalEntityProvider(clazz, entityManager);
        }


        throw new IllegalArgumentException("Illegal injection at " + injectionPoint.getAnnotated().getBaseType().toString());
    }


}
