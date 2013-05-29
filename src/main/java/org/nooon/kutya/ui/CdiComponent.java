package org.nooon.kutya.ui;

import com.github.wolfie.blackboard.Blackboard;
import com.vaadin.addon.jpacontainer.EntityProvider;
import org.nooon.core.dao.JpaDAO;
import org.nooon.core.utils.persistence.BeanHelper;

public interface CdiComponent {

    String getText(String key);

    JpaDAO getJpaDAO();

    BeanHelper getBeanHelper();

    EntityProvider getEntityProvider(Class clazz);

    Blackboard getBlackboard();

}
