package uk.ac.ebi.ricordo.refui.client.event;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.ricordo.refui.shared.VariableSearch;

public class MSyntaxQueryConstructEvent extends GwtEvent<MSyntaxQueryConstructEventHandler>{
	  public static Type<MSyntaxQueryConstructEventHandler> TYPE = new Type<MSyntaxQueryConstructEventHandler>();
	  private String parentPage;
	  private VariableSearch variableSearch=null;
	  
	  public MSyntaxQueryConstructEvent(String parentPage){
		  this.parentPage = parentPage;
	  }
	  
	  
	public MSyntaxQueryConstructEvent(String parentPage, VariableSearch variableSearch) {
		this.parentPage = parentPage;
		this.variableSearch = variableSearch;
	}

	public VariableSearch getVariableSearch() {
		return variableSearch;
	}


	public String getParentPage() {
		return parentPage;
	}


	@Override
	  public Type<MSyntaxQueryConstructEventHandler> getAssociatedType() {
	    return TYPE;
	  }

	  @Override
	  protected void dispatch(MSyntaxQueryConstructEventHandler handler) {
	    handler.onConstructManchesterQuery(this);
	  }
}
