package uk.ac.ebi.ricordo.refui.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import uk.ac.ebi.ricordo.refui.client.AppController;
import uk.ac.ebi.ricordo.refui.client.QueryServiceAsync;
import uk.ac.ebi.ricordo.refui.client.event.MSyntaxQueryConstructEvent;
import uk.ac.ebi.ricordo.refui.client.event.ModelDetailsEvent;
import uk.ac.ebi.ricordo.refui.client.exception.ManchesterQueryException;
import uk.ac.ebi.ricordo.refui.client.view.BackToMainButton;
import uk.ac.ebi.ricordo.refui.shared.ModelDetailsLight;
import uk.ac.ebi.ricordo.refui.shared.ModelSearch;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class QueryPresenter implements Presenter {	

	public interface Display {	
		Widget asWidget();
		String getManQueryText();
		
		HasClickHandlers getSearchButton();	
		HasClickHandlers getManQueryButton();
		BackToMainButton getBackToMainButton();
		HasClickHandlers getList();
		
		void setData(List<ModelDetailsLight> data);				
		void setManQueryText(String text);
		void setSearchButtonStatus(boolean status);
		void setNoResultsLabel(List<ModelDetailsLight> data);
		
		int getClickedRow(ClickEvent event);
		
		void clearResultPanel();
	}

	private final QueryServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;
	private List<ModelDetailsLight> modelDetailsLight;
	private ModelSearch modelSearch = new ModelSearch();

	public QueryPresenter(QueryServiceAsync rpcService,	HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}
	
	public QueryPresenter(QueryServiceAsync rpcService,	HandlerManager eventBus, Display view, ModelSearch modelSearch) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		this.modelSearch = modelSearch;
	}

	public QueryPresenter(QueryServiceAsync rpcService, HandlerManager eventBus, Display view, String manQuery) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		modelSearch.setManQueryText(manQuery);
	}

	public void go(final HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());
		setUpModelSearch();
	}
	
	public void bind() {		
		display.getSearchButton().addClickHandler(new ClickHandler() {			
			public void onClick(ClickEvent event) {	
				display.clearResultPanel();
				modelSearch.setManQueryText(display.getManQueryText());				
				setUpModelSearch();
			}
		});
		
		display.getManQueryButton().addClickHandler(new ClickHandler() {			
			public void onClick(ClickEvent event) {				
				eventBus.fireEvent(new MSyntaxQueryConstructEvent(AppController.QUERY_PAGE));
			}
		});
		
		display.getBackToMainButton().addAction(eventBus);

		display.getList().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int selectedRow = display.getClickedRow(event);

				if (selectedRow >= 1) {
					String modelURL = modelDetailsLight.get(selectedRow-1).getModelURL();
					modelSearch.setModelURL(modelURL);
					eventBus.fireEvent(new ModelDetailsEvent(modelSearch));
				}
			}
		});			
	}

	public void setContactDetails(List<ModelDetailsLight> modelDetailsLight) {
		this.modelDetailsLight = modelDetailsLight;
	}

	public ModelDetailsLight getContactDetail(int index) {
		return modelDetailsLight.get(index);
	}

	private void searchModelData(){
		rpcService.searchModelData(modelSearch,new AsyncCallback<ArrayList<ModelDetailsLight>>() {
			public void onSuccess(ArrayList<ModelDetailsLight> result) {
				modelDetailsLight = result;
				display.setData(modelDetailsLight);
				display.setNoResultsLabel(modelDetailsLight);
				display.setSearchButtonStatus(true);

			}

			public void onFailure(Throwable caught) {
				Window.alert("Error searching for model data");
				display.setSearchButtonStatus(true);
			}
		});	
	}
	
	private void setUpModelSearch(){	
		display.setManQueryText(modelSearch.getManQueryText());
		if(modelSearch.getManQueryText().isEmpty())
			return;
		
		display.setSearchButtonStatus(false);
		rpcService.setOntoTerms(modelSearch, new AsyncCallback<ModelSearch>() {
			public void onSuccess(ModelSearch result) {
				modelSearch = result;
				searchModelData();
			}
	
			public void onFailure(Throwable caught) {
			     try {
			       throw caught;
			     } catch (ManchesterQueryException e) {
			    	 Window.alert(e.getDisplayMessage()+ "\n\n Details:\n"+e.getErrorMessage());
			     } catch (Throwable e) {
			    	 Window.alert(e.getMessage());
			     }	
			     display.setSearchButtonStatus(true);
			}
		});		

	}
}
