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
import uk.ac.ebi.ricordo.refui.client.exception.ManchesterQueryException;
import uk.ac.ebi.ricordo.refui.client.view.BackToMainButton;
import uk.ac.ebi.ricordo.refui.shared.CompositeDetailsLight;
import uk.ac.ebi.ricordo.refui.shared.ModelSearch;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class CompositePresenter implements Presenter {

	private List<CompositeDetailsLight> compoisteDetailsLight;
	public interface Display {	
		Widget asWidget();
		
		String getManQueryText();
		
		HasClickHandlers getCreateCompButton();	
		HasClickHandlers getManQueryButton();
		BackToMainButton getBackToMainButton();
		HasClickHandlers getList();
		
		void setData(List<CompositeDetailsLight> data);				
		void setManQueryText(String text);
		void setCreateCompButtonStatus(boolean status);		
		
		void clearResultPanel();
		
	}

	private final QueryServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;
	private ModelSearch modelSearch = new ModelSearch();

	public CompositePresenter(QueryServiceAsync rpcService,	HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}
	
	public CompositePresenter(QueryServiceAsync rpcService, HandlerManager eventBus, Display view, String manQuery) {
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
		display.getCreateCompButton().addClickHandler(new ClickHandler() {			
			public void onClick(ClickEvent event) {	
				display.clearResultPanel();
				modelSearch.setManQueryText(display.getManQueryText());
				getComposites();
			}
		});		
		
		display.getManQueryButton().addClickHandler(new ClickHandler() {			
			public void onClick(ClickEvent event) {				
				eventBus.fireEvent(new MSyntaxQueryConstructEvent(AppController.COMPOSITE_PAGE));
			}
		});
		
		display.getBackToMainButton().addAction(eventBus);		

	}
	
	public void setContactDetails(List<CompositeDetailsLight> compoisteDetailsLight) {
		this.compoisteDetailsLight = compoisteDetailsLight;
	}

	public CompositeDetailsLight getContactDetail(int index) {
		return compoisteDetailsLight.get(index);
	}

	private void getComposites(){
		display.setCreateCompButtonStatus(false);
		
		rpcService.getCompoistes(modelSearch,new AsyncCallback<ArrayList<CompositeDetailsLight>>() {
			public void onSuccess(ArrayList<CompositeDetailsLight> result) {
				compoisteDetailsLight = result;
				display.setData(compoisteDetailsLight);
				display.setCreateCompButtonStatus(true);
			}

			public void onFailure(Throwable caught) {
				try {
			       throw caught;
			     } catch (ManchesterQueryException e) {
			    	 Window.alert(e.getDisplayMessage()+ "\n\n Details:\n"+e.getErrorMessage());
			     } catch (Throwable e) {
			    	 Window.alert(e.getMessage());
			     }	
				display.setCreateCompButtonStatus(true);
			}
		});	
		

	}
	
	private void setUpModelSearch(){		
		if(!modelSearch.getManQueryText().isEmpty()){
			display.setManQueryText(modelSearch.getManQueryText());
		}		
	}
}
