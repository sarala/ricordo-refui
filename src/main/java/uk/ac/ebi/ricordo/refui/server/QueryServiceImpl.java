package uk.ac.ebi.ricordo.refui.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import uk.ac.ebi.ontocat.OntologyService.SearchOptions;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ricordo.owlkb.bean.Query;
import uk.ac.ebi.ricordo.owlkb.bean.Term;
import uk.ac.ebi.ricordo.rdfstore.bean.Qualifier;
import uk.ac.ebi.ricordo.rdfstore.bean.Resource;
import uk.ac.ebi.ricordo.rdfstore.bean.ResourceList;
import uk.ac.ebi.ricordo.refui.client.QueryService;
import uk.ac.ebi.ricordo.refui.client.exception.AnnotVariableException;
import uk.ac.ebi.ricordo.refui.client.exception.ManchesterQueryException;
import uk.ac.ebi.ricordo.refui.client.exception.RemoteOntologyServiceException;
import uk.ac.ebi.ricordo.refui.shared.*;

import java.util.*;

@SuppressWarnings("serial")
public class QueryServiceImpl extends RemoteServiceServlet implements
        QueryService {

    public static final String[] ONTOLIST = new String[] { "FMA", "PATO", "GO", "CHEBI", "HP" };

    private ServicesHandler servicesHandler;


    public QueryServiceImpl() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:ricordo-refui-config.xml");
        servicesHandler = (ServicesHandler)ctx.getBean("servicesHandler");
//        servicesHandler.getOwlKbService().startService();
    }

    public ArrayList<VariableDetailsLight> getVariableList(ModelSearch modelSearch) {
        ArrayList<VariableDetailsLight> variableDetailsLight = new ArrayList<VariableDetailsLight>();
        int i = 1;
        ResourceList resourceList = new ResourceList();
        servicesHandler.getRdfStoreService().search(modelSearch.getModelURL(),"getAnnotationOfElementOfResource",resourceList);

        for(Resource resource: resourceList.getResources()) {
            String variableName = resource.getValue().get(0);
            String predicate = resource.getValue().get(1);
            String annotation = resource.getValue().get(2);


            VariableDetails variableDetails = new VariableDetails(String.valueOf(i++),modelSearch.getModelURL(),variableName, predicate,annotation);

            variableDetailsLight.add(variableDetails.getLightWeightContact());

        }

        return variableDetailsLight;
    }

    public ArrayList<VariableDetailsLight> getVariableListForAnnotation(VariableSearch variableSearch){

        ArrayList<VariableDetailsLight> variableDetailsLight = new ArrayList<VariableDetailsLight>();

        ResourceList resourceList = new ResourceList();
        servicesHandler.getRdfStoreService().search(variableSearch.getModelUrl(),"getElementOfResource",resourceList);

        int i=0;
        for(Resource resource: resourceList.getResources()) {
            String variableName = resource.getValue().get(0);
            String id = String.valueOf(i++);
            VariableDetails variableDetails = new VariableDetails(id,variableName);

            variableDetailsLight.add(variableDetails.getLightWeightVariableName());
        }

        return variableDetailsLight;
    }

    public ArrayList<String> getManQueryTypes() {
        ArrayList<String> manqueryList = new ArrayList<String>();
        for(Query query : servicesHandler.getQueryTemplateService().getQueryTemplateList()){
            manqueryList.add(query.getQuery());

        }

        return manqueryList;
    }

    public ArrayList<String> getRelations() {
        ArrayList<String> relationsList = new ArrayList<String>();
        for(Query query : servicesHandler.getRelationsService().getRelationsList()){
            relationsList.add(query.getQuery());

        }

        return relationsList;
    }

    @Override
    public LinkedList<String> getOntoTerms(String ontoTerm) throws RemoteOntologyServiceException {
        LinkedList<String> suggestions = new LinkedList<String>();
        for(int i=0;i<ONTOLIST.length;i++){
            suggestions = addSuggestions(ONTOLIST[i],suggestions, ontoTerm);
        }
        if(suggestions.size()>20){
            return new LinkedList<String>(suggestions.subList(0, 20));
        }
        else
            return suggestions;
    }

    private LinkedList<String> addSuggestions(String ontoAssersion, LinkedList<String> suggestions, String ontoTerm) throws RemoteOntologyServiceException{
        List<OntologyTerm> termList;
        try {
            termList = servicesHandler.getOntologyService().searchOntology(ontoAssersion, ontoTerm, SearchOptions.INCLUDE_PROPERTIES);
            for (OntologyTerm ot : termList) {
                String term = ot.getLabel() + " (" + ot.getAccession() + ")";
                if (ot.getLabel().toLowerCase().equals(ontoTerm.toLowerCase())) {
                    suggestions.addFirst(term);
                } else if (ot.getLabel().toLowerCase().startsWith(ontoTerm.toLowerCase()))
                    suggestions.add(term);
            }
        } catch (OntologyServiceException e) {
            throw new RemoteOntologyServiceException("Error in connecting to OLS",e.getMessage());
        }

        return suggestions;
    }

    public ModelSearch setOntoTerms(ModelSearch modelSearch) throws ManchesterQueryException{
        ArrayList<Term> termList = servicesHandler.getOwlKbService().getTerms(modelSearch.getManQueryText());
        ArrayList<OntologyTermData> ontologyTermDataList = new ArrayList<OntologyTermData>();

        for (Term term : termList) {
            OntologyTermData ontologyTermData = null;
            String termString = term.getId().substring(term.getId().indexOf("#")+1);
            String [] termData = termString.split("_");
            if(termData.length==2){
                ontologyTermData = new OntologyTermData(termData[0], termData[0]+":"+termData[1]);
                ontologyTermDataList.add(ontologyTermData);
                String miriamURL= servicesHandler.getMiriamLinkService().getURI(ontologyTermData.getOntologyAccession(), ontologyTermData.getTermAccession());
                System.out.println(miriamURL);
                miriamURL = servicesHandler.getMiriamLinkService().convertURN(miriamURL);
                System.out.println(miriamURL);
                if(miriamURL == null) {
                    ontologyTermData.setMiriamURN(term.getId());
                }
                else{
                    ontologyTermData.setMiriamURN(miriamURL);
                }
            }
        }
        modelSearch.setTermList(ontologyTermDataList);

        return modelSearch;
    }

    @Override
    public ArrayList<ModelDetailsLight> searchModelData(ModelSearch modelSearch) {

        LinkedHashMap<String, ModelDetails> modelDetailsList = new LinkedHashMap<String, ModelDetails>();
        int i = 1;
        for (OntologyTermData term : modelSearch.getTermList()) {
            ResourceList resourceList = new ResourceList();
            servicesHandler.getRdfStoreService().search(term.getMiriamURN(),"getResourceForAnnotationOfElement",resourceList);

            for (Resource resource : resourceList.getResources()) {
                String modelName = resource.getValue().get(0);

                if(modelName!=""){
                    ModelDetails modelDetails = modelDetailsList.get(modelName);
                    if(modelDetails == null){
                        modelDetails = new ModelDetails(String.valueOf(i++), modelName);
                        modelDetails.setFrequency(modelDetails.getFrequency()+ 1);
                        modelDetailsList.put(modelName, modelDetails);
                    }
                }

            }
        }

        return getContactDetails(modelDetailsList);
    }

    private ArrayList<ModelDetailsLight> getContactDetails(LinkedHashMap<String, ModelDetails> modelDetailsList) {
        ArrayList<ModelDetailsLight> modelDetailsLight = new ArrayList<ModelDetailsLight>();

        Iterator<String> it = modelDetailsList.keySet().iterator();
        while (it.hasNext()) {
            ModelDetailsLight modelDetails = modelDetailsList.get(it.next()).getLightWeightContact();
            modelDetailsLight.add(modelDetails);
        }

        return modelDetailsLight;
    }

    @Override
    public ArrayList<CompositeDetailsLight> getCompoistes(ModelSearch modelSearch) throws ManchesterQueryException {
        ArrayList<CompositeDetailsLight> compositeList = new ArrayList<CompositeDetailsLight>();

        ArrayList<Term> termList = servicesHandler.getOwlKbService().getEquivalentTerms(modelSearch.getManQueryText());

        int i = 1;
        for(Term term: termList){
            compositeList.add(new CompositeDetails(""+(i++), term.getId(), "Existing composites").getLightWeightContact());
        }

        if (termList.isEmpty()){
            termList = servicesHandler.getOwlKbService().addTerm(modelSearch.getManQueryText());
            compositeList.add(new CompositeDetails(""+(i++), termList.get(0).getId(), "Newly added").getLightWeightContact());
        }
        return compositeList;
    }

    public String getTermToAnnotate(String manQuery)throws ManchesterQueryException {
        ArrayList<Term> termList = servicesHandler.getOwlKbService().getEquivalentTerms(manQuery);
        if (termList.isEmpty()){
            termList = servicesHandler.getOwlKbService().addTerm(manQuery);
        }
        String termId = termList.get(0).getId();
        return termId.substring(termId.indexOf("#")+1);
    }


    public ArrayList<VariableDetailsLight> getVariableAnnotationList(VariableSearch variableSearch){
        ArrayList<VariableDetailsLight> variableDetailsLight = new ArrayList<VariableDetailsLight>();

        ResourceList resourceList = new ResourceList();
        servicesHandler.getRdfStoreService().search(variableSearch.getVariableUrl(),"getAnnotationOfResource",resourceList);

        int i =0;
        for (Resource resource : resourceList.getResources()) {
            String predicate =resource.getValue().get(0);
            String annotation = resource.getValue().get(1);

            VariableDetails variableDetails = new VariableDetails(String.valueOf(i++),variableSearch.getModelUrl(),variableSearch.getVariableUrl(),predicate,annotation);

            variableDetailsLight.add(variableDetails.getLightWeightAnnotation());
        }

        return variableDetailsLight;
    }

    public ArrayList<String> getMiriamQualifiers() {
        ArrayList<String> suggestions = new ArrayList<String>();
        for (Qualifier qualifier : servicesHandler.getBioQualifierService().getQualifierList()){
            suggestions.add(qualifier.getQualifier());
        }
        return suggestions;
    }

    public void addAnnotation(String variableURL, String property, String annotation) throws AnnotVariableException {
        String annotValue = annotation.replace("_", ":");
        String [] annotValArray = annotValue.split(":");
        if(annotValArray.length!=2){
            throw new AnnotVariableException("Invalid term ID");
        }

        String miriumURN=servicesHandler.getMiriamLinkService().getURI(annotValArray[0], annotValue);

        if(miriumURN!=null){
            miriumURN = servicesHandler.getMiriamLinkService().convertURN(miriumURN);
        }
        else{
            miriumURN = servicesHandler.getOwlKbService().getEquivalentTerms(annotation).get(0).getId();
        }

        String query = variableURL+",http://biomodels.net/biology-qualifiers/"+property+","+miriumURN;
        servicesHandler.getRdfStoreService().insert("insertStatement",query);
    }

}
