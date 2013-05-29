package org.nooon.kutya.ui.template.entity;

import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;

public class CommonGridLayout extends GridLayout {

    private int fieldWidth = 300;

    public CommonGridLayout(int columns, int rows, int fieldWidth) {
        super(columns, rows);
        this.fieldWidth = fieldWidth;
    }


    public CommonGridLayout(int columns, int rows) {
        super(columns, rows);
        if (columns == 2) {
            fieldWidth = 300;
        } else if (columns == 3) {
            fieldWidth = 200;
        }
    }

    @Override
    public void addComponent(Component c, int column, int row) throws OverlapsException, OutOfBoundsException {
        super.addComponent(c, column, row);
        c.setWidth(fieldWidth, Unit.PIXELS);
    }


    @Override
    public void addComponent(Component component, int column1, int row1, int column2, int row2) throws OverlapsException, OutOfBoundsException {
        super.addComponent(component, column1, row1, column2, row2);

        component.setWidth(fieldWidth * (column2 - column1 + 1) + 8 * (column2 - column1), Unit.PIXELS);
    }
}
