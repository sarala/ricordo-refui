package uk.ac.ebi.ricordo.refui.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import uk.ac.ebi.ricordo.refui.client.event.AnnotAppEvent;
import uk.ac.ebi.ricordo.refui.client.event.AnnotAppEventHandler;
import uk.ac.ebi.ricordo.refui.client.event.AnnotVariableEvent;
import uk.ac.ebi.ricordo.refui.client.event.AnnotVariableEventHandler;
import uk.ac.ebi.ricordo.refui.client.event.BackToMainEvent;
import uk.ac.ebi.ricordo.refui.client.event.BackToMainEventHandler;
import uk.ac.ebi.ricordo.refui.client.event.CancelAnnotEvent;
import uk.ac.ebi.ricordo.refui.client.event.CancelAnnotEventHandler;
import uk.ac.ebi.ricordo.refui.client.event.CompAppEvent;
import uk.ac.ebi.ricordo.refui.client.event.CompAppEventHandler;
import uk.ac.ebi.ricordo.refui.client.event.MSyntaxQueryCancelEvent;
import uk.ac.ebi.ricordo.refui.client.event.MSyntaxQueryCancelHandler;
import uk.ac.ebi.ricordo.refui.client.event.MSyntaxQueryConstructEvent;
import uk.ac.ebi.ricordo.refui.client.event.MSyntaxQueryConstructEventHandler;
import uk.ac.ebi.ricordo.refui.client.event.MSyntaxQuerySelectEvent;
import uk.ac.ebi.ricordo.refui.client.event.MSyntaxQuerySelectEventHandler;
import uk.ac.ebi.ricordo.refui.client.event.ModelDetailsCancelledEvent;
import uk.ac.ebi.ricordo.refui.client.event.ModelDetailsCancelledEventHandler;
import uk.ac.ebi.ricordo.refui.client.event.ModelDetailsEvent;
import uk.ac.ebi.ricordo.refui.client.event.ModelDetailsEventHandler;
import uk.ac.ebi.ricordo.refui.client.event.QueryAppEvent;
import uk.ac.ebi.ricordo.refui.client.event.QueryAppEventHandler;
import uk.ac.ebi.ricordo.refui.client.presenter.AnnotVariablePresenter;
import uk.ac.ebi.ricordo.refui.client.presenter.AnnotationPresenter;
import uk.ac.ebi.ricordo.refui.client.presenter.CompositePresenter;
import uk.ac.ebi.ricordo.refui.client.presenter.ManchesterSyntaxPresenter;
import uk.ac.ebi.ricordo.refui.client.presenter.ModelDetailsPresenter;
import uk.ac.ebi.ricordo.refui.client.presenter.Presenter;
import uk.ac.ebi.ricordo.refui.client.presenter.QueryPresenter;
import uk.ac.ebi.ricordo.refui.client.presenter.RicordoMainPresenter;
import uk.ac.ebi.ricordo.refui.client.view.AnnotVariableView;
import uk.ac.ebi.ricordo.refui.client.view.AnnotationView;
import uk.ac.ebi.ricordo.refui.client.view.CompositeView;
import uk.ac.ebi.ricordo.refui.client.view.ManchesterSyntaxView;
import uk.ac.ebi.ricordo.refui.client.view.ModelDetailsView;
import uk.ac.ebi.ricordo.refui.client.view.QueryView;
import uk.ac.ebi.ricordo.refui.client.view.RicordoMainView;
import uk.ac.ebi.ricordo.refui.client.view.TitlePanel;
import uk.ac.ebi.ricordo.refui.shared.ModelSearch;
import uk.ac.ebi.ricordo.refui.shared.VariableSearch;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

public class AppController implements Presenter, ValueChangeHandler<String> {
  public static String QUERY_PAGE = "query";
  public static String COMPOSITE_PAGE = "composite";
  public static String VARANNOT_PAGE="variableAnnot";
  private final HandlerManager eventBus;
  private final QueryServiceAsync rpcService; 
  private HasWidgets container;  

