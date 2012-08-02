package uk.ac.ebi.ricordo.refui.client.view;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ContactsPanel extends HorizontalPanel{
	
	public ContactsPanel(){
		setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		setWidth("20%");
		//setStyleName("pannel-Border");
		createContacts();
	}
	
	private void createContacts(){	
		
		HTML help = new HTML("<a href=\"https://sites.ac.com/site/ricordotoolset/documentation\" target=\"_blank\">Tutorial</a>");
		this.add(help);
		
		HTML ricordo = new HTML("<a href=\"http://ricordo.eu/\" target=\"_blank\">RICORDO</a>"); 
		this.add(ricordo);
		
		HTML contact = new HTML("<a href=\"mailto:sarala@ebi.ac.uk\">Contact Us</a>"); 
		this.add(contact);
	}

}
