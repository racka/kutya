package org.nooon.kutya.ui.pages.work;

import com.vaadin.addon.jpacontainer.JPAContainer;
import org.nooon.core.model.entity.Work;
import org.nooon.core.model.entity.Work_;
import org.nooon.kutya.ui.template.entity.EntityTable;

public class WorkTable extends EntityTable<Work> {

    public WorkTable(JPAContainer<Work> dataSource) {
        super(dataSource);
    }

    @Override
    public void attach() {
        setVisibleColumns(new String[] {Work_.damage.getName(), Work_.doing.getName()});
        super.attach();
    }
}
