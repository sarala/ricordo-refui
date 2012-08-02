package uk.ac.ebi.ricordo.refui.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface MSyntaxQueryConstructEventHandler extends EventHandler {
	void onConstructManchesterQuery(MSyntaxQueryConstructEvent event);
}
