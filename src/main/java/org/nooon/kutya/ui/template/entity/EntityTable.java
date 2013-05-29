package org.nooon.kutya.ui.template.entity;

import com.vaadin.addon.jpacontainer.JPAContainer;
import org.nooon.core.model.entity.base.BaseEntity;
import org.nooon.kutya.ui.template.CdiTable;

public class EntityTable<E extends BaseEntity> extends CdiTable {

    public EntityTable(JPAContainer<E> dataSource) {
        super("", dataSource);
    }

    @Override
    public int size() {
        int size = super.size();
        setCaption("(" + size + ")");
        return super.size();
    }
}
