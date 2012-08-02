package uk.ac.ebi.ricordo.refui.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VariableDetailsLight implements Serializable{
	private String [] displayContent;
	private String id;
	private String variableURL;

	public VariableDetailsLight() {
		new VariableDetailsLight(new String[]{});
	}
	
	public VariableDetailsLight(String id, String [] displayContent) {
		this.id = id;
		this.displayContent = displayContent;
	}
	
	public VariableDetailsLight(String id, String variableURL, String [] displayContent) {
		this.id = id;
		this.variableURL = variableURL;
		this.displayContent = displayContent;		
	}

	public VariableDetailsLight(String [] displayContent) {
		this.displayContent = displayContent;
	}

	public String [] getDisplayContent() {
		return displayContent;
	}

	public void setDisplayContent(String []displayContent) {
		this.displayContent = displayContent;
	}

	public String getId() {
		return id;
	}

	public String getVariableURL() {
		return variableURL;
	}

}
