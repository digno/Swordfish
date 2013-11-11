package net.madz.lifecycle.engine;

import org.junit.Test;

public class EngineCoreCompositeStateMachinePositiveTests extends EngineCoreCompositeStateMachineMetadata {

    @Test
    public void test_non_relational_composite_state_machine_complete_process() {
        final ProductOrder product = new ProductOrder();
        // Outer State + Outer Transition => Composite State => Composite
        // Initial State
        {
            // Outer Initial State
            assertState(OrderLifecycle.States.Created.class, product);
            // Outer Transition
            product.start();
            assertState(OrderLifecycle.States.Started.SubStates.OrderCreated.class, product);
        }
        {
            // Composite State + Composite Transition => Composite State
            product.doProduce();
            assertState(OrderLifecycle.States.Started.SubStates.Producing.class, product);
            product.doDeliver();
            assertState(OrderLifecycle.States.Started.SubStates.Delivering.class, product);
        }
        {
            // Composite State + Composite Transition => Composite Final State
            // => Outer State
            product.confirmComplete();
            assertState(OrderLifecycle.States.Finished.class, product);
        }
    }

    @Test
    public void test_non_relational_composite_state_machine_cancel_process_with_outer_transition() {
        final ProductOrder product = new ProductOrder();
        {// Outer State + Outer Transition => Composite State => Composite
         // Initial State
            assertState(OrderLifecycle.States.Created.class, product);
            product.start();
            assertState(OrderLifecycle.States.Started.SubStates.OrderCreated.class, product);
        }
        {
            // Composite State + Composite Transition => Composite State
            product.doProduce();
            assertState(OrderLifecycle.States.Started.SubStates.Producing.class, product);
        }
        {
            // Composite State + Composite Transition => Composite Final State
            // => Outer State
            product.cancel();
            assertState(OrderLifecycle.States.Canceled.class, product);
        }
    }

    @Test
    public void test_relational_composite_state_machine_sharing_with_composite_state() {
        final Contract contract = new Contract();
        // Outer Initial State
        assertState(ContractLifecycle.States.Draft.class, contract);
        contract.activate();
        assertState(ContractLifecycle.States.Active.class, contract);
        final ProductOrderSharingValidWhile product = new ProductOrderSharingValidWhile(contract);
        // Outer State + Outer Transition => Composite State => Composite
        // Initial State
        {
            // Outer Initial State
            assertState(RelationalOrderLifecycleSharingValidWhile.States.Created.class, product);
            // Outer Transition
            product.start();
            assertState(RelationalOrderLifecycleSharingValidWhile.States.Started.SubStates.OrderCreated.class, product);
        }
        {
            // Composite State + Composite Transition => Composite State
            product.doProduce();
            assertState(RelationalOrderLifecycleSharingValidWhile.States.Started.SubStates.Producing.class, product);
            product.doDeliver();
            assertState(RelationalOrderLifecycleSharingValidWhile.States.Started.SubStates.Delivering.class, product);
        }
        {
            // Composite State + Composite Transition => Composite Final State
            // => Outer State
            product.confirmComplete();
            assertState(RelationalOrderLifecycleSharingValidWhile.States.Finished.class, product);
        }
    }

    @Test
    public void test_relational_composite_state_machine_with_outer_relations() {
        final Contract contract = new Contract();
        // Outer Initial State
        assertState(ContractLifecycle.States.Draft.class, contract);
        contract.activate();
        assertState(ContractLifecycle.States.Active.class, contract);
        final ProductOrderOuterValidWhile product = new ProductOrderOuterValidWhile(contract);
        // Outer State + Outer Transition => Composite State => Composite
        // Initial State
        {
            // Outer Initial State
            assertState(RelationalOrderLifecycleReferencingOuterValidWhile.States.Created.class, product);
            // Outer Transition
            product.start();
            assertState(RelationalOrderLifecycleReferencingOuterValidWhile.States.Started.SubStates.OrderCreated.class,
                    product);
        }
        {
            // Composite State + Composite Transition => Composite State
            product.doProduce();
            assertState(RelationalOrderLifecycleReferencingOuterValidWhile.States.Started.SubStates.Producing.class,
                    product);
            product.doDeliver();
            assertState(RelationalOrderLifecycleReferencingOuterValidWhile.States.Started.SubStates.Delivering.class,
                    product);
        }
        {
            // Composite State + Composite Transition => Composite Final State
            // => Outer State
            product.confirmComplete();
            assertState(RelationalOrderLifecycleReferencingOuterValidWhile.States.Finished.class, product);
        }
    }

    @Test
    public void test_relational_composite_state_machine_with_inner_relations() {
        final Contract contract = new Contract();
        // Outer Initial State
        assertState(ContractLifecycle.States.Draft.class, contract);
        contract.activate();
        assertState(ContractLifecycle.States.Active.class, contract);
        final ProductOrderInnerValidWhile product = new ProductOrderInnerValidWhile(contract);
        // Outer State + Outer Transition => Composite State => Composite
        // Initial State
        {
            // Outer Initial State
            assertState(RelationalOrderLifecycleReferencingInnerValidWhile.States.Created.class, product);
            // Outer Transition
            product.start();
            assertState(RelationalOrderLifecycleReferencingInnerValidWhile.States.Started.SubStates.OrderCreated.class,
                    product);
        }
        {
            // Composite State + Composite Transition => Composite State
            product.doProduce();
            assertState(RelationalOrderLifecycleReferencingInnerValidWhile.States.Started.SubStates.Producing.class,
                    product);
            product.doDeliver();
            assertState(RelationalOrderLifecycleReferencingInnerValidWhile.States.Started.SubStates.Delivering.class,
                    product);
        }
        {
            // Composite State + Composite Transition => Composite Final State
            // => Outer State
            product.confirmComplete();
            assertState(RelationalOrderLifecycleReferencingInnerValidWhile.States.Finished.class, product);
        }
    }
}
