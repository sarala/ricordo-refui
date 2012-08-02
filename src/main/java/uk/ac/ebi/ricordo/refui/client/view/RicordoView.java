package uk.ac.ebi.ricordo.refui.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class RicordoView extends Composite{
	protected final VerticalPanel contentDetailsPanel;
	
	public RicordoView(String title){
		DecoratorPanel contentTableDecorator = new DecoratorPanel();
		initWidget(contentTableDecorator);
		//contentTableDecorator.setWidth("100%");
		//contentTableDecorator.setWidth("20cm");
		
	    contentDetailsPanel = new VerticalPanel();
	    contentDetailsPanel.setWidth("100%");
	    
		VerticalPanel mainContentPanel = new VerticalPanel();
		mainContentPanel.setStyleName("pannel-Border");
		mainContentPanel.setWidth("100%");

	//    contentDetailsPanel.add();  
	    
	    mainContentPanel.add(new TitlePanel(title));
	    mainContentPanel.add(contentDetailsPanel);
	    mainContentPanel.add(new ContactsPanel());
	    
		contentTableDecorator.add(mainContentPanel);

	}
}
