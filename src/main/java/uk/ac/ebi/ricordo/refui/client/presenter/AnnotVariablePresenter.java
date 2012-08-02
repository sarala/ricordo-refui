package uk.ac.ebi.ricordo.refui.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import uk.ac.ebi.ricordo.refui.client.AppController;
import uk.ac.ebi.ricordo.refui.client.QueryServiceAsync;
import uk.ac.ebi.ricordo.refui.client.event.CancelAnnotEvent;
import uk.ac.ebi.ricordo.refui.client.event.MSyntaxQueryConstructEvent;
import uk.ac.ebi.ricordo.refui.client.exception.AnnotVariableException;
import uk.ac.ebi.ricordo.refui.client.exception.ManchesterQueryException;
import uk.ac.ebi.ricordo.refui.shared.VariableDetailsLight;
import uk.ac.ebi.ricordo.refui.shared.VariableSearch;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class AnnotVariablePresenter implements Presenter {

	public interface Display {	
		Widget asWidget();
		
		String getQualifierListBoxText();
		String getVarNameLabel();
		String getTermIDText();
		
		HasClickHandlers getAddButton();	
		HasClickHandlers getManQueryButton();
		HasClickHandlers getCancelButton();
		HasClickHandlers getList();
		HasChangeHandlers getQualifierListBox();
		
		void setData(List<VariableDetailsLight> data);
		void setQualifierListBox(ArrayList<String> suggestions);
		void setVarNameLabel(String text);
		void setQualifierListBox(int index);
		void setNoResultsLabel(List<VariableDetailsLight> data);
		void clearTermIDText();
		void setTermIDText(String text);
		
		int getQualifierListBoxSelection();
		
		void clearResultPanel();
	}

	private final QueryServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;
	
	private List<VariableDetailsLight> variableDetailsLight;
	private VariableSearch variableSearch;
	private String manQuery = "";

	public AnnotVariablePresenter(QueryServiceAsync rpcService,	HandlerManager eventBus, Display view, VariableSearch variableSearch) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		this.variableSearch = variableSearch;
	}
	
	public AnnotVariablePresenter(QueryServiceAsync rpcService,	HandlerManager eventBus, Display view, String manQuery, VariableSearch variableSearch) {
		this(rpcService,eventBus,view,variableSearch);		
		this.manQuery = manQuery;
	}

	public void bind() {
		
		display.getAddButton().addClickHandler(new ClickHandler() {			
			public void onClick(ClickEvent event) {	
				display.clearResultPanel();
				addAnnotation();
			}
		});	
		
		display.getManQueryButton().addClickHandler(new ClickHandler() {			
			public void onClick(ClickEvent event) {				
				eventBus.fireEvent(new MSyntaxQueryConstructEvent(AppController.VARANNOT_PAGE, variableSearch));
			}
		});
		
		display.getCancelButton().addClickHandler(new ClickHandler() {			
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new CancelAnnotEvent(variableSearch));
				
			}
		});
	}
	
	public void go(final HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());
		uploadQualifierListBox();
		setSearchParameters();
		
	}
	
	private void setSearchParameters(){
		display.setVarNameLabel(variableSearch.getVariableUrl());
		
		if(!manQuery.isEmpty()){
			rpcService.getTermToAnnotate(manQuery, new AsyncCallback<String>() {
				public void onSuccess(String result) {
					display.setTermIDText(result);						
				}
				
				public void onFailure(Throwable caught) {
					try {
				       throw caught;
				     } catch (ManchesterQueryException e) {
				    	 Window.alert(e.getDisplayMessage()+ "\n\n Details:\n"+e.getErrorMessage());
				     } catch (Throwable e) {
				    	 Window.alert(e.getMessage());
				     }						
				}
			});
		}
		searchModelData();
	}

	public void setContactDetails(List<VariableDetailsLight> variableDetailsLight) {
		this.variableDetailsLight = variableDetailsLight;
	}


	private void searchModelData(){
		rpcService.getVariableAnnotationList(variableSearch, new AsyncCallback<ArrayList<VariableDetailsLight>>() {
			public void onSuccess(ArrayList<VariableDetailsLight> result) {
				variableDetailsLight = result;
				display.setData(variableDetailsLight);
				display.setNoResultsLabel(variableDetailsLight);

			}

			public void onFailure(Throwable caught) {
				Window.alert("Error searching data");
			}
		});	
	}
	
	private void uploadQualifierListBox(){
		rpcService.getMiriamQualifiers(new AsyncCallback<ArrayList<String>>() {
			public void onSuccess(ArrayList<String> result) {				
				display.setQualifierListBox(result);
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error suggesting Manchester query");
			}
		});		
	}	
	
	private void addAnnotation(){
		rpcService.addAnnotation(variableSearch.getVariableUrl(), display.getQualifierListBoxText(), display.getTermIDText(), new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				display.clearTermIDText();
				searchModelData();				
			}
			@Override
			public void onFailure(Throwable caught) {
				try {
			       throw caught;
			     } catch (AnnotVariableException e) {
			    	 Window.alert(e.getDisplayMessage());
			     } catch (Throwable e) {
			    	 Window.alert(e.getMessage());
			     }				
			}


		});
	}

}
