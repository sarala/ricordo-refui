package uk.ac.ebi.ricordo.refui.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import uk.ac.ebi.ricordo.refui.client.presenter.RicordoMainPresenter;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RicordoMainView extends RicordoView implements RicordoMainPresenter.Display {
	
	private final Button queryAppButton = new Button("Query");
	private final Button compAppButton = new Button("Compose");
	private final Button annotAppButton = new Button("Annotate");
	
	public RicordoMainView(){
		super(TitlePanel.MAIN_TITLE);
		contentDetailsPanel.add(createSelectPanel());
	}
	
	private VerticalPanel createSelectPanel() {
		VerticalPanel searchPanel = new VerticalPanel();		
		searchPanel.setStyleName("pannel-Border");
		searchPanel.setWidth("100%");
		
		FlexTable selectTable = new FlexTable();
		selectTable.getColumnFormatter().setWidth(0, "150em");
		selectTable.setWidget(0, 0, new Label("Query application"));
		selectTable.setWidget(0, 1, queryAppButton);
		queryAppButton.setWidth("2cm");
		
		selectTable.setWidget(1, 0, new Label("Composite application"));
		selectTable.setWidget(1, 1, compAppButton);
		compAppButton.setWidth("2cm");
		
		selectTable.setWidget(2, 0, new Label("Annotation application"));
		selectTable.setWidget(2, 1, annotAppButton);
		annotAppButton.setWidth("2cm");
		
		searchPanel.add(selectTable);		
		return searchPanel;
	}
	
	public Widget asWidget() {
		return this;
	}
	
	public HasClickHandlers getQueryAppButton() {
		return queryAppButton;
	}
	
	public HasClickHandlers getCompAppButton() {
		return compAppButton;
	}
	
	public HasClickHandlers getAnnotAppButton() {
		return annotAppButton;
	}

}
