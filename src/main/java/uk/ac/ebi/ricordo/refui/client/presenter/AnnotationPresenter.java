package uk.ac.ebi.ricordo.refui.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import uk.ac.ebi.ricordo.refui.client.QueryServiceAsync;
import uk.ac.ebi.ricordo.refui.client.event.AnnotVariableEvent;
import uk.ac.ebi.ricordo.refui.client.view.BackToMainButton;
import uk.ac.ebi.ricordo.refui.shared.VariableDetailsLight;
import uk.ac.ebi.ricordo.refui.shared.VariableSearch;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class AnnotationPresenter implements Presenter {
	public interface Display {	
		Widget asWidget();		
		
		String getModelText();
		
		HasClickHandlers getSearchButton();	
		BackToMainButton getBackToMainButton();
		HasClickHandlers getAddButton();
		HasClickHandlers getList();

		void setData(List<VariableDetailsLight> data);
		void setSearchButtonStatus(boolean status);
		void setNoResultsLabel(List<VariableDetailsLight> data);
		void setModelText(String modelTextString);
		int getClickedRow(ClickEvent event);
		
		void clearResultPanel();
	}
	
	private final QueryServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;
	
	private List<VariableDetailsLight> variableDetailsLight;
	private VariableSearch variableSearch = new VariableSearch();	
	
	public AnnotationPresenter(QueryServiceAsync rpcService,HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}
	
	public AnnotationPresenter(QueryServiceAsync rpcService,HandlerManager eventBus, Display view, VariableSearch variableSearch) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		this.variableSearch = variableSearch;
	}

	public void bind() {
		
		display.getSearchButton().addClickHandler(new ClickHandler() {			
			public void onClick(ClickEvent event) {	
				display.clearResultPanel();
				variableSearch.setModelUrl(display.getModelText());
				searchVariableData();
			}
		});

		display.getBackToMainButton().addAction(eventBus);		
		
		display.getList().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int selectedRow = display.getClickedRow(event);

				if (selectedRow >= 1) {
					String variableURL = variableDetailsLight.get(selectedRow-1).getVariableURL();
					variableSearch.setVariableUrl(variableURL);
					eventBus.fireEvent(new AnnotVariableEvent(variableSearch));
				}
			}
		});	
		
	}
	
	public void go(final HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());
		setSearchParameters();	
		searchVariableData();
	}
	
	private void setSearchParameters(){
		display.setModelText(variableSearch.getModelUrl());
	}

	public void setContactDetails(List<VariableDetailsLight> variableDetailsLight) {
		this.variableDetailsLight = variableDetailsLight;
	}

	public VariableDetailsLight getContactDetail(int index) {
		return variableDetailsLight.get(index);
	}

	private void searchVariableData(){
		if(variableSearch.getModelUrl().isEmpty()){
			return;
		}
		
		display.setSearchButtonStatus(false);
		
		rpcService.getVariableListForAnnotation(variableSearch,new AsyncCallback<ArrayList<VariableDetailsLight>>() {
			public void onSuccess(ArrayList<VariableDetailsLight> result) {
				variableDetailsLight = result;
				display.setData(variableDetailsLight);
				display.setNoResultsLabel(variableDetailsLight);
				display.setSearchButtonStatus(true);

			}

			public void onFailure(Throwable caught) {
				Window.alert("Error searching for variables");
				display.setSearchButtonStatus(true);
			}
		});	
	}
	
}
