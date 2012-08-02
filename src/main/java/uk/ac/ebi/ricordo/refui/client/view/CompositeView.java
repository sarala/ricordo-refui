package uk.ac.ebi.ricordo.refui.client.view;

import java.util.List;

import com.google.gwt.event.dom.client.HasClickHandlers;
import uk.ac.ebi.ricordo.refui.client.presenter.CompositePresenter;
import uk.ac.ebi.ricordo.refui.shared.CompositeDetailsLight;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CompositeView extends RicordoView implements CompositePresenter.Display {
	private final Button createCompButton = new Button("Create Composite");
	private final Button manQueryButton = new Button("Use template");
	private final BackToMainButton backToMainButton = new BackToMainButton();
	private ResultsPanel resultsPanel;
	private final FlexTable compositeTable = new FlexTable();
	private final TextBox manQueryText = new TextBox();

	public CompositeView() {
		super(TitlePanel.COMP_TITLE);
		contentDetailsPanel.add(discriptionPanel());
		contentDetailsPanel.add(createSearchPanel());
		contentDetailsPanel.add(createResultsPanel());
	}
	
	private VerticalPanel discriptionPanel(){
		VerticalPanel discriptionPanel = new VerticalPanel();
		discriptionPanel.setStyleName("pannel-Border");
		discriptionPanel.setWidth("100%");
		Label discriptionLabel = new Label("This application supports creating composites.");
		discriptionPanel.add(discriptionLabel);
		return discriptionPanel;
	}

	private VerticalPanel createSearchPanel() {
		VerticalPanel searchPanel = new VerticalPanel();		
		searchPanel.setStyleName("pannel-Border");
		searchPanel.setWidth("100%");		
		
		compositeTable.getColumnFormatter().setWidth(0, "150em");

		compositeTable.setWidget(0, 0, new Label("Composite"));
		compositeTable.setWidget(0, 1, manQueryText);
		manQueryText.setWidth("400");

		searchPanel.add(compositeTable);
		
		compositeTable.setWidget(3, 1, createButtonPanel());
		return searchPanel;
	}
	
	public HorizontalPanel createButtonPanel(){
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setSpacing(10);
		buttonPanel.add(createCompButton);
		buttonPanel.add(manQueryButton);
		buttonPanel.add(backToMainButton);
		return buttonPanel;
	}
	
	public void setCreateCompButtonStatus(boolean status){
		if(status){
			createCompButton.setText("Create Composite");
		}else{
			createCompButton.setText("Creating Composite...");
		}
		createCompButton.setEnabled(status);
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
	
	public HasClickHandlers getCreateCompButton() {
		return createCompButton;
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
	
	public void setData(List<CompositeDetailsLight> data) {
		resultsPanel.setResultsLabel("");
		resultsPanel.clearResultTable();

		if (data.isEmpty()){			
			return;
		}	
		
		resultsPanel.addResultRow(0, new String[] { "Index", "Composite", "Status" });

		for (int i = 0; i < data.size(); ++i) {
			resultsPanel.addResultRow(i + 1,data.get(i).getDisplayContent());
		}

		resultsPanel.applyResultTableStyles();
	}	

	public Widget asWidget() {
		return this;
	}

}
