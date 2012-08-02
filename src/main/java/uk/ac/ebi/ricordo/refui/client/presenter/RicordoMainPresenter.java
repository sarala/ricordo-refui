package uk.ac.ebi.ricordo.refui.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import uk.ac.ebi.ricordo.refui.client.QueryServiceAsync;
import uk.ac.ebi.ricordo.refui.client.event.AnnotAppEvent;
import uk.ac.ebi.ricordo.refui.client.event.CompAppEvent;
import uk.ac.ebi.ricordo.refui.client.event.QueryAppEvent;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class RicordoMainPresenter implements Presenter{
	
	public interface Display {
		Widget asWidget();
		HasClickHandlers getQueryAppButton();
		HasClickHandlers getCompAppButton();
		HasClickHandlers getAnnotAppButton();
    }
	
	private final QueryServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;
	
	public RicordoMainPresenter(QueryServiceAsync rpcService,	HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}
	
	public void bind() {			
		display.getQueryAppButton().addClickHandler(new ClickHandler() {			
			public void onClick(ClickEvent event) {	
				eventBus.fireEvent(new QueryAppEvent());
			}
		});
		
		display.getCompAppButton().addClickHandler(new ClickHandler() {			
			public void onClick(ClickEvent event) {	
				eventBus.fireEvent(new CompAppEvent());
			}
		});
		
		display.getAnnotAppButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new AnnotAppEvent());				
			}
		});
	}
	
	
	
	@Override
	public void go(HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());		
	}

}
