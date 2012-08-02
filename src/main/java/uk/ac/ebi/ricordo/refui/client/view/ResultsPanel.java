package uk.ac.ebi.ricordo.refui.client.view;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ResultsPanel extends VerticalPanel{
	private FlexTable resultsTable;
	private Label resultsLabel = new Label();
	
	public ResultsPanel(){
		setStyleName("pannel-Border");
		setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
		setWidth("100%");
		
		add(resultsLabel);
		
		resultsTable = new FlexTable();	
		resultsTable.setStylePrimaryName("resultsTable");		
		add(resultsTable);
	}
	
	public void setResultsLabel(String resultLabelTxt){
		resultsLabel.setText(resultLabelTxt);
			
	}
	
	public FlexTable getResultsTable(){
		return resultsTable;
	}
	
	public void setResultTableSize(String [] sizes){
		for(int i=0; i<sizes.length; i++){
			resultsTable.getColumnFormatter().setWidth(i, sizes[i]);
		}
	}
	
	public void applyResultTableStyles() {
		HTMLTable.RowFormatter rf = resultsTable.getRowFormatter();
		rf.addStyleName(0, "results-ColumnLabelCell");

		for (int row = 1; row < resultsTable.getRowCount(); ++row) {
			if ((row % 2) != 0) {
				rf.addStyleName(row, "results-OddRow");
			} else {
				rf.addStyleName(row, "results-EvenRow");
			}
		}
	}
	
	public void addResultRow(int row, String [] values){
		for(int i=0;i<values.length;i++){			
			resultsTable.setText(row, i, values[i]);
		}
	}
	
	public void clearResultTable(){
		resultsTable.removeAllRows();
	}
	
}
