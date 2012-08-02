package uk.ac.ebi.ricordo.refui.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface CancelAnnotEventHandler extends EventHandler {
	  void onAnnotCancelled(CancelAnnotEvent event);

}
