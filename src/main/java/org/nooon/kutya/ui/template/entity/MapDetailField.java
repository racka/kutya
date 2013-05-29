package org.nooon.kutya.ui.template.entity;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import org.nooon.kutya.ui.events.FormDiscardEvent;
import org.nooon.kutya.ui.template.CdiCustomField;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class MapDetailField<M extends Map> extends CdiCustomField implements
    FormDiscardEvent.FormDiscardEventListener {

    protected M sourceMap;
    protected Container container;
    protected Table table;
    protected Window addPopup;


    public MapDetailField(M sourceMap) {
        this.sourceMap = sourceMap;
    }

    @Override
    public void attach() {
        super.attach();
        getBlackboard().addListener(this);
    }

    @Override
    public void detach() {
        getBlackboard().removeListener(this);
        super.detach();
    }

    @Override
    public Object getValue() {
        sourceMap.clear();
        for (Object itemID : container.getItemIds()) {
            sourceMap.put(container.getItem(itemID).getItemProperty(PROP1), container.getItem(itemID).getItemProperty(PROP2));
        }
        return sourceMap;
    }

    @Override
    public Class getType() {
        return Map.class;
    }

    @Override
    protected Component initContent() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        mainLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);

        HorizontalLayout addRemoveLayout = new HorizontalLayout();
        addRemoveLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
        mainLayout.addComponent(addRemoveLayout);

        AbstractLayout createdButtons = createButtonLayout();
        addRemoveLayout.addComponent(createdButtons);
        addRemoveLayout.setComponentAlignment(createdButtons, Alignment.TOP_RIGHT);

        container = createContainer(sourceMap);
        table = createTable();
        table.setWidth(100, Sizeable.Unit.PERCENTAGE);
        table.setHeight(150, Sizeable.Unit.PIXELS);
        table.setSelectable(true);
        table.setMultiSelect(true);
        table.setEditable(false);
        table.setPageLength(20);
        mainLayout.addComponent(table);
        return mainLayout;

    }

    protected static final String PROP1 = "key";
    protected static final String PROP2 = "value";

    protected Container createContainer(M sourceMap) {
        Container container = new IndexedContainer();
        container.addContainerProperty(PROP1, String.class, "");
        container.addContainerProperty(PROP2, String.class, "");

        for (Map.Entry entry : (Set<Map.Entry>)sourceMap.entrySet()) {
            Item item = container.addItem(entry.getKey());
            item.getItemProperty(PROP1).setValue(entry.getKey().toString());
            item.getItemProperty(PROP2).setValue(entry.getValue().toString());
        }

        return container;
    }

    protected Table createTable() {
        Table table = new Table("", container);
        table.setVisibleColumns(new String[] {PROP1, PROP2});
        return table;
    }

    protected AbstractLayout createButtonLayout() {

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeUndefined();
        layout.setSpacing(true);

        layout.addComponent(constructAddButton());
        layout.addComponent(constructRemoveButton());

        return layout;
    }

    protected abstract Window createSelectPopup();


    protected Button constructAddButton() {
        Button addButton = new Button(getText("buttons.addSign"));
        addButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                addPopup = createSelectPopup();
                addPopup.setModal(true);
                addPopup.center();
                getUI().addWindow(addPopup);
            }

        });

        return addButton;
    }

    protected Button constructRemoveButton() {
        Button removeButton = new Button(getText("buttons.removeSign"));
        removeButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (table.getValue() instanceof Collection) {
                    for (Object itemID : (Collection)table.getValue()) {
                        container.removeItem(itemID);
                    }
                } else {
                    container.removeItem(table.getValue());
                }
            }
        });
        return removeButton;
    }


    @Override
    public void receiveEvent(FormDiscardEvent event) {
        container = createContainer(sourceMap);
    }
}
