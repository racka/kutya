package org.nooon.kutya.ui.pages.person;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import org.nooon.core.model.entity.Person;
import org.nooon.core.model.entity.Person_;
import org.nooon.kutya.ui.template.entity.EntityEditPopup;

public class PersonEditPopup extends EntityEditPopup<Person> {

    public PersonEditPopup(String caption, JPAContainer<Person> container, Object itemID) {
        super(caption, container, itemID);
    }

    @Override
    protected void createBinding(Layout formLayout) {

        binder = new BeanFieldGroup<Person>(Person.class);
        binder.setBuffered(true);
        binder.setItemDataSource(
                String.class.equals(itemID.getClass())
                        ? new BeanItem<Person>(sourceContainer.getItem(itemID).getEntity())
                        : ((BeanItem<Person>) itemID));

        TextField nameField = new TextField(getText("person.firstName"));
        nameField.setNullRepresentation("");
        formLayout.addComponent(nameField);
        binder.bind(nameField, Person_.firstName.getName());

        TextField familyNameField = new TextField(getText("person.familyName"));
        familyNameField.setNullRepresentation("");
        formLayout.addComponent(familyNameField);
        binder.bind(familyNameField, Person_.familyName.getName());

    }
}
