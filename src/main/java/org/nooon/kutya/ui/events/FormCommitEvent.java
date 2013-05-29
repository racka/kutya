package org.nooon.kutya.ui.events;

import com.github.wolfie.blackboard.Event;
import com.github.wolfie.blackboard.Listener;
import com.github.wolfie.blackboard.annotation.ListenerMethod;
import com.vaadin.data.fieldgroup.FieldGroup;

public class FormCommitEvent implements Event {

    public interface FormCommitEventListener extends Listener {

        @ListenerMethod
        public void receiveEvent(final FormCommitEvent event);
    }
    private FieldGroup fieldGroup;

    public FormCommitEvent(FieldGroup form) {
        this.fieldGroup = form;
    }

    public FieldGroup getFieldGroup() {
        return fieldGroup;
    }
}
