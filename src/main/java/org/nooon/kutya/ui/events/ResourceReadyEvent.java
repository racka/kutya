package org.nooon.kutya.ui.events;

import com.github.wolfie.blackboard.Event;
import com.github.wolfie.blackboard.Listener;
import com.github.wolfie.blackboard.annotation.ListenerMethod;
import com.vaadin.server.StreamResource;


public class ResourceReadyEvent implements Event {

    public interface ResourceReadyEventListener extends Listener {
        @ListenerMethod
        public void receiveEvent(final ResourceReadyEvent event);
    }

    private StreamResource streamResource;

    public ResourceReadyEvent(StreamResource source) {
        this.streamResource = source;
    }

    public StreamResource getStreamResource() {
        return streamResource;
    }
}
