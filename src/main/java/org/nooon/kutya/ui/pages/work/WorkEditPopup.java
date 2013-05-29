package org.nooon.kutya.ui.pages.work;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import org.nooon.core.model.entity.Work;
import org.nooon.core.model.entity.Work_;
import org.nooon.kutya.ui.template.entity.EntityEditPopup;

public class WorkEditPopup extends EntityEditPopup<Work> {
    
    public WorkEditPopup(String caption, JPAContainer<Work> container, Object itemID) {
        super(caption, container, itemID);
    }

    @Override
    protected void createBinding(Layout formLayout) {
        binder = new BeanFieldGroup<Work>(Work.class);
        binder.setBuffered(true);
        binder.setItemDataSource(
                String.class.equals(itemID.getClass())
                        ? new BeanItem<Work>(sourceContainer.getItem(itemID).getEntity())
                        : ((BeanItem<Work>) itemID));

        TextField damageField = new TextField(getText("work.damage"));
        damageField.setNullRepresentation("");
        formLayout.addComponent(damageField);
        binder.bind(damageField, Work_.damage.getName());

        TextField doingField = new TextField(getText("work.doing"));
        doingField.setNullRepresentation("");
        formLayout.addComponent(doingField);
        binder.bind(doingField, Work_.doing.getName());
    }
}
