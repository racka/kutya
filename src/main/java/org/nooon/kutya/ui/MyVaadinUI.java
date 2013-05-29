package org.nooon.kutya.ui;

import com.github.wolfie.blackboard.Blackboard;
import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.nooon.core.dao.JpaDAO;
import org.nooon.core.model.entity.*;
import org.nooon.core.model.provider.EntityType;
import org.nooon.core.utils.TextResources;
import org.nooon.core.utils.persistence.BeanHelper;
import org.nooon.kutya.ui.events.*;
import org.nooon.kutya.ui.pages.person.PersonPanel;
import org.nooon.kutya.ui.pages.vehicle.VehiclePanel;
import org.nooon.kutya.ui.pages.worksheet.WorksheetPanel;
import org.nooon.kutya.ui.template.entity.EntityPanel;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class MyVaadinUI extends UI implements CdiComponent {

    @Inject
    private TextResources texts;

    @EJB
    private JpaDAO jpaDAO;

    @EJB
    private BeanHelper beanHelper;

    @Inject
    @EntityType(entityClass = Part.class)
    private EntityProvider partEntityProvider;

    @Inject
    @EntityType(entityClass = Person.class)
    private EntityProvider personEntityProvider;

    @Inject
    @EntityType(entityClass = State.class)
    private EntityProvider stateEntityProvider;

    @Inject
    @EntityType(entityClass = Vehicle.class)
    private EntityProvider vehicleEntityProvider;

    @Inject
    @EntityType(entityClass = Work.class)
    private EntityProvider workEntityProvider;

    @Inject
    @EntityType(entityClass = Worksheet.class)
    private EntityProvider worksheetEntityProvider;

    private Blackboard blackboard = new Blackboard();

    final VerticalLayout clientLayout = new VerticalLayout();

    @Override
    protected void init(VaadinRequest request) {

        registerBlackboardEvents();
        getPage().setTitle(getText("ui.title"));

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        setContent(layout);


        MenuBar menuBar = new MenuBar();
        menuBar.setWidth(100, Unit.PERCENTAGE);
        layout.addComponent(menuBar);

        clientLayout.setSizeFull();
        clientLayout.setSpacing(false);
        clientLayout.setMargin(true);
        layout.addComponent(clientLayout);

        layout.setExpandRatio(clientLayout, 1);

        MenuBar.MenuItem entitiesMenu = menuBar.addItem(getText("menu.entities"), null);
        addEntityPanel(entitiesMenu, new WorksheetPanel(), getText("menu.worksheet"));
        addEntityPanel(entitiesMenu, new PersonPanel(), getText("menu.person"));
        addEntityPanel(entitiesMenu, new VehiclePanel(), getText("menu.vehicle"));

    }

    private MenuBar.MenuItem addEntityPanel(MenuBar.MenuItem entitiesMenu, final EntityPanel panel, String caption) {
        return entitiesMenu.addItem(caption, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                clientLayout.removeAllComponents();
                clientLayout.addComponent(panel);
                clientLayout.setExpandRatio(panel, 1);
            }
        });

    }

    private void registerBlackboardEvents() {
        blackboard.register(FormCommitEvent.FormCommitEventListener.class, FormCommitEvent.class);
        blackboard.register(FormDiscardEvent.FormDiscardEventListener.class, FormDiscardEvent.class);
        blackboard.register(EntityDeleteEvent.EntityDeleteEventListener.class, EntityDeleteEvent.class);
        blackboard.register(EntityFilterEvent.EntityFilterEventListener.class, EntityFilterEvent.class);
        blackboard.register(EntitySelectedEvent.EntitySelectedEventListener.class, EntitySelectedEvent.class);
        blackboard.register(DataRefreshEvent.DataRefreshEventListener.class, DataRefreshEvent.class);
        blackboard.register(FilterEvent.FilterEventListener.class, FilterEvent.class);
        blackboard.register(UploadSuccessEvent.UploadSuccessEventListener.class, UploadSuccessEvent.class);
    }


    public String getText(String key) {
        return texts.get(key);
    }

    public JpaDAO getJpaDAO() {
        return jpaDAO;
    }

    public BeanHelper getBeanHelper() {
        return beanHelper;
    }

    public Blackboard getBlackboard() {
        return blackboard;
    }

    public EntityProvider getEntityProvider(Class clazz) {

        if (clazz.equals(Part.class)) {
            return partEntityProvider;
        } else if (clazz.equals(Person.class)) {
            return personEntityProvider;
        } else if (clazz.equals(State.class)) {
            return stateEntityProvider;
        } else if (clazz.equals(Vehicle.class)) {
            return vehicleEntityProvider;
        } else if (clazz.equals(Work.class)) {
            return workEntityProvider;
        } else if (clazz.equals(Worksheet.class)) {
            return worksheetEntityProvider;
        }

        throw new IllegalArgumentException();
    }
}
