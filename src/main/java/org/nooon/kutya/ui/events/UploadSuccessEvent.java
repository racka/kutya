package org.nooon.kutya.ui.events;

import com.github.wolfie.blackboard.Event;
import com.github.wolfie.blackboard.Listener;
import com.github.wolfie.blackboard.annotation.ListenerMethod;
import com.vaadin.ui.Component;

public class UploadSuccessEvent implements Event {

    public interface UploadSuccessEventListener extends Listener {
        @ListenerMethod
        public void receiveEvent(final UploadSuccessEvent event);
    }

    private Component source;

    public UploadSuccessEvent(Component source) {
        this.source = source;
    }

    public Component getSource() {
        return source;
    }

}
