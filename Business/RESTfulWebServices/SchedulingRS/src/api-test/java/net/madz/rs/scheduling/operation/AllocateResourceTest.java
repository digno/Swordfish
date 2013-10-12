package net.madz.rs.scheduling.operation;

import javax.xml.bind.JAXBException;

import net.madz.scheduling.to.ServiceOrderTO;
import net.madz.utils.MOXyUtils;

import org.junit.Rule;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.Header;
import com.eclipsesource.restfuse.annotation.HttpTest;

@RunWith(HttpJUnitRunner.class)
public class AllocateResourceTest {

    @Rule
    public Destination destination = new Destination(this, "http://localhost:8080");

    @Context
    private Response response;

    @HttpTest(method = Method.POST, path = "/scheduling-api/operation/summaryPlan/1/serviceOrder",
            file = "positive.allocate.resource.json", headers = {
                    @Header(name = "Content-Type", value = "application/json"),
                    @Header(name = "Accept", value = "application/json") })
    public void testMethod() throws JAXBException {
        System.out.println(response.getBody());
        com.eclipsesource.restfuse.Assert.assertOk(response);
        ServiceOrderTO value = MOXyUtils.unmarshal(response, ServiceOrderTO.class, new Class[] { ServiceOrderTO.class });
        System.out.println(value);
    }
}