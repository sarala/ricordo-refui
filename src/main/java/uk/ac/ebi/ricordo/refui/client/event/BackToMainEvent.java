package uk.ac.ebi.ricordo.refui.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class BackToMainEvent extends GwtEvent<BackToMainEventHandler>{
  public static Type<BackToMainEventHandler> TYPE = new Type<BackToMainEventHandler>();
  
@Override
  public Type<BackToMainEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(BackToMainEventHandler handler) {
    handler.onBackToMain(this);
  }
}