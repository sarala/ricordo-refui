package uk.ac.ebi.ricordo.refui.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import uk.ac.ebi.ricordo.refui.client.event.BackToMainEvent;
import com.google.gwt.user.client.ui.Button;

public class BackToMainButton extends Button{
	
	public BackToMainButton(){
		setText("Back to RICORDO toolset");
	}
	
	public void addAction(final HandlerManager eventBus){
		addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new BackToMainEvent());				
			}
		});
	}

}
