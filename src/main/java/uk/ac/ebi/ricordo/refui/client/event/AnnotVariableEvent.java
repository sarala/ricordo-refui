package uk.ac.ebi.ricordo.refui.client.event;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.ricordo.refui.shared.VariableSearch;

public class AnnotVariableEvent extends GwtEvent<AnnotVariableEventHandler>{
	  public static Type<AnnotVariableEventHandler> TYPE = new Type<AnnotVariableEventHandler>();
	  private final VariableSearch variableSearch;
	  
	  public AnnotVariableEvent(VariableSearch variableSearch) {
	    this.variableSearch = variableSearch;
	  }	  
	  
	  public VariableSearch getVariableSearch() {
		return variableSearch;
	  }

	  @Override
	  public Type<AnnotVariableEventHandler> getAssociatedType() {
	    return TYPE;
	  }

	  @Override
	  protected void dispatch(AnnotVariableEventHandler handler) {
	    handler.onVariableSelect(this);
	  }
	}

