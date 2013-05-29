package org.nooon.kutya.ui.template.entity;


import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import org.nooon.core.model.entity.base.BaseEntity;
import org.nooon.core.utils.persistence.PredicateBuilder;
import org.nooon.kutya.ui.events.EntityFilterEvent;
import org.nooon.kutya.ui.events.EntitySelectedEvent;
import org.nooon.kutya.ui.template.CdiWindow;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public abstract class EntitySelectPopup<E extends BaseEntity> extends CdiWindow implements
        EntityFilterEvent.EntityFilterEventListener {

    protected Class<E> beanClass;
    protected JPAContainer<E> popupContainer;
    protected PredicateBuilder popupFilter;
    protected Table beanTable;

    protected EntityFilterPanel filterPanel;
    protected Button okButton;
    protected Button cancelButton;

    public EntitySelectPopup(String caption, Class<E> beanClass, final PredicateBuilder popupFilter) {
        super(caption);
        this.popupFilter = popupFilter;
        this.beanClass = beanClass;
    }

    private void createPopupContainer() {
        this.popupContainer = new JPAContainer<E>(beanClass);
        this.popupContainer.setEntityProvider(getEntityProvider(this.beanClass));
        this.popupContainer.getEntityProvider().setQueryModifierDelegate(new DefaultQueryModifierDelegate() {
            @Override
            public void filtersWillBeAdded(CriteriaBuilder criteriaBuilder, CriteriaQuery<?> query, List<Predicate> predicates) {
                predicates.add(popupFilter.create(criteriaBuilder, query, query.getRoots().iterator().next()));
            }
        });
    }

    private void createContent() {
        Panel contentPanel = new Panel();
        contentPanel.setContent(createPrimalLayout());
        contentPanel.setWidth(930, Sizeable.Unit.PIXELS);
        contentPanel.setHeight(550, Sizeable.Unit.PIXELS);
        setContent(contentPanel);
    }

    public AbstractLayout createPrimalLayout() {
        AbstractLayout layout = new VerticalLayout();
        layout.setSizeFull();
        ((AbstractOrderedLayout)layout).setMargin(true);
        ((VerticalLayout)layout).setSpacing(true);

        filterPanel = createFilterPanel();
        if (filterPanel != null) {
            layout.addComponent(filterPanel);
        }

        beanTable = createTable();
        layout.addComponent(beanTable);

        AbstractLayout buttonLayout = EntitySelectPopup.this.createButtonsLayout();
        layout.addComponent(buttonLayout);
        ((VerticalLayout) layout).setComponentAlignment(buttonLayout, Alignment.BOTTOM_LEFT);
        return layout;
    }

    protected abstract EntityFilterPanel createFilterPanel();

    protected Table createTable() {
        Table beanTable = createEntityTable(popupContainer);
        for (String columnId : (Collection<String>)beanTable.getContainerPropertyIds()) {
            beanTable.setColumnHeader(columnId, getText(popupContainer.getEntityClass().getSimpleName() + "." + columnId));
        }
        beanTable.setWidth(100, Sizeable.Unit.PERCENTAGE);
        beanTable.setHeight(200, Sizeable.Unit.PIXELS);
        beanTable.setPageLength(50);
        beanTable.setEditable(false);
        beanTable.setSelectable(true);
        beanTable.setMultiSelect(true);
        return beanTable;
    }

    protected Table createEntityTable(JPAContainer<E> container) {
        return new EntityTable(container);
    }

    protected AbstractLayout createButtonsLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);

        okButton = new Button(getText("buttons.ok"));
        okButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getBlackboard().fire(new EntitySelectedEvent<E>(EntitySelectPopup.this, addSelectedEntitiesToTarget()));
                EntitySelectPopup.this.close();
            }
        });
        okButton.addStyleName("small");
        buttonLayout.addComponent(okButton);

        cancelButton = new Button(getText("buttons.cancel"));
        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                EntitySelectPopup.this.close();
            }
        });
        cancelButton.addStyleName("small");
        buttonLayout.addComponent(cancelButton);

        return buttonLayout;
    }

    protected Collection<E> addSelectedEntitiesToTarget() {
        Collection<E> result = new HashSet<E>();
        if (beanTable.isMultiSelect()) {
            for (Object itemId : ((Collection) beanTable.getValue())) {
                result.add(popupContainer.getItem(itemId).getEntity());
            }
        } else {
            result.add(popupContainer.getItem(beanTable.getValue()).getEntity());
        }
        return result;
    }

    @Override
    public void attach() {
        super.attach();
        getBlackboard().addListener(EntitySelectPopup.this);
        createPopupContainer();
        createContent();
    }

    @Override
    public void detach() {
        getBlackboard().removeListener(EntitySelectPopup.this);
        super.detach();
    }


    @Override
    public void receiveEvent(final EntityFilterEvent event) {
        if (event.getSource().equals(filterPanel)) {
            this.popupContainer.getEntityProvider().setQueryModifierDelegate(new DefaultQueryModifierDelegate() {
                @Override
                public void filtersWillBeAdded(CriteriaBuilder criteriaBuilder, CriteriaQuery<?> query, List<Predicate> predicates) {
                    predicates.add(event.getFilter().create(criteriaBuilder, query, query.getRoots().iterator().next()));
                }
            });
        }
    }


}
