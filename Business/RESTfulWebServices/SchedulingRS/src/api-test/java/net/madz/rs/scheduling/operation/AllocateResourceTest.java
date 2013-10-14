package net.madz.rs.scheduling.operation;

import javax.xml.bind.JAXBException;

import net.madz.rs.scheduling.providers.ErrorTO;
import net.madz.scheduling.to.ServiceOrderTO;
import net.madz.test.MadzDestination;
import net.madz.test.MadzHttpUnitRunner;
import net.madz.test.annotations.FreeTrialTenant;
import net.madz.test.annotations.VariableInjector;
import net.madz.test.annotations.processors.FreeTrialCredentialInjector;
import net.madz.utils.MOXyUtils;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.Header;
import com.eclipsesource.restfuse.annotation.HttpTest;

@RunWith(MadzHttpUnitRunner.class)
@FreeTrialTenant
public class AllocateResourceTest {

    @Rule
    public MadzDestination destination = new MadzDestination(this, "http://localhost:8080");

    @Context
    private Response response;

    @HttpTest(method = Method.POST, path = "/api/scheduling/operation/summaryPlan/1/serviceOrder",
            file = "positive.allocate.resource.json", headers = {
                    @Header(name = "Content-Type", value = "application/json"),
                    @Header(name = "Accept", value = "application/json") })
    public void testMethod() throws JAXBException {
        System.out.println(response.getBody());
        com.eclipsesource.restfuse.Assert.assertOk(response);
        ServiceOrderTO value = MOXyUtils
                .unmarshal(response, ServiceOrderTO.class, new Class[] { ServiceOrderTO.class });
        System.out.println(value);
    }

    @HttpTest(method = Method.POST, path = "/api/scheduling/operation/summaryPlan/2/serviceOrder",
            file = "positive.allocate.resource.json", headers = {
                    @Header(name = "Content-Type", value = "application/json"),
                    @Header(name = "Accept", value = "application/json") })
    @VariableInjector(FreeTrialCredentialInjector.class)
    public void test_invalid_summaryPlanId() throws JAXBException {
        System.out.println(response.getBody());
        com.eclipsesource.restfuse.Assert.assertNotFound(response);
        ErrorTO value = MOXyUtils.unmarshal(response, ErrorTO.class, new Class[] { ErrorTO.class });
        Assert.assertEquals("100-0001", value.getErrorCode());
        System.out.println(value);
    }

    @HttpTest(method = Method.POST, path = "/api/scheduling/operation/summaryPlan/1/serviceOrder",
            file = "allocate.resource.invalid.plantResourceId.json", headers = {
                    @Header(name = "Content-Type", value = "application/json"),
                    @Header(name = "Accept", value = "application/json") })
    public void test_invalid_plantResourceId() throws JAXBException {
        System.out.println(response.getBody());
        com.eclipsesource.restfuse.Assert.assertNotFound(response);
        ErrorTO value = MOXyUtils.unmarshal(response, ErrorTO.class, new Class[] { ErrorTO.class });
        Assert.assertEquals("100-0006", value.getErrorCode());
        System.out.println(value);
    }

    @HttpTest(method = Method.POST, path = "/api/scheduling/operation/summaryPlan/1/serviceOrder",
            file = "allocate.resource.invalid.concreteTruckResourceId.json", headers = {
                    @Header(name = "Content-Type", value = "application/json"),
                    @Header(name = "Accept", value = "application/json") })
    public void test_invalid_concreteTruckResourceId() throws JAXBException {
        System.out.println(response.getBody());
        com.eclipsesource.restfuse.Assert.assertNotFound(response);
        ErrorTO value = MOXyUtils.unmarshal(response, ErrorTO.class, new Class[] { ErrorTO.class });
        Assert.assertEquals("100-0004", value.getErrorCode());
        System.out.println(value);
    }
}
