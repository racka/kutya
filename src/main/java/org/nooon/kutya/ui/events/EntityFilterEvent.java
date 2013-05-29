package org.nooon.kutya.ui.events;

import com.github.wolfie.blackboard.Event;
import com.github.wolfie.blackboard.Listener;
import com.github.wolfie.blackboard.annotation.ListenerMethod;
import com.vaadin.ui.CustomComponent;
import org.nooon.core.utils.persistence.PredicateBuilder;

public class EntityFilterEvent implements Event {

    public interface EntityFilterEventListener extends Listener {
        @ListenerMethod
        public void receiveEvent(final EntityFilterEvent event);
    }

    private CustomComponent source;
    private PredicateBuilder filter;

    public EntityFilterEvent(CustomComponent source, PredicateBuilder filter) {
        this.source = source;
        this.filter = filter;
    }

    public PredicateBuilder getFilter() {
        return filter;
    }

    public CustomComponent getSource() {
        return source;
    }
}
