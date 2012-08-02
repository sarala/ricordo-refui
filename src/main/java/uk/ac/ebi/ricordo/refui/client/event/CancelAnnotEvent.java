package uk.ac.ebi.ricordo.refui.client.event;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.ricordo.refui.shared.VariableSearch;

public class CancelAnnotEvent extends GwtEvent<CancelAnnotEventHandler>{
	  public static Type<CancelAnnotEventHandler> TYPE = new Type<CancelAnnotEventHandler>();
	  private final VariableSearch variableSearch;
	  
	  public CancelAnnotEvent(VariableSearch variableSearch) {
		  this.variableSearch = variableSearch;
	  }	  
	  
	  public VariableSearch getVariableSearch() {
		  return variableSearch;
	  }

	  @Override
	  public Type<CancelAnnotEventHandler> getAssociatedType() {
	     return TYPE;
	  }

	  @Override
	  protected void dispatch(CancelAnnotEventHandler handler) {
	    handler.onAnnotCancelled(this);
	  }

}
