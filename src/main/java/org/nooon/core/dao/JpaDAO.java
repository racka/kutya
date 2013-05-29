package org.nooon.core.dao;


import org.nooon.core.model.entity.base.BaseEntity;
import org.nooon.core.model.entity.base.BaseEntity_;
import org.nooon.core.model.provider.KutyaDatabase;

import javax.ejb.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class JpaDAO implements Serializable {

    @Inject
    @KutyaDatabase
    private EntityManager entityManager;

    public <E extends BaseEntity> E save(E entity) {
        return entityManager.merge(entity);
    }

    public <E extends BaseEntity> void save(Collection<E> entities) {
        for (E entity : entities) {
            entityManager.merge(entity);
        }
    }

    public <E extends BaseEntity> void remove(E entity) {
        E entityInDb = entityManager.find((Class<E>) entity.getClass(), entity.getId());
        if (entityInDb != null) {
            entityInDb.setActive(false);
        }
    }

    public <E extends BaseEntity> void remove(String id, Class<E> clazz) {
        E entityInDb = entityManager.find(clazz, id);
        if (entityInDb != null) {
            entityInDb.setActive(false);
        }
    }

    public <E extends BaseEntity> void remove(Collection<String> ids, Class<E> clazz) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(clazz);
        Root<E> from = cq.from(clazz);

        List<E> entities = entityManager.createQuery(cq.where(
                cb.and(from.get(BaseEntity_.id).in(ids), cb.isTrue(from.get(BaseEntity_.active))))).getResultList();
        for (E entity : entities) {
            entity.setActive(false);
        }
    }


    public <E extends BaseEntity> List<E> findAll(Class<E> clazz) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(clazz);
        Root<E> from = cq.from(clazz);
        cq.select(from);
        return entityManager.createQuery(cq).getResultList();
    }

    public <E extends BaseEntity> E find(String id, Class<E> clazz) {
        return entityManager.find(clazz, id);
    }

    public <E extends BaseEntity> Collection<E> find(Collection<String> ids, Class<E> clazz) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(clazz);
        Root<E> from = cq.from(clazz);
        cq.select(from).where(cb.and(from.get(BaseEntity_.id).in(ids), cb.isTrue(from.get(BaseEntity_.active))));
        return entityManager.createQuery(cq).getResultList();
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    public TypedQuery createQuery(CriteriaQuery criteriaQuery) {
        return entityManager.createQuery(criteriaQuery);
    }

}
