package uk.ac.ebi.ricordo.refui.server;

import uk.ac.ebi.miriam.lib.MiriamLink;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.virtual.CachedServiceDecorator;
import uk.ac.ebi.ricordo.owlkb.service.OwlKbService;
import uk.ac.ebi.ricordo.owlkb.service.QueryTemplateService;
import uk.ac.ebi.ricordo.owlkb.service.RelationsService;
import uk.ac.ebi.ricordo.rdfstore.service.QualifierService;
import uk.ac.ebi.ricordo.rdfstore.service.RdfStoreService;

/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 13/07/12
 * Time: 10:20
 */
public class ServicesHandler {

    private QueryTemplateService queryTemplateService;
    private RelationsService relationsService;
    private OntologyService ontologyService;
    private MiriamLink miriamLinkService;
    private RdfStoreService rdfStoreService;
    private OwlKbService owlKbService;
    private QualifierService bioQualifierService;

    public ServicesHandler() {
        setUpOntologyService();
        setUpMiriamService();
    }

    private void setUpOntologyService(){
        try {
            ontologyService = CachedServiceDecorator.getService(new OlsOntologyService());
        } catch (OntologyServiceException e) {
            e.printStackTrace();
        }
    }

    private void setUpMiriamService() {
        miriamLinkService = new MiriamLink();

        // Sets the address to access the Web Services
        //miriamLinkService.setAddress("http://www.ebi.ac.uk/miriamws/main/MiriamWebServices");
    }

    public QueryTemplateService getQueryTemplateService() {
        return queryTemplateService;
    }

    public void setQueryTemplateService(QueryTemplateService queryTemplateService) {
        this.queryTemplateService = queryTemplateService;
    }

    public RelationsService getRelationsService() {
        return relationsService;
    }

    public void setRelationsService(RelationsService relationsService) {
        this.relationsService = relationsService;
    }

    public OntologyService getOntologyService() {
        return ontologyService;
    }

    public MiriamLink getMiriamLinkService() {
        return miriamLinkService;
    }

    public RdfStoreService getRdfStoreService() {
        return rdfStoreService;
    }

    public void setRdfStoreService(RdfStoreService rdfStoreService) {
        this.rdfStoreService = rdfStoreService;
    }

    public OwlKbService getOwlKbService() {
        return owlKbService;
    }

    public void setOwlKbService(OwlKbService owlKbService) {
        this.owlKbService = owlKbService;
    }

    public QualifierService getBioQualifierService() {
        return bioQualifierService;
    }

    public void setBioQualifierService(QualifierService bioQualifierService) {
        this.bioQualifierService = bioQualifierService;
    }
}
