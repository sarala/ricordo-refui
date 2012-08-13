package uk.ac.ebi.ricordo.refui.client.presenter;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.Widget;
import uk.ac.ebi.ricordo.refui.client.AppController;
import uk.ac.ebi.ricordo.refui.client.QueryServiceAsync;
import uk.ac.ebi.ricordo.refui.client.event.MSyntaxQueryCancelEvent;
import uk.ac.ebi.ricordo.refui.client.event.MSyntaxQuerySelectEvent;
import uk.ac.ebi.ricordo.refui.client.exception.RemoteOntologyServiceException;
import uk.ac.ebi.ricordo.refui.client.view.RelationSuggestBox;
import uk.ac.ebi.ricordo.refui.client.view.TermSuggestBox;
import uk.ac.ebi.ricordo.refui.shared.ModelDetailsLight;
import uk.ac.ebi.ricordo.refui.shared.VariableSearch;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ManchesterSyntaxPresenter implements Presenter {	

	public interface Display {	
		Widget asWidget();
		
		String getManQueryTypeListBoxText();
		
		HasClickHandlers getSelectButton();	
		HasClickHandlers getCancelButton();
		HasChangeHandlers getManQueryTypeListBox();		

		void setManQueryTypeListBox(ArrayList<String> suggestions);
		void constructManQueryPanel(String querytype);
		void setSuggestBoxObject(List<String> result,	SuggestBox suggestBox);	
		void setManQueryTypeSelection(int index);

		int getManQueryTypeSelection();
		
		ArrayList<Widget> getQueryWidgits();	

	}

	private final QueryServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;
	private String objectText = "";
	private List<ModelDetailsLight> modelDetailsLight;
	private String manQuery = "";
	private String parentPage = "";
	private List<String> result = new LinkedList<String>();
	private VariableSearch variableSearch;

	public ManchesterSyntaxPresenter(QueryServiceAsync rpcService,	HandlerManager eventBus, Display view, String parentPage) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		this.parentPage = parentPage;
	}
	
	public ManchesterSyntaxPresenter(QueryServiceAsync rpcService,	HandlerManager eventBus, Display view, String parentPage, VariableSearch variableSearch) {
		this(rpcService, eventBus, view, parentPage);
		this.variableSearch = variableSearch;
	}

	public void go(final HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());
		uploadManQueryType();
