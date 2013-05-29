package org.nooon.kutya.ui;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * Vaadin needs instances of the UIs, not the Weld_proxy
 */
@SessionScoped
public class MySessionUI implements Serializable {

    @Inject
    private Instance<MyVaadinUI> clientUI;

    public MyVaadinUI getClientUI() {
        return clientUI.get();
    }
}
