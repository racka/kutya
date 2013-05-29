package org.nooon.core.utils.persistence;

import org.nooon.core.model.entity.base.BaseEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Order;

public abstract class OrderBuilder<E extends BaseEntity> {

    public abstract Order create(CriteriaBuilder cb, From<E, E> from);

}
