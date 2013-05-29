package org.nooon.core.utils.persistence;

import org.nooon.core.model.entity.base.BaseEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.NoResultException;
import javax.persistence.Tuple;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.Attribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BeanHelper {

    @EJB
    private PredicateHelper predicateHelper;

    @SuppressWarnings("unchecked")
    public <E extends BaseEntity> List<Tuple> getBeanAttributes(PredicateBuilder<E> filter, List<Attribute> attributes, Class<E> entityClass) {
        return predicateHelper.selectFields(attributes, toActiveFilter(filter, entityClass), attributes.get(0).getDeclaringType().getJavaType());
    }

    public <E extends BaseEntity> List getBeanAttribute(PredicateBuilder<E> filter, Attribute attribute, Class<E> entityClass) {

        List<Tuple> tupleResult = getBeanAttributes(toActiveFilter(filter, entityClass), Arrays.asList(attribute), entityClass);

        List<Object> result = new ArrayList<Object>();
        for (Tuple tuple : tupleResult) {
            result.add(tuple.get(0));
        }

        return result;
    }

    public <E extends BaseEntity> List<E> getActiveBeans(Class<E> entityClass) {
        return getActiveBeans(null, entityClass);
    }


    public <E extends BaseEntity> E getActiveBean(final PredicateBuilder<E> predicateBuilder, final Class<E> entityClass) {
        return getBean(new PredicateBuilder<E>() {
            @Override
            public Predicate create(CriteriaBuilder cb, AbstractQuery<E> cq, From<E, E> from) {
                return predicateBuilder != null
                        ? cb.and(predicateBuilder.create(cb, cq, from), PredicateBuilder.activeFilter(entityClass).create(cb, cq, from))
                        : PredicateBuilder.activeFilter(entityClass).create(cb, cq, from);
            }
        }, entityClass);
    }


    public <E extends BaseEntity> List<E> getActiveBeans(final PredicateBuilder<E> predicateBuilder, final Class<E> entityClass) {
        return getBeans(new PredicateBuilder<E>() {
            @Override
            public Predicate create(CriteriaBuilder cb, AbstractQuery<E> cq, From<E, E> from) {
                return predicateBuilder != null
                        ? cb.and(predicateBuilder.create(cb, cq, from), PredicateBuilder.activeFilter(entityClass).create(cb, cq, from))
                        : PredicateBuilder.activeFilter(entityClass).create(cb, cq, from);
            }
        }, entityClass);
    }

    public <E extends BaseEntity> List<E> getActiveBeans(final PredicateBuilder<E> predicateBuilder, OrderBuilder<E> order, final Class<E> entityClass) {
        return getBeans(new PredicateBuilder<E>() {
            @Override
            public Predicate create(CriteriaBuilder cb, AbstractQuery<E> cq, From<E, E> from) {
                return predicateBuilder != null
                        ? cb.and(predicateBuilder.create(cb, cq, from), PredicateBuilder.activeFilter(entityClass).create(cb, cq, from))
                        : PredicateBuilder.activeFilter(entityClass).create(cb, cq, from);
            }
        }, order, entityClass);
    }

    public <E extends BaseEntity> List<E> getActiveBeans(final PredicateBuilder<E> predicateBuilder, OrderBuilder<E> order, final Class<E> entityClass,
                                                         int start, int count) {
        return getBeans(new PredicateBuilder<E>() {
            @Override
            public Predicate create(CriteriaBuilder cb, AbstractQuery<E> cq, From<E, E> from) {
                return predicateBuilder != null
                        ? cb.and(predicateBuilder.create(cb, cq, from), PredicateBuilder.activeFilter(entityClass).create(cb, cq, from))
                        : PredicateBuilder.activeFilter(entityClass).create(cb, cq, from);
            }
        }, order, entityClass, start, count);
    }

    public  <E extends BaseEntity> E getBean(PredicateBuilder<E> predicateBuilder, Class<E> entityClass) {
        try {
            return predicateHelper.constructSelectQuery(predicateBuilder, entityClass).getSingleResult();
        } catch (NoResultException ignored) {
            return null;
        }
    }

    public  <E extends BaseEntity> List<E> getBeans(PredicateBuilder<E> predicateBuilder, OrderBuilder<E> order, Class<E> entityClass, int start, int count) {
        return predicateHelper.constructSelectQuery(predicateBuilder, entityClass, order, start, count).getResultList();
    }

    public  <E extends BaseEntity> List<E> getBeans(PredicateBuilder<E> predicateBuilder, OrderBuilder<E> order, Class<E> entityClass) {
        return predicateHelper.constructSelectQuery(predicateBuilder, order, entityClass).getResultList();
    }

    public  <E extends BaseEntity> List<E> getBeans(Class<E> entityClass) {
        return getBeans(null, null, entityClass);
    }

    public  <E extends BaseEntity> List<E> getBeans(PredicateBuilder<E> predicateBuilder, Class<E> entityClass) {
        return getBeans(predicateBuilder, null, entityClass);
    }

    public  <E extends BaseEntity> List<E> getBeans(PredicateBuilder<E> predicateBuilder, Class<E> entityClass, int start, int count) {
        return getBeans(predicateBuilder, null, entityClass, start, count);
    }


    public <E extends BaseEntity> E findById(final String id, Class<E> entityClass) {
        List<E> resultList = getActiveBeans(PredicateBuilder.idFilter(id, entityClass), entityClass);
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        }

        return null;
    }

    public <E extends BaseEntity> int countBeans(Class<E> entityClass) {
        return countBeans(null, entityClass);
    }

    public <E extends BaseEntity> int countBeans(PredicateBuilder<E> predicateBuilder, Class<E> entityClass) {
        return predicateHelper.constructCountQuery(toActiveFilter(predicateBuilder, entityClass), entityClass).getResultList().get(0).intValue();
    }


    private <E extends BaseEntity> PredicateBuilder<E> toActiveFilter(final PredicateBuilder<E> predicateBuilder, final Class<E> entityClass) {
        return new PredicateBuilder<E>() {
            @Override
            public Predicate create(CriteriaBuilder cb, AbstractQuery<E> cq, From<E, E> from) {
                return predicateBuilder != null
                        ? cb.and(predicateBuilder.create(cb, cq, from), PredicateBuilder.activeFilter(entityClass).create(cb, cq, from))
                        : PredicateBuilder.activeFilter(entityClass).create(cb, cq, from);
            }
        };
    }

}
