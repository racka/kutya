package org.nooon.kutya.ui.pages.worksheet;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Property;
import org.nooon.core.model.entity.Worksheet;
import org.nooon.core.model.entity.Worksheet_;
import org.nooon.kutya.ui.template.entity.EntityTable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WorksheetTable extends EntityTable<Worksheet> {

    public WorksheetTable(JPAContainer<Worksheet> dataSource) {
        super(dataSource);
    }

    @Override
    public void attach() {
        setVisibleColumns(new String[] {Worksheet_.name.getName(), Worksheet_.created.getName()});
        super.attach();
    }

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

    @Override
    protected String formatPropertyValue(Object rowId, Object colId, Property<?> property) {

        if (Worksheet_.created.getName().equals(colId)) {
            Calendar created = (Calendar) property.getValue();
            return created != null ? dateFormat.format(created.getTime()) : "";
        }

        return super.formatPropertyValue(rowId, colId, property);
    }
}
