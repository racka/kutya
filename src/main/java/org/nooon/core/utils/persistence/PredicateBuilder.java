package org.nooon.core.utils.persistence;


import org.nooon.core.model.entity.base.BaseEntity;
import org.nooon.core.model.entity.base.BaseEntity_;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.Attribute;
import java.util.Collection;
import java.util.Map;


public abstract class PredicateBuilder<E extends BaseEntity> {

    public abstract Predicate create(CriteriaBuilder cb, AbstractQuery<E> cq, From<E, E> from);

    public Map.Entry<String, String> getHint() {
        return null;
    }

    public static <E extends BaseEntity> PredicateBuilder<E> equals(final String field, final Object value) {
        return new PredicateBuilder<E>() {
            @Override
            public Predicate create(CriteriaBuilder cb, AbstractQuery<E> cq, From<E, E> from) {
                return cb.equal(from.get(field), value);
            }
        };
    }

    public static <E extends BaseEntity> PredicateBuilder<E> alwaysTrue(Class<E> entityClass) {
        return new PredicateBuilder<E>() {
            @Override
            public Predicate create(CriteriaBuilder cb, AbstractQuery<E> cq, From<E, E> from) {
                return alwaysTrue(cb);
            }

            @Override
            public String toString() {
                return "alwaysTrue";
            }

        };
    }

    public static <E extends BaseEntity> PredicateBuilder<E> alwaysTrue() {
        return alwaysTrue((Class<E>) null);
    }

    public static <E extends BaseEntity> PredicateBuilder<E> alwaysFalse(Class<E> entityClass) {
        return new PredicateBuilder<E>() {

            @Override
            public Predicate create(CriteriaBuilder cb, AbstractQuery<E> cq, From<E, E> from) {
                return cb.not(alwaysTrue(cb));
            }

            @Override
            public String toString() {
                return "alwaysFalse";
            }

        };
    }

    public static <E extends BaseEntity> PredicateBuilder<E> alwaysFalse() {
        return alwaysFalse((Class<E>) null);
    }

    public static <E extends BaseEntity> PredicateBuilder<E> activeFilter() {
        return activeFilter(null);
    }

    public static <E extends BaseEntity> PredicateBuilder<E> activeFilter(Class<E> entityClass) {
        return new PredicateBuilder<E>() {
            @Override
            public Predicate create(CriteriaBuilder cb, AbstractQuery<E> cq, From<E, E> from) {
                return cb.isTrue(from.get(BaseEntity_.active));
            }

            @Override
            public String toString() {
                return "ActiveFilter";
            }
        };
    }

    public static <E extends BaseEntity> PredicateBuilder<E> idFilter(final String id, Class<E> entityClass) {
        return new PredicateBuilder<E>() {
            @Override
            public Predicate create(CriteriaBuilder cb, AbstractQuery<E> cq, From<E, E> from) {
                return cb.equal(from.get(BaseEntity_.id), id);
            }

            @Override
            public String toString() {
                return "IDFilter";
            }
        };
    }

    public static <E extends BaseEntity> PredicateBuilder<E> idFilter(final String id) {
        return idFilter(id, null);
    }

    public static <E extends BaseEntity> PredicateBuilder nameFilter(final Attribute name, final String value) {
        return new PredicateBuilder<E>() {
            @Override
            public Predicate create(CriteriaBuilder cb, AbstractQuery<E> cq, From<E, E> from) {
                return cb.equal(from.get(name.getName()), value);
            }

            @Override
            public String toString() {
                return "NameFilter";
            }
        };
    }


    public static Predicate alwaysTrue(CriteriaBuilder cb) {
        return cb.isTrue(cb.literal(Boolean.TRUE));
    }

    public static Predicate alwaysFalse(CriteriaBuilder cb) {
        return cb.isTrue(cb.literal(Boolean.FALSE));
    }

    public static Predicate combine(CriteriaBuilder cb, Collection<Predicate> predicates) {
        if (predicates == null || predicates.isEmpty()) {
            return alwaysTrue(cb);
        } else if (predicates.size() == 1) {
            return predicates.iterator().next();
        } else {
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }
    }

    @SuppressWarnings("unchecked")
    public static Predicate createTypeless(PredicateBuilder predicateBuilder, CriteriaBuilder cb, AbstractQuery cq, From from) {
        return predicateBuilder.create(cb, cq, from);
    }

    @Override
    public String toString() {
        return "";
    }
}
