package uk.ac.ebi.ricordo.refui.client.view;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;

public class TermSuggestBox extends SuggestBox{
	
	public TermSuggestBox(MultiWordSuggestOracle oracle){
		super(oracle);
	}

}
