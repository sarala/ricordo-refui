package uk.ac.ebi.ricordo.refui.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CompositeDetails implements Serializable{
	
	private String composite = "";
	private String status = "";
	private String id = "";
	
	public CompositeDetails() {		
	}
	
	public CompositeDetails(String id, String composite, String status){
		this.composite = composite;
		this.status = status;
		this.id = id;
	}
	
	public CompositeDetailsLight getLightWeightContact() {
		return new CompositeDetailsLight(getDisplayName());
	}	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComposite() {
		return composite;
	}

	public void setComposite(String composite) {
		this.composite = composite;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String [] getDisplayName() {
		return new String []{id, composite, status};
	}
	

}