  public AppController(QueryServiceAsync rpcService, HandlerManager eventBus) {
    this.eventBus = eventBus;
    this.rpcService = rpcService;
    bind();
  }
  
  private void bind() {
    History.addValueChangeHandler(this);
    
    eventBus.addHandler(QueryAppEvent.TYPE,
            new QueryAppEventHandler() {
				public void onQueryAppEvent(QueryAppEvent event) {
					doQueryAppEvent();					
				}
			});  

    eventBus.addHandler(ModelDetailsEvent.TYPE,
        new ModelDetailsEventHandler() {
          public void onEditContact(ModelDetailsEvent event) {
            doEditContact(event.getModelSearch());
          }
        });  

    eventBus.addHandler(ModelDetailsCancelledEvent.TYPE,
        new ModelDetailsCancelledEventHandler() {
          public void onEditContactCancelled(ModelDetailsCancelledEvent event) {
            doEditContactCancelled(event.getModelSearch());
          }
        });  
    
    eventBus.addHandler(CompAppEvent.TYPE, 
    		new CompAppEventHandler() {		
				public void onCompAppEvent(CompAppEvent event) {
					doCompAppEvent();
			
		}			
	});
    
    eventBus.addHandler(AnnotAppEvent.TYPE, 
    		new AnnotAppEventHandler() {
				public void onAnnotAppEvent(AnnotAppEvent event) {
					doAnnotAppEvent();
				}
	});
    
    eventBus.addHandler(AnnotVariableEvent.TYPE,
            new AnnotVariableEventHandler() {
              public void onVariableSelect(AnnotVariableEvent event) {
                doVariableSelect(event.getVariableSearch());
              }
    });
    
    eventBus.addHandler(BackToMainEvent.TYPE,
            new BackToMainEventHandler() {
              public void onBackToMain(BackToMainEvent event) {
                doBackToMain();
              }
            });  
    
    eventBus.addHandler(CancelAnnotEvent.TYPE, 
    		new CancelAnnotEventHandler() {
				public void onAnnotCancelled(CancelAnnotEvent event) {					
					doAnnotCancel(event.getVariableSearch());
				}
			});
    
    eventBus.addHandler(MSyntaxQuerySelectEvent.TYPE, 
    		new MSyntaxQuerySelectEventHandler() {
				public void onSelectMancesterQuery(MSyntaxQuerySelectEvent event) {
					doSelectManchesterQuery(event.getManQuery(), event.getParentPage(), event.getVariableSearch());					
				}
			});
    
    eventBus.addHandler(MSyntaxQueryCancelEvent.TYPE, 
    		new MSyntaxQueryCancelHandler() {
				public void onCancelMancesterQuery(MSyntaxQueryCancelEvent event) {
					doCancelManchesterQuery(event.getParentPage(),event.getVariableSearch());					
				}
			});  
    
    eventBus.addHandler(MSyntaxQueryConstructEvent.TYPE, 
    		new MSyntaxQueryConstructEventHandler() {
				public void onConstructManchesterQuery(MSyntaxQueryConstructEvent event) {
					doConstructManchesterQuery(event.getParentPage(),event.getVariableSearch());					
				}
			});   
  }

  private void doQueryAppEvent(){
	History.newItem(QUERY_PAGE,false);
	Presenter presenter = new QueryPresenter(rpcService, eventBus, new QueryView());
	presenter.go(container);
  }
  
   private void doEditContact(ModelSearch modelSearch) {
    History.newItem("edit", false);
    Presenter presenter = new ModelDetailsPresenter(rpcService, eventBus, new ModelDetailsView(), modelSearch);
    presenter.go(container);
  }
  
  private void doEditContactCancelled(ModelSearch modelSearch) {
    History.newItem(QUERY_PAGE);
	Presenter presenter = new QueryPresenter(rpcService, eventBus, new QueryView(),modelSearch);
	presenter.go(container);
  }
  
  private void doCompAppEvent(){
	  History.newItem(COMPOSITE_PAGE,false);
	  Presenter presenter = new CompositePresenter(rpcService,eventBus, new CompositeView());
	  presenter.go(container);
  }
  
