package org.nooon.kutya.ui.events;

import com.github.wolfie.blackboard.Event;
import com.github.wolfie.blackboard.Listener;
import com.github.wolfie.blackboard.annotation.ListenerMethod;
import com.vaadin.ui.Component;
import org.nooon.core.model.entity.base.BaseEntity;

public class EntityDeleteEvent<E extends BaseEntity> implements Event {

    public interface EntityDeleteEventListener extends Listener {
        @ListenerMethod
        public void receiveEvent(final EntityDeleteEvent event);
    }

    private Component source;
    private E bean;

    public EntityDeleteEvent(Component source, E bean) {
        this.source = source;
        this.bean = bean;
    }

    public Component getSource() {
        return source;
    }

    public E getBean() {
        return bean;
    }
}
