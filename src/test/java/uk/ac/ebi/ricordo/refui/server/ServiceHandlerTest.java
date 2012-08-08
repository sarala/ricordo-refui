package uk.ac.ebi.ricordo.refui.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.ricordo.rdfstore.bean.ResourceList;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 23/07/12
 * Time: 11:43
 */
@RunWith(SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations={"classpath:ricordo-refui-config.xml"})
public class ServiceHandlerTest {

    @Autowired
    ServicesHandler servicesHandler;

    @Test
    public void testResources() {
        ResourceList resourceList = new ResourceList();
        servicesHandler.getRdfStoreService().search("http://identifiers.org/obo.go/GO:0005892","getResourceForAnnotationOfElement",resourceList);
        assertEquals(24,resourceList.getCount());
    }

    @Test
    public void testQueryTemplateService(){
        assertEquals(6,servicesHandler.getQueryTemplateService().getQueryTemplateList().size());
    }

    @Test
    public void testOwlKbService(){
        assertEquals(2, servicesHandler.getOwlKbService().getTerms("GO_0005892").size());
    }

    @Test
    public void testMiriamService(){
         assertEquals("urn:miriam:obo.go:GO%3A0005892",servicesHandler.getMiriamLinkService().getURI("GO","GO:0005892"));
    }

}
