package org.nooon.kutya.ui.template.entity;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

public class CommonHorizontalLayout extends HorizontalLayout {

    public CommonHorizontalLayout() {
        setSpacing(true);
    }

    @Override
    public void addComponent(Component c) {
        super.addComponent(c);
        c.setWidth(160, Unit.PIXELS);
    }

}
