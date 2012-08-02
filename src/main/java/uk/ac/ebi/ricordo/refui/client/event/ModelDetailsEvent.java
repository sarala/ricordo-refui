package uk.ac.ebi.ricordo.refui.client.event;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.ricordo.refui.shared.ModelSearch;

public class ModelDetailsEvent extends GwtEvent<ModelDetailsEventHandler>{
  public static Type<ModelDetailsEventHandler> TYPE = new Type<ModelDetailsEventHandler>();
  private final ModelSearch modelSearch;
  
  public ModelDetailsEvent(ModelSearch modelSearch) {
    this.modelSearch = modelSearch;
  }
  
  public ModelSearch getModelSearch() { return modelSearch; }
  

@Override
  public Type<ModelDetailsEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ModelDetailsEventHandler handler) {
    handler.onEditContact(this);
  }
}
