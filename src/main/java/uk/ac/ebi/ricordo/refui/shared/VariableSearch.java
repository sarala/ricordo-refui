package uk.ac.ebi.ricordo.refui.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VariableSearch implements Serializable{
	private String modelUrl = "";
	private String variableUrl = "";
	
	public VariableSearch(){		
	}

	public String getModelUrl() {
		return modelUrl;
	}

	public void setModelUrl(String modelUrl) {
		this.modelUrl = modelUrl;
	}

	public String getVariableUrl() {
		return variableUrl;
	}

	public void setVariableUrl(String variableUrl) {
		this.variableUrl = variableUrl;
	}	

}
