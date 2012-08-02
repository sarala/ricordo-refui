package uk.ac.ebi.ricordo.refui.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VariableDetails implements Serializable{
	private String modelName;
	private String variableName;
	private String qualifier;
	private String miriamURN;
	private String id;
	
	public VariableDetails() {		
	}
	
	public VariableDetails(String id, String variableName){
		this.id=id;
		this.variableName = variableName;	
	}

	public VariableDetails(String id, String modelName, String variableName, String qualifier, String miriamURN) {
		this.id=id;
		this.modelName = modelName;
		this.variableName = variableName;
		this.qualifier = qualifier;
		this.miriamURN = miriamURN;
	}
	
	public VariableDetailsLight getLightWeightVariableName() {
		return new VariableDetailsLight(id, variableName, new String[]{id,variableName});
	}

	public VariableDetailsLight getLightWeightContact() {
		return new VariableDetailsLight(getDisplayName());
	}

	public String [] getDisplayName() {
		return new String [] {id,variableName,qualifier, miriamURN};
	}
	
	public VariableDetailsLight getLightWeightAnnotation(){
		return new VariableDetailsLight(id, new String[]{id,qualifier,miriamURN});
	}

	public String getVariableName() {
		return variableName;
	}
	
	
	
}
