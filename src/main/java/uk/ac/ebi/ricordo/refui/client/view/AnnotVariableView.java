package uk.ac.ebi.ricordo.refui.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import uk.ac.ebi.ricordo.refui.client.presenter.AnnotVariablePresenter;
import uk.ac.ebi.ricordo.refui.shared.VariableDetailsLight;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AnnotVariableView extends RicordoView implements AnnotVariablePresenter.Display {
	private final Button addButton = new Button("Add annotation");
	private final Button manQueryButton = new Button("Find term ID");
	private final Button cancelButton = new Button("Back");
	private final ListBox qualifierListBox = new ListBox();
	private ResultsPanel resultsPanel;
	private final FlexTable searchTable = new FlexTable();
	private final Label varNameLabel = new Label();
	private final TextBox termIDText = new TextBox();

	public AnnotVariableView() {
		super(TitlePanel.ANNOT_TITLE);
		contentDetailsPanel.add(discriptionPanel());
		contentDetailsPanel.add(createVariablePanel());
		contentDetailsPanel.add(createResultsPanel());
		contentDetailsPanel.add(createAnnotPanel());
	}
	
	private VerticalPanel discriptionPanel(){
		VerticalPanel discriptionPanel = new VerticalPanel();
		discriptionPanel.setStyleName("pannel-Border");
		discriptionPanel.setWidth("100%");
		Label discriptionLabel = new Label("This application supports annotations of Virtual Physiological Human data and models (VPHDM)s.");
		discriptionPanel.add(discriptionLabel);
		return discriptionPanel;
	}

	private VerticalPanel createVariablePanel() {
		VerticalPanel searchPanel = new VerticalPanel();		
		searchPanel.setStyleName("pannel-Border");
		searchPanel.setWidth("100%");
		
		FlexTable searchTable = new FlexTable();		
		searchTable.getColumnFormatter().setWidth(0, "150em");
		searchTable.setWidget(0, 0, new Label("Variable Details"));
		
		searchTable.setWidget(1, 0, new Label("Variabl URL"));
		searchTable.setWidget(1, 1, varNameLabel);
		varNameLabel.setWidth("400");
		
		searchPanel.add(searchTable);
		return searchPanel;
	}

	private VerticalPanel createResultsPanel() {
		resultsPanel = new ResultsPanel();
		resultsPanel.setResultTableSize(new String[] { "80px", "500px", "80px" });

		return resultsPanel;
	}
	
	private VerticalPanel createAnnotPanel() {
		VerticalPanel annotPanel = new VerticalPanel();		
		annotPanel.setStyleName("pannel-Border");
		annotPanel.setWidth("100%");
		
		
		searchTable.getColumnFormatter().setWidth(0, "150em");
		searchTable.setWidget(0, 0, new Label("Create New Annotations"));
		
		searchTable.setWidget(1, 0, new Label("Qualifier"));
		searchTable.setWidget(1, 1, qualifierListBox);
		
		searchTable.setWidget(2, 0, new Label("Term ID"));
		searchTable.setWidget(2, 1, termIDText);		
		termIDText.setWidth("400");
		
		searchTable.setWidget(3, 1, createButtonPanel());
		annotPanel.add(searchTable);
		return annotPanel;
	}
	
	public HorizontalPanel createButtonPanel(){
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setSpacing(10);
		buttonPanel.add(addButton);
		buttonPanel.add(manQueryButton);
		buttonPanel.add(cancelButton);
		return buttonPanel;
	}
	
	public String getQualifierListBoxText() {
		return qualifierListBox.getItemText(qualifierListBox.getSelectedIndex());
	}

	public String getVarNameLabel(){
		return varNameLabel.getText();
	}
	
	public void setVarNameLabel(String text){
		varNameLabel.setText(text);
	}
	
	public void setTermIDText(String text){
		termIDText.setText(text);
	}
	
	public HasClickHandlers getAddButton() {
		return addButton;
	}
	
	public HasClickHandlers getManQueryButton() {
		return manQueryButton;
	}
	
	public HasClickHandlers getCancelButton() {
		return cancelButton;
	}
	
	public String getTermIDText(){
		return termIDText.getText();
	}
	
	public void clearTermIDText(){
		termIDText.setText("");
	}

	public HasClickHandlers getList() {
		return resultsPanel.getResultsTable();
	}

	public void clearResultPanel(){
		resultsPanel.setResultsLabel("");
		resultsPanel.clearResultTable();
	}
	
	public void setData(List<VariableDetailsLight> data) {	
		resultsPanel.setResultsLabel("");
		resultsPanel.clearResultTable();

		if (data.isEmpty()){			
			return;
		}	
		
		resultsPanel.addResultRow(0, new String[] { "Index", "Property", "Annotation" });

		for (int i = 0; i < data.size(); ++i) {
			resultsPanel.addResultRow(i + 1,data.get(i).getDisplayContent());
		}

		resultsPanel.applyResultTableStyles();
	}
	
	public void setNoResultsLabel(List<VariableDetailsLight> data){
		if (data.isEmpty()){
			resultsPanel.setResultsLabel("No results found for this query");
			return;
		}	
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

	public void setQualifierListBox(ArrayList<String> suggestions) {
		for (String suggest : suggestions) {
			qualifierListBox.addItem(suggest);
		}
	}
	
	public HasChangeHandlers getQualifierListBox(){
		return qualifierListBox;
	}
	
	public int getQualifierListBoxSelection(){
		return qualifierListBox.getSelectedIndex();
	}
	
	public void setQualifierListBox(int index){
		qualifierListBox.setItemSelected(index,true);
	}

	public Widget asWidget() {
		return this;
	}
}