//        setUpManQueryPanel();
	}

    private void setUpManQueryPanel() {
        display.setManQueryTypeSelection(0);
        display.constructManQueryPanel(display.getManQueryTypeListBoxText());
        manQuery = display.getManQueryTypeListBoxText();
        setSuggestBoxHandlers();
    }

    public void bind() {
		
		display.getSelectButton().addClickHandler(new ClickHandler() {			
			public void onClick(ClickEvent event) {	
				if(parentPage.equals(AppController.VARANNOT_PAGE)){
					eventBus.fireEvent(new MSyntaxQuerySelectEvent(manQuery, parentPage, variableSearch));
				}
				else{
					eventBus.fireEvent(new MSyntaxQuerySelectEvent(manQuery, parentPage));
				}
			}
		});
		
		display.getCancelButton().addClickHandler(new ClickHandler() {			
			public void onClick(ClickEvent event) {	
				if(parentPage.equals(AppController.VARANNOT_PAGE)){
					eventBus.fireEvent(new MSyntaxQueryCancelEvent(parentPage, variableSearch));
				}
				else{
					eventBus.fireEvent(new MSyntaxQueryCancelEvent(parentPage));
				}				
			}
		});

		display.getManQueryTypeListBox().addChangeHandler(new ChangeHandler() {			
			@Override
			public void onChange(ChangeEvent event) {
				display.constructManQueryPanel(display.getManQueryTypeListBoxText());
				manQuery = display.getManQueryTypeListBoxText();
				setSuggestBoxHandlers();				
			}		
		
		});		
	}
	
	private void setSuggestBoxHandlers() {
		ArrayList<Widget> widgets = display.getQueryWidgits();
		for (Widget widget : widgets) {
			if (widget instanceof TermSuggestBox) {
				final TermSuggestBox suggestBox = (TermSuggestBox) widget;
				suggestBox.addKeyUpHandler(new KeyUpHandler() {
					@Override
					public void onKeyUp(KeyUpEvent event) {	
						if ( !objectText.equals(suggestBox.getText()) && (suggestBox.getText().length()) > 2) {
							uploadSuggestBox(suggestBox);
							objectText = suggestBox.getText();
						}
						else{
							display.setSuggestBoxObject(result, suggestBox);
						}

					}
				});

				suggestBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
							@Override
							public void onSelection(SelectionEvent<SuggestOracle.Suggestion> event) {
								String selection = event.getSelectedItem().getReplacementString();

								//suggestBox.setText(selection.substring(selection.indexOf("-") + 2, selection.length()));
								suggestBox.setText(selection.substring(0,selection.indexOf("(") - 1));

								int index = display.getQueryWidgits().lastIndexOf(suggestBox);
								String[] queryType = manQuery.split(" ");
								manQuery = "";
								for (int i = 0; i < queryType.length; i++) {
									if (i == index) {
										manQuery = manQuery	+ selection.substring(selection.indexOf("(")+1,selection.length()-1).replace(":", "_");
									} else {
										manQuery = manQuery + queryType[i];
									}
									manQuery = manQuery + " ";
								}								
							}
						});
			}
			
			if (widget instanceof RelationSuggestBox) {
				final RelationSuggestBox suggestBox = (RelationSuggestBox) widget;
				suggestBox.addKeyUpHandler(new KeyUpHandler() {
					@Override
					public void onKeyUp(KeyUpEvent event) {	
						uploadSuggestBox(suggestBox);
						display.setSuggestBoxObject(result, suggestBox);
					}
				});

				suggestBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
							@Override
							public void onSelection(SelectionEvent<SuggestOracle.Suggestion> event) {
								String selection = event.getSelectedItem().getReplacementString();
								selection = selection.split(" ")[0];

								suggestBox.setText(selection);

								int index = display.getQueryWidgits().lastIndexOf(suggestBox);
								String[] queryType = manQuery.split(" ");
								manQuery = "";
								for (int i = 0; i < queryType.length; i++) {
									if (i == index) {
										manQuery = manQuery	+ selection;
									} else {
										manQuery = manQuery + queryType[i];
									}
									manQuery = manQuery + " ";
								}								
							}
						});
			}
			
		}

	}
	
	public void setContactDetails(List<ModelDetailsLight> modelDetailsLight) {
		this.modelDetailsLight = modelDetailsLight;
	}

	public ModelDetailsLight getContactDetail(int index) {
		return modelDetailsLight.get(index);
	}
	
	private void uploadManQueryType(){
		rpcService.getManQueryTypes(new AsyncCallback<ArrayList<String>>() {
			public void onSuccess(ArrayList<String> result) {				
				display.setManQueryTypeListBox(result);
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error suggesting Manchester query");
			}
		});		
	}
	
	private void uploadSuggestBox(final SuggestBox suggestBox){
		if(suggestBox.getText().isEmpty())
			return;
		
		if(suggestBox instanceof TermSuggestBox){			
			rpcService.getOntoTerms(suggestBox.getText(), new AsyncCallback<LinkedList<String>>() {
				public void onSuccess(LinkedList<String> result) {
					ManchesterSyntaxPresenter.this.result = result;
					display.setSuggestBoxObject(result, suggestBox);
				}
	
				public void onFailure(Throwable caught) {
					try {
				       throw caught;
				     } catch (RemoteOntologyServiceException e) {
				    	 Window.alert(e.getDisplayMessage()+ "\n\n Details:\n"+e.getErrorMessage());
				     } catch (Throwable e) {
				    	 Window.alert(e.getMessage());
				     }	
				}
			});		
		}
		else if(suggestBox instanceof RelationSuggestBox){
			rpcService.getRelations(new AsyncCallback<ArrayList<String>>(){
				public void onSuccess(ArrayList<String> result) {
					ManchesterSyntaxPresenter.this.result = result;
					display.setSuggestBoxObject(result, suggestBox);
				}
				public void onFailure(Throwable caught) {
					Window.alert("Error suggesting relations");
				}				
			});
		}
		
		
		
	}
}

