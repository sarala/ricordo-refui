package uk.ac.ebi.ricordo.refui.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AnnotAppEvent extends GwtEvent<AnnotAppEventHandler>{
	
	  public static Type<AnnotAppEventHandler> TYPE = new Type<AnnotAppEventHandler>();
	  
	  @Override
	  public Type<AnnotAppEventHandler> getAssociatedType() {
	      return TYPE;
	  }
	
	  @Override
	  protected void dispatch(AnnotAppEventHandler handler) {
	      handler.onAnnotAppEvent(this);
	  }


}
