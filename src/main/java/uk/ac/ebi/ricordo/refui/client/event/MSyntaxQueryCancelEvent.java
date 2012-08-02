package uk.ac.ebi.ricordo.refui.client.event;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.ricordo.refui.shared.VariableSearch;

public class MSyntaxQueryCancelEvent extends GwtEvent<MSyntaxQueryCancelHandler>{
	  public static Type<MSyntaxQueryCancelHandler> TYPE = new Type<MSyntaxQueryCancelHandler>();
	  private final String parentPage;
	  private VariableSearch variableSearch = null;

		public MSyntaxQueryCancelEvent(String parentPage) {
			this.parentPage = parentPage;
		}
		
		public MSyntaxQueryCancelEvent(String parentPage, VariableSearch variableSearch) {
			this(parentPage);
			this.variableSearch = variableSearch;
		}

		public String getParentPage() {
			return parentPage;
		}
		
		public VariableSearch getVariableSearch() {
			return variableSearch;
		}

	@Override
	  public Type<MSyntaxQueryCancelHandler> getAssociatedType() {
	    return TYPE;
	  }

	  @Override
	  protected void dispatch(MSyntaxQueryCancelHandler handler) {
	    handler.onCancelMancesterQuery(this);
	  }
}
