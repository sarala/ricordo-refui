package uk.ac.ebi.ricordo.refui.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface MSyntaxQueryCancelHandler extends EventHandler {
	  void onCancelMancesterQuery(MSyntaxQueryCancelEvent event);
}
