package org.nooon.kutya.ui.events;

import com.github.wolfie.blackboard.Event;
import com.github.wolfie.blackboard.Listener;
import com.github.wolfie.blackboard.annotation.ListenerMethod;

public class FormDiscardEvent implements Event {

    public interface FormDiscardEventListener extends Listener {

        @ListenerMethod
        public void receiveEvent(final FormDiscardEvent event);
    }

    public FormDiscardEvent() {
    }
}
