package org.nooon.kutya.ui.pages.vehicle;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import org.nooon.core.model.entity.Vehicle;
import org.nooon.core.model.entity.Vehicle_;
import org.nooon.kutya.ui.template.entity.EntityEditPopup;

public class VehicleEditPopup extends EntityEditPopup<Vehicle> {

    public VehicleEditPopup(String caption, JPAContainer<Vehicle> container, Object itemID) {
        super(caption, container, itemID);
    }

    @Override
    protected void createBinding(Layout formLayout) {
        binder = new BeanFieldGroup<Vehicle>(Vehicle.class);
        binder.setBuffered(true);
        binder.setItemDataSource(
                String.class.equals(itemID.getClass())
                        ? new BeanItem<Vehicle>(sourceContainer.getItem(itemID).getEntity())
                        : ((BeanItem<Vehicle>) itemID));

        TextField typeField = new TextField(getText("vehicle.type"));
        typeField.setNullRepresentation("");
        formLayout.addComponent(typeField);
        binder.bind(typeField, Vehicle_.type.getName());

        TextField licenseNumberField = new TextField(getText("vehicle.licenseNumber"));
        licenseNumberField.setNullRepresentation("");
        formLayout.addComponent(licenseNumberField);
        binder.bind(licenseNumberField, Vehicle_.licenseNumber.getName());

        TextField vinField = new TextField(getText("vehicle.vin"));
        vinField.setNullRepresentation("");
        formLayout.addComponent(vinField);
        binder.bind(vinField, Vehicle_.vin.getName());

        TextField engineNumberField = new TextField(getText("vehicle.engineNumber"));
        engineNumberField.setNullRepresentation("");
        formLayout.addComponent(engineNumberField);
        binder.bind(engineNumberField, Vehicle_.engineNumber.getName());



    }
}
