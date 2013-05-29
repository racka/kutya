package org.nooon.kutya.ui.pages.person;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import org.nooon.core.model.entity.Person;
import org.nooon.kutya.ui.template.entity.EntityPanel;

public class PersonPanel extends EntityPanel<Person> {

    public PersonPanel() {
        super(Person.class);
    }

    @Override
    protected Table createEntityTable(JPAContainer<Person> container) {
        return new PersonTable(container);
    }

    @Override
    protected Window createEntityEditWindow(JPAContainer<Person> container, Object itemID) {
        return new PersonEditPopup(getText("person.edit"), container, itemID);
    }
}