  private void doAnnotAppEvent(){
	  History.newItem("annotation",false);
	  Presenter presenter = new AnnotationPresenter(rpcService,eventBus, new AnnotationView());
	  presenter.go(container);
  }  
  
  private void doVariableSelect(VariableSearch variableSearch) {
	    History.newItem(VARANNOT_PAGE, false);
	    Presenter presenter = new AnnotVariablePresenter(rpcService, eventBus, new AnnotVariableView(), variableSearch);
	    presenter.go(container);
	  }
  
  private void doBackToMain() {
	    History.newItem("ricordoapp");
	  }
  
  private void doAnnotCancel(VariableSearch variableSearch) {
	    History.newItem("annotation");
	    Presenter presenter = new AnnotationPresenter(rpcService,eventBus, new AnnotationView(), variableSearch);
	    presenter.go(container);
  }
  
  private void doSelectManchesterQuery(String manQuery, String parentPage, VariableSearch variableSearch) {
		Presenter presenter = null;
		if (parentPage.equals(QUERY_PAGE)) {
			History.newItem(QUERY_PAGE);
			presenter = new QueryPresenter(rpcService, eventBus, new QueryView(), manQuery);
		} else if (parentPage.equals(COMPOSITE_PAGE)) {
			History.newItem(COMPOSITE_PAGE);
			presenter = new CompositePresenter(rpcService, eventBus, new CompositeView(), manQuery);
		} else if (parentPage.equals(VARANNOT_PAGE)){
			History.newItem(VARANNOT_PAGE);
			presenter = new AnnotVariablePresenter(rpcService, eventBus, new AnnotVariableView(), manQuery, variableSearch);
		}
		presenter.go(container);
	}

  private void doCancelManchesterQuery(String parentPage, VariableSearch variableSearch) {
	  Presenter presenter = null;
	  if(parentPage.equals(QUERY_PAGE)){
		  History.newItem(QUERY_PAGE);
		  presenter = new QueryPresenter(rpcService,eventBus, new QueryView());
	  }
	  else if (parentPage.equals(COMPOSITE_PAGE)){
		  History.newItem(COMPOSITE_PAGE);
		  presenter = new CompositePresenter(rpcService, eventBus, new CompositeView());
	  }
	  else if (parentPage.equals(VARANNOT_PAGE)){
			History.newItem(VARANNOT_PAGE);
			presenter = new AnnotVariablePresenter(rpcService, eventBus, new AnnotVariableView(), variableSearch);
		}
	  presenter.go(container);	    
  }
  
  private void doConstructManchesterQuery(String parentPage, VariableSearch  variableSearch) {
	    History.newItem("manchesterQuery");
	    Presenter presenter= null;
	    if(parentPage.equals(VARANNOT_PAGE)){
	    	presenter= new ManchesterSyntaxPresenter(rpcService, eventBus, new ManchesterSyntaxView(TitlePanel.QUERY_TITLE), parentPage,variableSearch);
	    }else{	    
	    	presenter= new ManchesterSyntaxPresenter(rpcService,eventBus, new ManchesterSyntaxView(TitlePanel.QUERY_TITLE),parentPage);
	    }
	    presenter.go(container);	
		
	}

  public void go(final HasWidgets container) {
    this.container = container;
    
    if ("".equals(History.getToken())) {
        History.newItem("ricordoapp");
      }
    else {
      History.fireCurrentHistoryState();
    }
  }

  public void onValueChange(ValueChangeEvent<String> event) {
    String token = event.getValue();
    
    if (token != null) {
      Presenter presenter = null;

      if (token.equals("ricordoapp")) {
          presenter = new RicordoMainPresenter(rpcService, eventBus, new RicordoMainView());
      }
      
      else if (token.equals(QUERY_PAGE)) {
        presenter = new QueryPresenter(rpcService, eventBus, new QueryView());
      }
      
      if (presenter != null) {
        presenter.go(container);
      }
    }
  } 
}
