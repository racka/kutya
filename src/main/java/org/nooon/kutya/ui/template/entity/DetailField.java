package org.nooon.kutya.ui.template.entity;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import org.nooon.core.model.entity.base.BaseEntity;
import org.nooon.core.model.entity.base.BaseEntity_;
import org.nooon.core.utils.persistence.PredicateBuilder;
import org.nooon.kutya.ui.events.FormDiscardEvent;
import org.nooon.kutya.ui.template.CdiCustomField;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.*;

public abstract class DetailField<E extends BaseEntity> extends CdiCustomField implements
                                                                               FormDiscardEvent.FormDiscardEventListener {

    protected JPAContainer<E>     sourceContainer;
    protected PredicateBuilder<E> initialEntitySelectFilter;
    protected PredicateBuilder<E> additionalEntitySelectFilter;
    protected Table table;

    public DetailField(JPAContainer<E> sourceContainer, PredicateBuilder<E> initialEntitySelectFilter) {
        this.sourceContainer = sourceContainer;
        this.initialEntitySelectFilter = initialEntitySelectFilter != null
                ? initialEntitySelectFilter
                : PredicateBuilder.alwaysTrue((Class<E>) null);
        this.additionalEntitySelectFilter = null;

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
    public Class<?> getType() {
        return Collection.class;
    }

    @Override
    public Object getValue() {
        Set<E> value = new HashSet<E>();
        for (Object itemID : sourceContainer.getItemIds()) {
            value.add(sourceContainer.getItem(itemID).getEntity());
        }
        return value;
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

        table = createTable(sourceContainer);
        table.setWidth(100, Sizeable.Unit.PERCENTAGE);
        table.setSelectable(true);
        table.setMultiSelect(true);
        table.setEditable(false);
        table.setPageLength(20);
        mainLayout.addComponent(table);
        return mainLayout;
    }

    protected Table createTable(JPAContainer<E> targetContainer) {
        return new Table(getText("WVISDetailField.Table.caption"), targetContainer);
    }

    public void setAdditionalEntitySelectFilter(PredicateBuilder<E> additionalEntitySelectFilter) {
        this.additionalEntitySelectFilter = additionalEntitySelectFilter;
    }

    protected abstract Window createSelectPopup();

    protected PredicateBuilder<E> constructSelectPopupFilter() {

        return new PredicateBuilder<E>() {
            @Override
            public Predicate create(CriteriaBuilder cb, AbstractQuery<E> cq, From<E, E> from) {

                Set<String> alreadyAdded = new HashSet<String>();
                alreadyAdded.add("dummy");
                for (Object itemID : sourceContainer.getItemIds()) {
                    E added = sourceContainer.getItem(itemID).getEntity();
                    alreadyAdded.add(added.getId());
                }

                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.not(from.get(BaseEntity_.id).in(alreadyAdded)));
                predicates.add(cb.equal(from.get(BaseEntity_.active), Boolean.TRUE));

                if (!PredicateBuilder.alwaysTrue().toString().equals(initialEntitySelectFilter.toString())) {
                    predicates.add(initialEntitySelectFilter.create(cb, cq, from));
                }

                if (additionalEntitySelectFilter != null && !PredicateBuilder.alwaysTrue().toString().equals(additionalEntitySelectFilter.toString())) {
                    predicates.add(additionalEntitySelectFilter.create(cb, cq, from));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

    }

    protected AbstractLayout createButtonLayout() {

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeUndefined();
        layout.setSpacing(true);

        layout.addComponent(constructAddButton());
        layout.addComponent(constructRemoveButton());

        return layout;
    }

    protected Button constructAddButton() {
        Button addButton = new Button(getText("buttons.addSign"));
        addButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Window popup = createSelectPopup();
                popup.setModal(true);
                popup.center();
                getUI().addWindow(popup);
            }

        });

        return addButton;
    }

    protected Button constructRemoveButton() {
        Button removeButton = new Button(getText("buttons.removeSign"));
        removeButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                sourceContainer.removeItem(table.getValue());
            }
        });
        return removeButton;
    }


    public void clear() {
        sourceContainer.removeAllItems();
    }

    @Override
    public void receiveEvent(FormDiscardEvent event) {
        sourceContainer.discard();
    }


}