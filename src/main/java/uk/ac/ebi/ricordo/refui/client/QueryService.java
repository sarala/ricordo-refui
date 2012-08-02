package uk.ac.ebi.ricordo.refui.client;

import java.util.ArrayList;
import java.util.LinkedList;

import uk.ac.ebi.ricordo.refui.client.exception.AnnotVariableException;
import uk.ac.ebi.ricordo.refui.client.exception.ManchesterQueryException;
import uk.ac.ebi.ricordo.refui.client.exception.RemoteOntologyServiceException;
import uk.ac.ebi.ricordo.refui.shared.CompositeDetailsLight;
import uk.ac.ebi.ricordo.refui.shared.ModelDetailsLight;
import uk.ac.ebi.ricordo.refui.shared.ModelSearch;
import uk.ac.ebi.ricordo.refui.shared.VariableDetailsLight;
import uk.ac.ebi.ricordo.refui.shared.VariableSearch;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("queryService")
public interface QueryService extends RemoteService {	
  ArrayList<VariableDetailsLight> getVariableList(ModelSearch modelSearch);
  ArrayList<String> getManQueryTypes();
  LinkedList<String> getOntoTerms(String ontoTerm) throws RemoteOntologyServiceException;
  ModelSearch setOntoTerms(ModelSearch modelSearch) throws ManchesterQueryException;
  ArrayList<ModelDetailsLight> searchModelData(ModelSearch modelSearch);
  ArrayList<CompositeDetailsLight> getCompoistes(ModelSearch modelSearch) throws ManchesterQueryException;
  ArrayList<VariableDetailsLight> getVariableListForAnnotation(VariableSearch variableSearch);
  ArrayList<VariableDetailsLight> getVariableAnnotationList(VariableSearch variableSearch); 
  ArrayList<String> getMiriamQualifiers();
  void addAnnotation(String variableURL, String property, String annotValue)throws AnnotVariableException;
  String getTermToAnnotate(String manQuery)throws ManchesterQueryException;
  ArrayList<String> getRelations();
  
}
