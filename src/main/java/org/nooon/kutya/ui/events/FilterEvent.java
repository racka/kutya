package org.nooon.kutya.ui.events;


import com.github.wolfie.blackboard.Event;
import com.github.wolfie.blackboard.Listener;
import com.github.wolfie.blackboard.annotation.ListenerMethod;
import com.vaadin.ui.Component;

import javax.persistence.criteria.Predicate;

/**
 * @author Attila Majoros <attila.majoros@webvalto.hu>
 */
public class FilterEvent implements Event {

	public interface FilterEventListener extends Listener {
		@ListenerMethod
		public void receiveEvent(final FilterEvent event);
	}

	private Component source;
	private Predicate filter;

	public FilterEvent(Component source, Predicate filter) {
		this.source = source;
		this.filter = filter;
	}

	public Component getSource() {
		return source;
	}

	public Predicate getFilter() {
		return filter;
	}
}
