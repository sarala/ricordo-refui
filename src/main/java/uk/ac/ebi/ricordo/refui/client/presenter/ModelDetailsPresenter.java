package uk.ac.ebi.ricordo.refui.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import uk.ac.ebi.ricordo.refui.client.QueryServiceAsync;
import uk.ac.ebi.ricordo.refui.client.event.ModelDetailsCancelledEvent;
import uk.ac.ebi.ricordo.refui.shared.ModelSearch;
import uk.ac.ebi.ricordo.refui.shared.VariableDetailsLight;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class ModelDetailsPresenter implements Presenter{  
  public interface Display {
    HasClickHandlers getCancelButton();
    HasText getTitleLabel();
    
    void setData(List<VariableDetailsLight> data);
    Widget asWidget();
  }
  
  private final QueryServiceAsync rpcService;
  private final HandlerManager eventBus;
  private final Display display;
  
  private ModelSearch modelSearch;
  
/*  public ModelDetailsPresenter(QueryServiceAsync rpcService, HandlerManager eventBus, Display display) {
    this.rpcService = rpcService;
    this.eventBus = eventBus;
    this.display = display;
  }
  */
  public ModelDetailsPresenter(QueryServiceAsync rpcService, HandlerManager eventBus, Display display, ModelSearch modelSearch) {
    this.rpcService = rpcService;
    this.eventBus = eventBus;
    this.display = display;
    this.modelSearch = modelSearch;    
  }
  

	private void setUpResults() {
		    rpcService.getVariableList(modelSearch, new AsyncCallback<ArrayList<VariableDetailsLight>>() {
		        public void onSuccess(ArrayList<VariableDetailsLight> result) {
		        	display.setData(result);
		        }
		        
		        public void onFailure(Throwable caught) {
		          Window.alert("Error retrieving modelDetails");
		        }
		      });
		
	}
	
	public void bind() {
	    this.display.getCancelButton().addClickHandler(new ClickHandler() {   
	      public void onClick(ClickEvent event) {
	        eventBus.fireEvent(new ModelDetailsCancelledEvent(modelSearch));
	      }
	    });
	}

  public void go(final HasWidgets container) {
	bind();
    container.clear();
    container.add(display.asWidget());
    setUpResults();
  }

}
