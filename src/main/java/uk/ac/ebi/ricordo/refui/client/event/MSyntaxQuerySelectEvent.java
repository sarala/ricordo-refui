package uk.ac.ebi.ricordo.refui.client.event;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.ricordo.refui.shared.VariableSearch;

public class MSyntaxQuerySelectEvent extends GwtEvent<MSyntaxQuerySelectEventHandler>{
	  public static Type<MSyntaxQuerySelectEventHandler> TYPE = new Type<MSyntaxQuerySelectEventHandler>();
	  private final String manQuery;	  
	  private final String parentPage;
	  private VariableSearch variableSearch = null;

	public MSyntaxQuerySelectEvent(String manQuery, String parentPage) {
		this.manQuery = manQuery;
		this.parentPage = parentPage;
	}
	
	public MSyntaxQuerySelectEvent(String manQuery, String parentPage,VariableSearch variableSearch) {
		this(manQuery,parentPage);
		this.variableSearch = variableSearch;
	}

	public String getManQuery() {
		return manQuery;
	}	

	public String getParentPage() {
		return parentPage;
	}	

	public VariableSearch getVariableSearch() {
		return variableSearch;
	}

	@Override
	  public Type<MSyntaxQuerySelectEventHandler> getAssociatedType() {
	    return TYPE;
	  }

	  @Override
	  protected void dispatch(MSyntaxQuerySelectEventHandler handler) {
	    handler.onSelectMancesterQuery(this);
	  }
}
