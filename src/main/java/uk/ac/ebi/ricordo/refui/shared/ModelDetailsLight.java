package uk.ac.ebi.ricordo.refui.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ModelDetailsLight implements Serializable {
	private String id;
	private String [] displayName;
	private String modelURL;

	public ModelDetailsLight() {
		new ModelDetailsLight("0", new String []{});
	}

	public ModelDetailsLight(String id, String [] displayName) {
		this.id = id;
		this.displayName = displayName;
	}
	
	public ModelDetailsLight(String id, String modelURL, String [] displayName) {
		this.id = id;
		this.displayName = displayName;
		this.modelURL = modelURL;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String [] getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String [] displayName) {
		this.displayName = displayName;
	}

	public String getModelURL() {
		return modelURL;
	}
	
	
}
