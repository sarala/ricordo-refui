package uk.ac.ebi.ricordo.refui.shared;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ModelDetails implements Serializable {
	public String id;
	public String modelName;
	public int frequency = 0;
	public ArrayList<OntologyTermData> termList = new ArrayList<OntologyTermData>();

	public ModelDetails() {
	}
	
	public ModelDetails(String id, String modelName){
		this.id = id;
		this.modelName = modelName;
	}

	public ModelDetails(String id, String modelName, int frequency) {
		this.id = id;
		this.modelName = modelName;
		this.frequency = frequency;
	}

	public ModelDetailsLight getLightWeightContact() {
		return new ModelDetailsLight(id, modelName, getDisplayName());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getModelName() {
		return modelName;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public ArrayList<OntologyTermData> getTermList() {
		return termList;
	}

	public void addTermtoList(OntologyTermData term) {
		termList.add(term);
	}

	public String [] getDisplayName() {
		return new String []{id, modelName, String.valueOf(frequency)};
	}
}
