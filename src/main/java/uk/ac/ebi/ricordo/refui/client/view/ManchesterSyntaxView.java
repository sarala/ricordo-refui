package uk.ac.ebi.ricordo.refui.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import uk.ac.ebi.ricordo.refui.client.presenter.ManchesterSyntaxPresenter;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ManchesterSyntaxView extends RicordoView implements ManchesterSyntaxPresenter.Display {
	private final Button selectButton = new Button("Select");
	private final Button cancelButton = new Button("Cancel");
	private final ListBox manQueryTypeListBox = new ListBox();
	private MultiWordSuggestOracle ontoTermOracle = new MultiWordSuggestOracle();
	private final FlexTable searchTable = new FlexTable();
	private ArrayList<Widget> queryWidgits = new ArrayList<Widget>();
	private final HorizontalPanel manQueryPanel = new HorizontalPanel();

	public ManchesterSyntaxView(String title) {
		super(title);
		contentDetailsPanel.add(discriptionPanel());
		contentDetailsPanel.add(createSearchPanel());
	}
	
	private VerticalPanel discriptionPanel(){
		VerticalPanel discriptionPanel = new VerticalPanel();
		discriptionPanel.setStyleName("pannel-Border");
		discriptionPanel.setWidth("100%");
		Label discriptionLabel = new Label("This page supports construction of queryies using Manchester query syntax.");
		discriptionPanel.add(discriptionLabel);
		return discriptionPanel;
	}

	private VerticalPanel createSearchPanel() {
		VerticalPanel searchPanel = new VerticalPanel();		
		searchPanel.setStyleName("pannel-Border");
		searchPanel.setWidth("100%");
		
		
		searchTable.getColumnFormatter().setWidth(0, "150em");
		searchTable.setWidget(0, 0, new Label("Query type"));
		searchTable.setWidget(0, 1, manQueryTypeListBox);
		
		searchTable.setWidget(1, 0, new Label("Query construct"));
		searchTable.setWidget(1, 1, manQueryPanel);
		
		manQueryTypeListBox.setFocus(true);

		searchTable.setWidget(2, 1, createButtonPanel());
		
		searchPanel.add(searchTable);
		return searchPanel;
	}
	
	public HorizontalPanel createButtonPanel(){
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setSpacing(10);
		buttonPanel.add(selectButton);
		buttonPanel.add(cancelButton);
		return buttonPanel;
	}
	
	public void constructManQueryPanel(String query){
		manQueryPanel.clear();
		queryWidgits.clear();
		String [] tokens = query.split(" ");
		for (int i =0; i<tokens.length;i++) {
			String token = tokens[i];
			Widget widget;
			if(token.equals("term")){
				widget = new TermSuggestBox(ontoTermOracle);				
				manQueryPanel.add(widget);
			}
			else if (token.equals("relation")){				
				widget = new RelationSuggestBox(ontoTermOracle);				
				manQueryPanel.add(widget);
				
			}
			else{
				widget = new Label(token);
				manQueryPanel.add(widget);
				widget.setWidth("60px");
			}
			queryWidgits.add(widget);
			
        }		
	}
	
	public ArrayList<Widget> getQueryWidgits(){
		return queryWidgits;
	}
	

	public String getManQueryTypeListBoxText() {
		return manQueryTypeListBox.getItemText(manQueryTypeListBox.getSelectedIndex());
	}

	public HasClickHandlers getSelectButton() {
		return selectButton;
	}
	
	public  HasClickHandlers getCancelButton() {
		return cancelButton;
	}	

	public void setManQueryTypeListBox(ArrayList<String> suggestions) {
		for (String suggest : suggestions) {
			manQueryTypeListBox.addItem(suggest);
		}
	}
	
	public HasChangeHandlers getManQueryTypeListBox(){
		return manQueryTypeListBox;
	}
	
	public int getManQueryTypeSelection(){
		return manQueryTypeListBox.getSelectedIndex();
	}
	
	public void setManQueryTypeSelection(int index){
		manQueryTypeListBox.setItemSelected(index,true);
	}

	public Widget asWidget() {
		return this;
	}

	@Override
	public void setSuggestBoxObject(List<String> result, SuggestBox suggestBox) {
		ontoTermOracle.clear();
		for (String suggest : result) {
			ontoTermOracle.add(suggest);
		}
		suggestBox.showSuggestionList();		
	}	
}
