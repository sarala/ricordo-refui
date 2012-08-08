package uk.ac.ebi.ricordo.refui.server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.ricordo.refui.client.QueryService;
import uk.ac.ebi.ricordo.refui.client.exception.ManchesterQueryException;
import uk.ac.ebi.ricordo.refui.shared.ModelDetailsLight;
import uk.ac.ebi.ricordo.refui.shared.ModelSearch;
import uk.ac.ebi.ricordo.refui.shared.VariableDetailsLight;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 20/07/12
 * Time: 11:15
 */
@RunWith(SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations={"classpath:ricordo-refui-test-config.xml"})

public class QueryServiceImplTest {

    @Autowired
    private QueryService queryService;
    ModelSearch modelSearch = new ModelSearch();

    @Before
    public void setUp() throws Exception {
        modelSearch.setManQueryText("GO_0005892");
        try {
            queryService.setOntoTerms(modelSearch);
        } catch (ManchesterQueryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSetOntoTerms(){
        assertEquals(1,modelSearch.getTermList().size() );
        // assertEquals("",servicesHandler.getMiriamLinkService().getURI("GO","GO:0005892"));
    }

    @Test
    public void testSearchModelData(){
        modelSearch.getTermList().get(0).setMiriamURN("http://identifiers.org/obo.go/GO:0005892");
        ArrayList<ModelDetailsLight> modelDetailsLights = queryService.searchModelData(modelSearch);
        assertEquals(2,modelDetailsLights.size());

        ModelDetailsLight modelDetailsLight = modelDetailsLights.get(0);
        assertEquals("http://www.ebi.ac.uk/ricordo/toolbox/sbmlo#BIOMD0000000001", modelDetailsLight.getModelURL());
        modelDetailsLight = modelDetailsLights.get(1);
        assertEquals( "http://www.ebi.ac.uk/ricordo/toolbox/sbmlo#BIOMD0000000002", modelDetailsLight.getModelURL());
    }

    @Test
    public void testgetVariableList(){
        modelSearch.setModelURL("http://www.ebi.ac.uk/ricordo/toolbox/sbmlo#BIOMD0000000001");
        ArrayList<VariableDetailsLight> variableDetailsLights = queryService.getVariableList(modelSearch);
        assertEquals(39,variableDetailsLights.size());

    }


    @Test
    public void testModelSearch(){
        modelSearch.setManQueryText("GO_0005892");
        assertEquals("GO_0005892", modelSearch.getManQueryText());
    }
}
