package org.nooon.kutya.ui.events;

import com.github.wolfie.blackboard.Event;
import com.github.wolfie.blackboard.Listener;
import com.github.wolfie.blackboard.annotation.ListenerMethod;
import com.vaadin.ui.Component;

public class DataRefreshEvent implements Event {

    public interface DataRefreshEventListener extends Listener {
        @ListenerMethod
        public void receiveEvent(final DataRefreshEvent event);
    }

    private Component source;
    private Object payload;

    public DataRefreshEvent(Component source, Object payload) {
        this.source = source;
        this.payload = payload;
    }

    public Component getSource() {
        return source;
    }

    public Object getPayload() {
        return payload;
    }
}
