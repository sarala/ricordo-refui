package uk.ac.ebi.ricordo.refui.client.exception;


@SuppressWarnings("serial")
public class AnnotVariableException extends RicordoException  {
	public AnnotVariableException(){		
	}
	
	public AnnotVariableException(String displayMessage){
		super(displayMessage);
	}
	
	public AnnotVariableException(String displayMessage, String errorMessage){
		super(displayMessage,errorMessage);
	}
}
