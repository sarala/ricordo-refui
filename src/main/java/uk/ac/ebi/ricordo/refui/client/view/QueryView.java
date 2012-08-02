package uk.ac.ebi.ricordo.refui.client.view;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import uk.ac.ebi.ricordo.refui.client.presenter.QueryPresenter;
import uk.ac.ebi.ricordo.refui.shared.ModelDetailsLight;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class QueryView extends RicordoView implements QueryPresenter.Display {
	private final Button searchButton = new Button("Search");
	private final Button manQueryButton = new Button("Use template");
	private final BackToMainButton backToMainButton = new BackToMainButton();
	private ResultsPanel resultsPanel;
	private final FlexTable searchTable = new FlexTable();
	private final TextBox manQueryText = new TextBox();

	public QueryView() {
		super(TitlePanel.QUERY_TITLE);
		contentDetailsPanel.add(discriptionPanel());
		contentDetailsPanel.add(createSearchPanel());
		contentDetailsPanel.add(createResultsPanel());
	}
	
	private VerticalPanel discriptionPanel(){
		VerticalPanel discriptionPanel = new VerticalPanel();
		discriptionPanel.setStyleName("pannel-Border");
		discriptionPanel.setWidth("100%");
		Label discriptionLabel = new Label("This application supports querying of annotations of Virtual Physiological Human data and models (VPHDM)s. Manchester query syntax is used to find relevant ontological terms. The annotation repository which is in RDF is queried using SPARQL to find VPHDMs with annotations to the relevant terms.");
		discriptionPanel.add(discriptionLabel);
		return discriptionPanel;
	}

	private VerticalPanel createSearchPanel() {
		VerticalPanel searchPanel = new VerticalPanel();		
		searchPanel.setStyleName("pannel-Border");
		searchPanel.setWidth("100%");
		
		
		searchTable.getColumnFormatter().setWidth(0, "150em");
		searchTable.setWidget(0, 0, new Label("Query"));
		searchTable.setWidget(0, 1, manQueryText);
		manQueryText.setWidth("400");
		
		Label searchLabel = new Label("Find VPHDMs related to:");
		searchPanel.add(searchLabel);
		searchPanel.add(searchTable);

		searchTable.setWidget(3, 1, createButtonPanel());
		return searchPanel;
	}
	
	public HorizontalPanel createButtonPanel(){
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setSpacing(10);
		buttonPanel.add(searchButton);
		buttonPanel.add(manQueryButton);
		buttonPanel.add(backToMainButton);
		return buttonPanel;
	}
	
	public void setSearchButtonStatus(boolean status){
		if(status){
			searchButton.setText("Search");
		}else{
			searchButton.setText("Searching...");
		}
		searchButton.setEnabled(status);
	}

	private VerticalPanel createResultsPanel() {
		resultsPanel = new ResultsPanel();
		resultsPanel.setResultTableSize(new String[] { "80px", "500px", "80px" });

		return resultsPanel;
	}

	public String getManQueryText(){
		return manQueryText.getText();
	}
	
	public void setManQueryText(String text){
		manQueryText.setText(text);
	}
	
	public HasClickHandlers getSearchButton() {
		return searchButton;
	}
	
	public HasClickHandlers getManQueryButton() {
		return manQueryButton;
	}
	
	public BackToMainButton getBackToMainButton() {
		return backToMainButton;
	}

	public HasClickHandlers getList() {
		return resultsPanel.getResultsTable();
	}

	public void clearResultPanel(){
		resultsPanel.setResultsLabel("");
		resultsPanel.clearResultTable();
	}
	
	public void setData(List<ModelDetailsLight> data) {	
		resultsPanel.setResultsLabel("");
		resultsPanel.clearResultTable();

		if (data.isEmpty()){			
			return;
		}	
		
		resultsPanel.addResultRow(0, new String[] { "Index", "Model URL", "Frequency" });

		for (int i = 0; i < data.size(); ++i) {
			resultsPanel.addResultRow(i + 1,data.get(i).getDisplayName());
		}

		resultsPanel.applyResultTableStyles();
	}
	
	public void setNoResultsLabel(List<ModelDetailsLight> data){
		if (data.isEmpty()){
			resultsPanel.setResultsLabel("No results found for this query");
			return;
		}	
	}

	public int getClickedRow(ClickEvent event) {
		int selectedRow = -1;
		HTMLTable.Cell cell = resultsPanel.getResultsTable().getCellForEvent(event);

		if (cell != null) {
			// Suppress clicks if the user is actually selecting the
			// check box
			//
			if (cell.getCellIndex() > 0) {
				selectedRow = cell.getRowIndex();
			}
		}

		return selectedRow;
	}

	/*
	 * public List<Integer> getSelectedRows() { List<Integer> selectedRows = new
	 * ArrayList<Integer>();
	 * 
	 * for (int i = 0; i < resultsTable.getRowCount(); ++i) { CheckBox checkBox
	 * = (CheckBox) resultsTable.getWidget(i, 0); if (checkBox.getValue()) {
	 * selectedRows.add(i); } }
	 * 
	 * return selectedRows; }
	 */


	public Widget asWidget() {
		return this;
	}
}
