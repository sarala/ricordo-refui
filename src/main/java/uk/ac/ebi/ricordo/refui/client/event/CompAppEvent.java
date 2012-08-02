package uk.ac.ebi.ricordo.refui.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class CompAppEvent extends GwtEvent<CompAppEventHandler>{
	
	  public static Type<CompAppEventHandler> TYPE = new Type<CompAppEventHandler>();
	  
	  @Override
	  public Type<CompAppEventHandler> getAssociatedType() {
	      return TYPE;
	  }
	
	  @Override
	  protected void dispatch(CompAppEventHandler handler) {
	      handler.onCompAppEvent(this);
	  }

}
