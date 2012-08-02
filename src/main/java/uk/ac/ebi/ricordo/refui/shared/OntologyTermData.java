package uk.ac.ebi.ricordo.refui.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OntologyTermData implements Serializable{
	
	private String termAccession = "";
	private String ontologyAccession = "";
	private String miriamURN = "";
	
	public OntologyTermData(){
		
	}
	
	public OntologyTermData(String ontologyAccession, String termAccession ) {
		this.termAccession = termAccession;
		this.ontologyAccession = ontologyAccession;
	}

	public String getTermAccession() {
		return termAccession;
	}

	public String getOntologyAccession() {
		return ontologyAccession;
	}

	public String getMiriamURN() {
		return miriamURN;
	}

	public void setMiriamURN(String miriamURN) {
		this.miriamURN = miriamURN;
	}
	
	

}
