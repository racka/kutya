package org.nooon.kutya.ui.template.entity;


import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import org.nooon.core.model.entity.base.BaseEntity;
import org.nooon.kutya.ui.events.DataRefreshEvent;
import org.nooon.kutya.ui.events.EntityDeleteEvent;
import org.nooon.kutya.ui.events.FormCommitEvent;
import org.nooon.kutya.ui.events.FormDiscardEvent;
import org.nooon.kutya.ui.template.CdiWindow;
import org.vaadin.dialogs.ConfirmDialog;

public abstract class EntityEditPopup<E extends BaseEntity> extends CdiWindow implements
        FormCommitEvent.FormCommitEventListener,
        FormDiscardEvent.FormDiscardEventListener {

    protected BeanFieldGroup<E> binder;
    protected JPAContainer<E> sourceContainer;
    protected Object itemID;
    protected Class<E> beanClass;

    public EntityEditPopup(String caption, JPAContainer<E> container, Object itemID) {
        super(caption);
        this.beanClass = container.getEntityClass();
        this.sourceContainer = container;
        this.itemID = itemID;
    }

    protected Layout createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);
        formLayout.setSpacing(true);
        formLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
        return formLayout;
    }

    protected abstract void createBinding(Layout formLayout);

    protected AbstractLayout createMainLayout() {

        // MAIN
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        mainLayout.setSizeFull();

        // FORM
        Layout formLayout = createFormLayout();
        mainLayout.addComponent(formLayout);
        createBinding(formLayout);


        // BUTTONS
        AbstractLayout buttonsLayout = createButtonLayout();
        mainLayout.addComponent(buttonsLayout);
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_LEFT);
        return mainLayout;
    }

    protected HorizontalLayout createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setMargin(true);
        buttonLayout.setSpacing(true);


        // SAVE
        Button saveButton = new Button(getText("buttons.save"));
	    saveButton.setStyleName("small default");
        saveButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                getBlackboard().fire(new FormCommitEvent(binder));
            }
        });
        buttonLayout.addComponent(saveButton);


        // DISCARD
        Button discardButton = new Button(getText("buttons.reset"));
	    discardButton.setStyleName("small");
        discardButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                getBlackboard().fire(new FormDiscardEvent());
            }
        });
        buttonLayout.addComponent(discardButton);


        // CANCEL
        Button cancelButton = new Button(getText("buttons.cancel"));
	    cancelButton.setStyleName("small");
        cancelButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                EntityEditPopup.this.close();
            }
        });
        buttonLayout.addComponent(cancelButton);


        // DELETE
        Button deleteButton = new Button(getText("buttons.delete"));
	    deleteButton.setStyleName("small");
        deleteButton.addClickListener(new Button.ClickListener() {

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
                                    getBlackboard().fire(new EntityDeleteEvent(EntityEditPopup.this, binder.getItemDataSource().getBean()));
                                    EntityEditPopup.this.close();
                                }
                            }
                        });
            }
        });
        buttonLayout.addComponent(deleteButton);

        return buttonLayout;
    }

    @Override
    public void attach() {
        super.attach();
        getBlackboard().addListener(EntityEditPopup.this);
        setContent(createMainLayout());
    }

    @Override
    public void close() {
        getBlackboard().removeListener(EntityEditPopup.this);
        getBlackboard().fire(new DataRefreshEvent(EntityEditPopup.this, itemID));
        super.close();
    }

    @Override
    public void receiveEvent(FormCommitEvent event) {
        if (binder.equals(event.getFieldGroup())) {
            try {
                binder.commit();
                E committedBean = binder.getItemDataSource().getBean();
                getJpaDAO().save(committedBean);
                EntityEditPopup.this.close();
            } catch (FieldGroup.CommitException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void receiveEvent(FormDiscardEvent event) {
        binder.discard();
    }
}
