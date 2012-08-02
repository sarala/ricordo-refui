package uk.ac.ebi.ricordo.refui.client.exception;

@SuppressWarnings("serial")
public class ManchesterQueryException extends RicordoException {
	public ManchesterQueryException(){
		
	}
	
	public ManchesterQueryException(String displayMessage, String errorMessage){
		super(displayMessage,errorMessage);
	}
}
