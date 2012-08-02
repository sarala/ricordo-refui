package uk.ac.ebi.ricordo.refui.client.event;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.ricordo.refui.shared.ModelSearch;

public class ModelDetailsCancelledEvent extends GwtEvent<ModelDetailsCancelledEventHandler> {
	
	public static Type<ModelDetailsCancelledEventHandler> TYPE = new Type<ModelDetailsCancelledEventHandler>();
	private final ModelSearch modelSearch;

	public ModelDetailsCancelledEvent(ModelSearch modelSearch) {
		this.modelSearch = modelSearch;
	}
	  
	public ModelSearch getModelSearch() { return modelSearch; }	  

	@Override
	public Type<ModelDetailsCancelledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ModelDetailsCancelledEventHandler handler) {
		handler.onEditContactCancelled(this);
	}
}
