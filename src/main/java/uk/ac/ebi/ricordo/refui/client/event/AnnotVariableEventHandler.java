package uk.ac.ebi.ricordo.refui.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AnnotVariableEventHandler extends EventHandler {
  void onVariableSelect(AnnotVariableEvent event);
}

