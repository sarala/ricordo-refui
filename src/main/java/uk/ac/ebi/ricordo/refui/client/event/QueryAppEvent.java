package uk.ac.ebi.ricordo.refui.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class QueryAppEvent extends GwtEvent<QueryAppEventHandler>{
	
	  public static Type<QueryAppEventHandler> TYPE = new Type<QueryAppEventHandler>();
	  
	  @Override
	  public Type<QueryAppEventHandler> getAssociatedType() {
	      return TYPE;
	  }
	
	  @Override
	  protected void dispatch(QueryAppEventHandler handler) {
	      handler.onQueryAppEvent(this);
	  }

}
