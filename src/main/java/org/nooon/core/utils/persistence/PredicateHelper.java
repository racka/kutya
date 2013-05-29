package org.nooon.core.utils.persistence;

import org.nooon.core.dao.JpaDAO;
import org.nooon.core.model.entity.base.BaseEntity;

import javax.ejb.*;
import javax.persistence.FlushModeType;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.Attribute;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PredicateHelper implements Serializable {

    @EJB
    private transient JpaDAO dao;

    public <E extends BaseEntity> TypedQuery<Long> constructCountQuery(PredicateBuilder<E> predicateBuilder, Class<E> entityClass) {
        CriteriaBuilder criteriaBuilder = dao.getCriteriaBuilder();
        CriteriaQuery<Long> countingQuery = criteriaBuilder.createQuery(Long.class);
        Root from = countingQuery.from(entityClass);
        countingQuery.select(criteriaBuilder.count(from));
        if (predicateBuilder != null) {
            countingQuery.where(PredicateBuilder.createTypeless(predicateBuilder, criteriaBuilder, countingQuery, from));
        }

        return dao.createQuery(countingQuery);
    }

    public <E extends BaseEntity> TypedQuery<E> constructSelectQuery(PredicateBuilder<E> predicateBuilder, Class<E> entityClass) {
        return constructSelectQuery(predicateBuilder, null, entityClass);
    }

    public <E extends BaseEntity> TypedQuery<E> constructSelectQuery(PredicateBuilder<E> predicateBuilder, OrderBuilder<E> order, Class<E> entityClass) {
        CriteriaBuilder criteriaBuilder = dao.getCriteriaBuilder();
        CriteriaQuery<E> selectingQuery = criteriaBuilder.createQuery(entityClass);
        Root<E> from = selectingQuery.from(entityClass);
        selectingQuery.select(from);

        if (predicateBuilder != null) {
            selectingQuery.where(predicateBuilder.create(criteriaBuilder, selectingQuery, from));
        }

        if (order != null) {
            selectingQuery.orderBy(order.create(criteriaBuilder, from));
        }

        return dao.createQuery(selectingQuery).setFlushMode(FlushModeType.COMMIT);
    }

    public <E extends BaseEntity> TypedQuery<E> constructSelectQuery(PredicateBuilder<E> predicateBuilder, Class<E> entityClass,
                                                                     OrderBuilder<E> order, int start, int count) {

        TypedQuery<E> selectQuery = constructSelectQuery(predicateBuilder, order, entityClass);
        selectQuery.setFirstResult(start);
        selectQuery.setMaxResults(count);
        return selectQuery;
    }


    public <E extends BaseEntity> List<Tuple> selectFields(List<Attribute> fields, PredicateBuilder predicateBuilder,
                                                           Class<E> entityClass) {
        CriteriaBuilder criteriaBuilder = dao.getCriteriaBuilder();
        CriteriaQuery<Tuple> selectingQuery = criteriaBuilder.createTupleQuery();
        Root<E> from = selectingQuery.from(entityClass);
        List<Selection> selections = new ArrayList<Selection>();
        for (Attribute field : fields) {
            if (field != null) {
                selections.add(from.get(field.getName()));
            }
        }

        if (!selections.isEmpty()) {

            selectingQuery.select(criteriaBuilder.tuple(selections.toArray(new Selection[selections.size()])));

            if (predicateBuilder != null) {
                selectingQuery.where(PredicateBuilder.createTypeless(predicateBuilder, criteriaBuilder, selectingQuery, from));
            }

            return dao.createQuery(selectingQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
        } else {
            return new ArrayList<Tuple>();
        }

    }
}
