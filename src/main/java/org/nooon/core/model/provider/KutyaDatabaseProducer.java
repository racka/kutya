package org.nooon.core.model.provider;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class KutyaDatabaseProducer {

    @Produces
    @KutyaDatabase
    @PersistenceContext(unitName = "kutya_pu")
    private EntityManager em;
}
