package net.madz.lifecycle.engine;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import net.madz.lifecycle.LifecycleCommonErrors;
import net.madz.lifecycle.LifecycleException;
import net.madz.lifecycle.engine.CoreFuntionTestMetadata.InternetServiceLifecycleMeta.Relations.CustomerRelation;
import net.madz.lifecycle.engine.CoreFuntionTestMetadata.KeyBoardLifecycleMetadataPreValidateCondition.Relations.PowerRelation;

import org.junit.Test;

public class EngineCoreFunctionNegativeTests extends CoreFuntionTestMetadata {

    @Test(expected = LifecycleException.class)
    public void test_standalone_object_with_definite_relation_negative() throws LifecycleException {
        Customer customer = new Customer();
        customer.activate();
        customer.cancel();
        assertEquals(CustomerLifecycleMeta.States.Canceled.class.getSimpleName(), customer.getState());
        InternetServiceOrder order = new InternetServiceOrder(new Date(), null, customer, "1 year");
        try {
            order.start();
        } catch (LifecycleException e) {
            assertInvalidStateErrorByValidWhile(e, customer, order, CustomerLifecycleMeta.States.Active.class);
            throw e;
        }
    }

    private String inboundWhileDottedPath(Class<?> stateClass, Class<?> relationKeyClss) {
        return stateClass.getDeclaringClass().getDeclaringClass().getName() + ".StateSet." + stateClass.getSimpleName()
                + ".InboundWhiles." + relationKeyClss.getSimpleName();
    }

    @Test(expected = LifecycleException.class)
    public void test_inherited_valid_while_relation_validation_negative_with_super_valid_while()
            throws LifecycleException {
        final InternetTVServiceProvider provider = new InternetTVServiceProvider();
        assertEquals(InternetTVProviderLifecycle.States.ServiceAvailable.class.getSimpleName(), provider.getState());
        final Customer customer = new Customer();
        customer.activate();
        assertEquals(CustomerLifecycleMeta.States.Active.class.getSimpleName(), customer.getState());
        customer.cancel();
        assertEquals(CustomerLifecycleMeta.States.Canceled.class.getSimpleName(), customer.getState());
        final InternetTVService service = new InternetTVService(customer);
        service.setProvider(provider);
        try {
            service.start();
        } catch (LifecycleException e) {
            assertInvalidStateErrorByValidWhile(e, customer, service, CustomerLifecycleMeta.States.Active.class);
            throw e;
        }
    }

    @Test(expected = LifecycleException.class)
    public void test_inherited_valid_while_relation_validation_negative_with_self_valid_while()
            throws LifecycleException {
        final InternetTVServiceProvider provider = new InternetTVServiceProvider();
        assertEquals(InternetTVProviderLifecycle.States.ServiceAvailable.class.getSimpleName(), provider.getState());
        provider.shutdown();
        assertEquals(InternetTVProviderLifecycle.States.Closed.class.getSimpleName(), provider.getState());
        final Customer customer = new Customer();
        customer.activate();
        assertEquals(CustomerLifecycleMeta.States.Active.class.getSimpleName(), customer.getState());
        final InternetTVService service = new InternetTVService(customer);
        service.setProvider(provider);
        try {
            service.start();
        } catch (LifecycleException e) {
            assertInvalidStateErrorByValidWhile(e, provider, service,
                    VOIPProviderLifecycleMeta.States.ServiceAvailable.class);
            throw e;
        }
    }

    @Test(expected = LifecycleException.class)
    public void test_overrides_inherited_valid_while_relation_validation_negative_with_self_valid_while()
            throws LifecycleException {
        final VOIPProvider provider = new VOIPProvider();
        final Customer customer = new Customer();
        assertEquals(CustomerLifecycleMeta.States.Draft.class.getSimpleName(), customer.getState());
        final VOIPService service = new VOIPService(customer);
        assertEquals(VOIPServiceLifecycleMeta.States.New.class.getSimpleName(), service.getState());
        provider.shutdown();
        assertEquals(VOIPProviderLifecycleMeta.States.Closed.class.getSimpleName(), provider.getState());
        service.setProvider(provider);
        try {
            service.start();
        } catch (LifecycleException e) {
            assertInvalidStateErrorByValidWhile(e, provider, service,
                    VOIPProviderLifecycleMeta.States.ServiceAvailable.class);
            throw e;
        }
    }

    @Test(expected = LifecycleException.class)
    public void test_inbound_while_with_non_conditional_transition() {
        final Customer customer = new Customer();
        customer.activate();
        InternetServiceOrderWithInboundWhile service = new InternetServiceOrderWithInboundWhile(new Date(), null,
                customer, "3 years");
        customer.cancel();
        assertEquals(CustomerLifecycleMeta.States.Canceled.class.getSimpleName(), customer.getState());
        try {
            service.start();
        } catch (LifecycleException e) {
            assertViolateInboundWhileRelationConstraint(e,
                    InternetServiceLifecycleMetaWithInboundWhile.Transitions.Start.class,
                    InternetServiceLifecycleMetaWithInboundWhile.States.InService.class, service, customer,
                    CustomerLifecycleMeta.States.Active.class);
        }
    }

    @Test(expected = LifecycleException.class)
    public void test_inbound_while_with_conditional_transition_prevalidate_inbound_while_negative() {
        final PowerObject power = new PowerObject();
        final KeyBoardObjectPreValidateCondition keyboard = new KeyBoardObjectPreValidateCondition(power);
        power.shutDown();
        assertState(PowerLifecycleMetadata.States.PowerOff.class, power);
        try {
            keyboard.pressAnyKey();
        } catch (LifecycleException e) {
            assertViolateInboundWhileRelationConstraint(e,
                    KeyBoardLifecycleMetadataPreValidateCondition.Transitions.PressAnyKey.class,
                    KeyBoardLifecycleMetadataPreValidateCondition.States.ReadingInput.class, keyboard, power,
                    PowerLifecycleMetadata.States.PowerOn.class);
        }
    }

    @Test(expected = LifecycleException.class)
    public void test_inbound_while_with_conditional_transition_postvalidate_inbound_while_negative() {
        final PowerObject power = new PowerObject();
        final KeyBoardObjectPostValidateCondition keyboard = new KeyBoardObjectPostValidateCondition(power);
        keyboard.pressAnyKey();
        assertState(KeyBoardLifecycleMetadataPostValidateCondition.States.ReadingInput.class, keyboard);
        try {
            keyboard.pressAnyKey();
        } catch (LifecycleException e) {
            assertViolateInboundWhileRelationConstraint(e,
                    KeyBoardLifecycleMetadataPostValidateCondition.Transitions.PressAnyKey.class,
                    KeyBoardLifecycleMetadataPostValidateCondition.States.Broken.class, keyboard, power,
                    PowerLifecycleMetadata.States.PowerOn.class);
        }
    }
}
