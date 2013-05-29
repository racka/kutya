package org.nooon.kutya.ui.pages.work;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import org.nooon.core.model.entity.Work;
import org.nooon.core.model.entity.Worksheet;
import org.nooon.core.utils.persistence.PredicateBuilder;
import org.nooon.kutya.ui.template.entity.DetailField;

public class WorkDetailField extends DetailField<Work> {

    private Worksheet worksheet;

    public WorkDetailField(Worksheet worksheet, JPAContainer<Work> sourceContainer, PredicateBuilder<Work> initialEntitySelectFilter) {
        super(sourceContainer, initialEntitySelectFilter);
        this.worksheet = worksheet;
    }

    @Override
    protected Table createTable(JPAContainer<Work> targetContainer) {
        return new WorkTable(targetContainer);
    }

    @Override
    protected Window createSelectPopup() {
        return new WorkSelectPopup(getText("work.select"), worksheet, sourceContainer, new BeanItem<Work>(new Work()));
    }
}
