package org.nooon.kutya.ui.pages.person;

import com.vaadin.addon.jpacontainer.JPAContainer;
import org.nooon.core.model.entity.Person;
import org.nooon.core.model.entity.Person_;
import org.nooon.kutya.ui.template.entity.EntityTable;

public class PersonTable extends EntityTable<Person> {

    public PersonTable(JPAContainer<Person> dataSource) {
        super(dataSource);
    }

    @Override
    public void attach() {
        setVisibleColumns(new String[] {Person_.firstName.getName(), Person_.familyName.getName()});
        super.attach();
    }
}
