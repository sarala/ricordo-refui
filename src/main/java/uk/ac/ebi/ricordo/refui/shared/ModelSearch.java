package uk.ac.ebi.ricordo.refui.shared;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ModelSearch implements Serializable {
	private String manQueryText = "";
	private String modelURL="";
	private ArrayList<OntologyTermData> termList;
	
	public ModelSearch(){	}

	public String getManQueryText() {
		return manQueryText;
	}

	public void setManQueryText(String manQueryText) {
		this.manQueryText = manQueryText;
	}

	public String getModelURL() {
		return modelURL;
	}

	public void setModelURL(String modelURL) {
		this.modelURL = modelURL;
	}

	public ArrayList<OntologyTermData> getTermList() {
		return termList;
	}

	public void setTermList(ArrayList<OntologyTermData> termList) {
		this.termList = termList;
	}
	
	
	
}
