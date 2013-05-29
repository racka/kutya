package org.nooon.kutya.ui.template;

import com.github.wolfie.blackboard.Blackboard;
import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.ui.ComboBox;
import org.nooon.core.dao.JpaDAO;
import org.nooon.core.utils.persistence.BeanHelper;
import org.nooon.kutya.ui.CdiComponent;

public class CdiComboBox extends ComboBox implements CdiComponent {

    public CdiComboBox(String caption) {
        super(caption);
    }

    public String getText(String key) {
        return ((CdiComponent)getUI()).getText(key);
    }

    public Blackboard getBlackboard() {
        return ((CdiComponent)getUI()).getBlackboard();
    }

    public JpaDAO getJpaDAO() {
        return ((CdiComponent)getUI()).getJpaDAO();
    }

    public BeanHelper getBeanHelper() {
        return ((CdiComponent)getUI()).getBeanHelper();
    }

    public EntityProvider getEntityProvider(Class clazz) {
        return ((CdiComponent)getUI()).getEntityProvider(clazz);
    }

}
