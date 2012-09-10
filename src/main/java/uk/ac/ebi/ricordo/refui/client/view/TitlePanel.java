package uk.ac.ebi.ricordo.refui.client.view;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TitlePanel extends VerticalPanel{
	
	public static String ANNOT_TITLE = ("Annotation Editor");
	public static String COMP_TITLE = ("Composite Editor");
	public static String QUERY_TITLE = ("Query Service");
	public static String MAIN_TITLE = ("Reference User Interface");
	public static String MAN_TITLE = ("Manchester Query Creator");
	
	public TitlePanel(String title){
		setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		setWidth("100%");
		setStyleName("pannel-Border");
		createTitle(title);
	}
	
	private void createTitle(String title){
		Label titleLabel = new Label(title);
		titleLabel.setStyleName("query-Title");
		this.add(titleLabel);
	}
}
