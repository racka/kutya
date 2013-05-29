package org.nooon.kutya.ui.pages.worksheet;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import org.nooon.core.model.entity.Worksheet;
import org.nooon.kutya.ui.template.entity.EntityPanel;

public class WorksheetPanel extends EntityPanel<Worksheet> {

    public WorksheetPanel() {
        super(Worksheet.class);
    }

    @Override
    protected Table createEntityTable(JPAContainer<Worksheet> container) {
        return new WorksheetTable(container);
    }

    @Override
    protected Window createEntityEditWindow(JPAContainer<Worksheet> container, Object itemID) {
        return new WorksheetEditPopup(getText("worksheet.edit"), container, itemID);
    }
}
