package uk.ac.ebi.ricordo.refui.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CompositeDetailsLight implements Serializable{
	private String [] displayContent;

	public CompositeDetailsLight() {
		new CompositeDetailsLight(new String[]{});
	}

	public CompositeDetailsLight(String [] displayContent) {
		this.displayContent = displayContent;
	}

	public String [] getDisplayContent() {
		return displayContent;
	}

	public void setDisplayContent(String []displayContent) {
		this.displayContent = displayContent;
	}

}
