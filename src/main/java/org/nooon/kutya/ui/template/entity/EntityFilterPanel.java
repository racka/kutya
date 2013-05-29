package org.nooon.kutya.ui.template.entity;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import org.nooon.core.utils.persistence.PredicateBuilder;
import org.nooon.kutya.ui.events.EntityFilterEvent;
import org.nooon.kutya.ui.template.CdiCustomComponent;

public abstract class EntityFilterPanel extends CdiCustomComponent {

    @Override
    public void attach() {
        super.attach();
        createCompositionRoot();
    }

    public void createCompositionRoot() {

        HorizontalLayout layout = new HorizontalLayout();
        layout.addStyleName("wvis_filterpanel");
        layout.setWidth(100, Sizeable.Unit.PERCENTAGE);
        layout.setMargin(true);

        layout.addComponent(createFilterLayout());

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(true);
        buttonsLayout.setSizeUndefined();
        layout.addComponent(buttonsLayout);
        layout.setComponentAlignment(buttonsLayout, Alignment.TOP_RIGHT);

        Button filterButton = new Button(getText("buttons.filter"));
        filterButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getBlackboard().fire(new EntityFilterEvent(EntityFilterPanel.this, constructFilter()));
            }
        });
        filterButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        filterButton.addStyleName("small");
        buttonsLayout.addComponent(filterButton);

        Button clearFilterButton = new Button(getText("buttons.clear.filter"));
        clearFilterButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                clearFilters();
                getBlackboard().fire(new EntityFilterEvent(EntityFilterPanel.this, PredicateBuilder.alwaysTrue()));
            }
        });
        clearFilterButton.addStyleName("small");
        buttonsLayout.addComponent(clearFilterButton);

        setCompositionRoot(layout);
    }

    protected abstract AbstractLayout createFilterLayout();

    protected abstract PredicateBuilder constructFilter();

    protected abstract void clearFilters();

}
