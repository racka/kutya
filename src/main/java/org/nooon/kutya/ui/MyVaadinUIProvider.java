package org.nooon.kutya.ui;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MyVaadinUIProvider extends UIProvider {

    @Inject
    private MySessionUI sessionUI;


    @Override
    public UI createInstance(UICreateEvent event) {
        return sessionUI.getClientUI();
    }

    @Override
    public Class<? extends UI> getUIClass(UIClassSelectionEvent uiClassSelectionEvent) {
        return MyVaadinUI.class;
    }

}
