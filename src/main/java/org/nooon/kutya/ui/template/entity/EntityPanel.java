package org.nooon.kutya.ui.template.entity;


import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
import com.vaadin.addon.jpacontainer.util.HibernateLazyLoadingDelegate;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import org.nooon.core.model.entity.base.BaseEntity;
import org.nooon.core.utils.persistence.PredicateBuilder;
import org.nooon.kutya.ui.events.DataRefreshEvent;
import org.nooon.kutya.ui.events.EntityDeleteEvent;
import org.nooon.kutya.ui.events.EntityFilterEvent;
import org.nooon.kutya.ui.template.CdiCustomComponent;
import org.vaadin.dialogs.ConfirmDialog;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.List;

public abstract class EntityPanel<E extends BaseEntity> extends CdiCustomComponent implements
        EntityFilterEvent.EntityFilterEventListener, EntityDeleteEvent.EntityDeleteEventListener,
        DataRefreshEvent.DataRefreshEventListener {

    protected CustomComponent filterPanel;
    protected HorizontalLayout clientLayout;
    protected AbstractOrderedLayout buttonLayout;
    protected Button addBeanButton;
    protected Button removeBeanButton;
    protected Table beanTable;
    protected JPAContainer<E> container;
    protected Class<E> beanClass;
    protected Window entityEditPopup;

    public EntityPanel(Class<E> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public void attach() {
        super.attach();
        getBlackboard().addListener(EntityPanel.this);
        createCompositionRoot();
    }

    @Override
    public void detach() {
        getBlackboard().removeListener(EntityPanel.this);
        super.detach();
    }

    private void createCompositionRoot() {
        this.filterPanel = createFilterPanel();

        this.clientLayout = new HorizontalLayout();
        this.clientLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);

        this.container = new JPAContainer<E>(beanClass);
        this.container.setEntityProvider(getEntityProvider(this.beanClass));


        this.container.getEntityProvider().setQueryModifierDelegate(new DefaultQueryModifierDelegate() {
            @Override
            public void filtersWillBeAdded(CriteriaBuilder criteriaBuilder, CriteriaQuery<?> query, List<Predicate> predicates) {
                predicates.add(createInitialFilter().create(criteriaBuilder, query, query.getRoots().iterator().next()));
            }
        });
        this.container.getEntityProvider().setLazyLoadingDelegate(new HibernateLazyLoadingDelegate());
        this.beanTable = createTable(this.container);
        this.buttonLayout = createButtonLayout();

        // ROOT layout
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth(100, Sizeable.Unit.PERCENTAGE);
        layout.setMargin(true);
        layout.setSpacing(true);


        // filter
        layout.addComponent(this.filterPanel);

        // buttons
        layout.addComponent(this.buttonLayout);

        // client layout
        layout.addComponent(this.clientLayout);


        // table
        this.clientLayout.addComponent(this.beanTable);

        setCompositionRoot(layout);

    }

    protected PredicateBuilder createInitialFilter() {
        return PredicateBuilder.activeFilter();
    }

    protected CustomComponent createFilterPanel() {
        return new CustomComponent();
    }

    protected Table createEntityTable(JPAContainer<E> container) {
        return new EntityTable(container);
    }

    protected Table createTable(final JPAContainer<E> container) {
        Table entityBeanTable = createEntityTable(container);

        entityBeanTable.setWidth(100, Sizeable.Unit.PERCENTAGE);
        entityBeanTable.setHeight(400, Sizeable.Unit.PIXELS);
        entityBeanTable.setPageLength(50);

        entityBeanTable.setSelectable(true);
        entityBeanTable.setMultiSelect(true);

        entityBeanTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                    Object itemID = event.getItemId();
                    container.refreshItem(itemID);
                    showEntityPopup(itemID);
                }
            }
        });
        return entityBeanTable;
    }

    protected abstract Window createEntityEditWindow(JPAContainer<E> container, Object itemID);

    protected void showEntityPopup(Object itemID) {
        entityEditPopup = createEntityEditWindow(container, itemID);
        entityEditPopup.setWidth(600, Sizeable.Unit.PIXELS);
        entityEditPopup.setHeight(500, Sizeable.Unit.PIXELS);
        entityEditPopup.setModal(true);
        entityEditPopup.center();
        getUI().addWindow(entityEditPopup);
    }

    protected E createNewInstance() {
        E newInstance = null;
        try {
            newInstance = beanClass.newInstance();
        } catch (InstantiationException e) {
            // TODO:
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO:
            e.printStackTrace();
        }
        return newInstance;
    }

    
    protected AbstractOrderedLayout createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);

        addBeanButton = new Button( getText("buttons.new"), new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                showEntityPopup(new BeanItem<E>(createNewInstance()));
            }
        });
        addBeanButton.addStyleName("small");
        buttonLayout.addComponent(addBeanButton);

        removeBeanButton = new Button(getText("buttons.remove"), new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {

                ConfirmDialog.show(getUI(),
                        getText("confirmation.delete.title"),
                        getText("confirmation.delete.question"),
                        getText("confirmation.delete.yes"),
                        getText("confirmation.delete.no"),
                        new ConfirmDialog.Listener() {
                            public void onClose(ConfirmDialog dialog) {
                                if (dialog.isConfirmed()) {
                                    for (String itemId : (Collection<String>) beanTable.getValue()) {
                                        container.refreshItem(itemId);
                                        getBlackboard().fire(new EntityDeleteEvent<E>(EntityPanel.this, container.getItem(itemId).getEntity()));
                                    }
                                }
                            }
                        });


            }
        });
        removeBeanButton.addStyleName("small");
        buttonLayout.addComponent(removeBeanButton);

        Button refreshButton = new Button(getText("buttons.refresh"), new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                container.refresh();
            }
        });
        refreshButton.addStyleName("small");
        buttonLayout.addComponent(refreshButton);

        return buttonLayout;
    }
    
    
    @Override
    public void receiveEvent(final EntityFilterEvent event) {
        if (event.getSource().equals(filterPanel)) {
            this.container.getEntityProvider().setQueryModifierDelegate(new DefaultQueryModifierDelegate() {
                @Override
                public void filtersWillBeAdded(CriteriaBuilder criteriaBuilder, CriteriaQuery<?> query, List<Predicate> predicates) {
                    predicates.add(event.getFilter().create(criteriaBuilder, query, query.getRoots().iterator().next()));
                }
            });
            this.container.refresh();
        }
    }

    @Override
    public void receiveEvent(EntityDeleteEvent event) {
        if (event.getSource().equals(this) || event.getSource().equals(entityEditPopup)) {
            getJpaDAO().remove(event.getBean());
            container.refresh();
        }
    }

    @Override
    public void receiveEvent(DataRefreshEvent event) {
        if (event.getSource().equals(entityEditPopup)) {
            container.refreshItem(event.getPayload());
        } else if (event.getSource().equals(beanTable)) {
            container.refresh();
        }
    }

}
