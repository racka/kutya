package org.nooon.kutya.ui.pages.vehicle;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Property;
import org.nooon.core.model.entity.Person;
import org.nooon.core.model.entity.Vehicle;
import org.nooon.core.model.entity.Vehicle_;
import org.nooon.kutya.ui.template.entity.EntityTable;

public class VehicleTable extends EntityTable<Vehicle>{

    public VehicleTable(JPAContainer<Vehicle> dataSource) {
        super(dataSource);
    }

    @Override
    public void attach() {
        setVisibleColumns(new String[] {Vehicle_.type.getName(), Vehicle_.licenseNumber.getName(), Vehicle_.owner.getName() });
        super.attach();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected String formatPropertyValue(Object rowId, Object colId, Property<?> property) {

        if (Vehicle_.owner.getName().equals(colId)) {
            Person owner = (Person) property.getValue();
            return owner != null ? owner.toString() : "";
        }

        return super.formatPropertyValue(rowId, colId, property);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
