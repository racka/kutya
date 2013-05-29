package org.nooon.kutya.ui.events;

import com.github.wolfie.blackboard.Event;
import com.github.wolfie.blackboard.Listener;
import com.github.wolfie.blackboard.annotation.ListenerMethod;
import com.vaadin.ui.Component;
import org.nooon.core.model.entity.base.BaseEntity;

import java.util.Collection;

public class EntitySelectedEvent<E extends BaseEntity> implements Event {

    public interface EntitySelectedEventListener extends Listener {
        @ListenerMethod
        public void receiveEvent(final EntitySelectedEvent event);
    }

    private Component source;
    private Collection<E> selection;

    public EntitySelectedEvent(Component source, Collection<E> selection) {
        this.source = source;
        this.selection = selection;
    }

    public Component getSource() {
        return source;
    }

    public Collection<E> getSelection() {
        return selection;
    }
}
