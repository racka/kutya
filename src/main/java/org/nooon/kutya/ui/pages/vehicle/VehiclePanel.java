package org.nooon.kutya.ui.pages.vehicle;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import org.nooon.core.model.entity.Vehicle;
import org.nooon.kutya.ui.template.entity.EntityPanel;

public class VehiclePanel extends EntityPanel<Vehicle>{

    public VehiclePanel() {
        super(Vehicle.class);
    }

    @Override
    protected Table createEntityTable(JPAContainer<Vehicle> container) {
        return new VehicleTable(container);
    }

    @Override
    protected Window createEntityEditWindow(JPAContainer<Vehicle> container, Object itemID) {
        return new VehicleEditPopup(getText("vehicle.edit"), container, itemID);
    }
}
