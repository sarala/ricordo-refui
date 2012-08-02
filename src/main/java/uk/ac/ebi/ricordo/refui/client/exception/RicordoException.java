package uk.ac.ebi.ricordo.refui.client.exception;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RicordoException extends Exception implements Serializable {
	private String displayMessage = "";
	private String errorMessage="";
	
	public RicordoException(){}
	
	public RicordoException(String displayMessage){
		this.displayMessage=displayMessage;
	}
	
	public RicordoException(String displayMessage, String errorMessage){
		this.displayMessage = displayMessage;
		this.errorMessage = errorMessage;
	}

	public String getDisplayMessage() {
		return displayMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
