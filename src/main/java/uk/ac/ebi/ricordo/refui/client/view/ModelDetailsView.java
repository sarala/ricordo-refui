package uk.ac.ebi.ricordo.refui.client.view;

import java.util.List;

import com.google.gwt.event.dom.client.HasClickHandlers;
import uk.ac.ebi.ricordo.refui.client.presenter.ModelDetailsPresenter;
import uk.ac.ebi.ricordo.refui.shared.VariableDetailsLight;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ModelDetailsView extends RicordoView implements ModelDetailsPresenter.Display {
  private final FlexTable detailsTable;
  private final Button cancelButton;
  private Label titleLabel = new Label();
  private ResultsPanel resultsPanel;
  
  public ModelDetailsView() {
	super(TitlePanel.QUERY_TITLE);
    contentDetailsPanel.add(createResultsPanel());
    
    detailsTable = new FlexTable();
    detailsTable.setCellSpacing(0);
    detailsTable.setWidth("100%");
    detailsTable.addStyleName("contacts-ListContainer");
    detailsTable.getColumnFormatter().addStyleName(1, "add-contact-input");
    
    HorizontalPanel menuPanel = new HorizontalPanel();
    menuPanel.setStyleName("pannel-Border");
    menuPanel.setWidth("100%");
    cancelButton = new Button("Back");
    
//    menuPanel.add(saveButton);
    menuPanel.add(cancelButton);
    contentDetailsPanel.add(menuPanel);
  }
  
 
  private VerticalPanel createResultsPanel(){
	  resultsPanel = new ResultsPanel();
	  resultsPanel.setResultTableSize(new String []{"80px","500px","200px","250px"});
	  
	 return resultsPanel;
	  
  }
  
  
  public HasClickHandlers getCancelButton() {
    return cancelButton;
  }
	
  public void setData(List<VariableDetailsLight> data) {
	  	resultsPanel.clearResultTable();	  	
	  	resultsPanel.addResultRow(0, new String []{"Index","Variable URL","Biological Qualifier","MIRIAM URN"});

		for (int i = 0; i < data.size(); ++i) {
			VariableDetailsLight variableDetails = data.get(i);
			resultsPanel.addResultRow(i+1,variableDetails.getDisplayContent());
		}
		
		resultsPanel.applyResultTableStyles();
		
	}
  
	

  public HasText getTitleLabel() {
	     return titleLabel;
  }  
  
  public Widget asWidget() {
    return this;
  }
}
