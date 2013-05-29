package org.nooon.kutya.ui.pages.work;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import org.nooon.core.model.entity.Work;
import org.nooon.core.model.entity.Worksheet;
import org.nooon.kutya.ui.events.EntitySelectedEvent;
import org.nooon.kutya.ui.events.FormCommitEvent;

import java.util.Arrays;
import java.util.Collection;

public class WorkSelectPopup extends WorkEditPopup {

    private Worksheet worksheet;

    public WorkSelectPopup(String caption, Worksheet worksheet, JPAContainer<Work> container, Object itemID) {
        super(caption, container, itemID);
        this.worksheet = worksheet;
    }

    @Override
    protected HorizontalLayout createButtonLayout() {
        HorizontalLayout layout = super.createButtonLayout();

        Button saveButton = (Button) layout.getComponent(0);
        for (Button.ClickListener listener : (Collection<Button.ClickListener>)saveButton.getListeners(Button.ClickEvent.class)) {
            saveButton.removeClickListener(listener);
        }

        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {

                binder.getItemDataSource().getBean().setWorksheet(worksheet);

                getBlackboard().fire(new FormCommitEvent(binder));
                getBlackboard().fire(new EntitySelectedEvent<Work>(WorkSelectPopup.this,
                        Arrays.asList(sourceContainer.getItem(itemID).getEntity())));
                WorkSelectPopup.this.close();
            }
        });


        return layout;
    }
}
