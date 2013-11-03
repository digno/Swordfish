package net.madz.lifecycle.syntax;

import net.madz.lifecycle.StateConverter;
import net.madz.lifecycle.annotations.Function;
import net.madz.lifecycle.annotations.LifecycleMeta;
import net.madz.lifecycle.annotations.StateIndicator;
import net.madz.lifecycle.annotations.StateMachine;
import net.madz.lifecycle.annotations.StateSet;
import net.madz.lifecycle.annotations.Transition;
import net.madz.lifecycle.annotations.TransitionSet;
import net.madz.lifecycle.annotations.state.Converter;
import net.madz.lifecycle.annotations.state.End;
import net.madz.lifecycle.annotations.state.Initial;
import net.madz.lifecycle.syntax.StateIndicatorMetadata.PS1.Transitions.S1_X;

public class StateIndicatorMetadata extends BaseMetaDataTest {

    @StateMachine
    static interface PS1 {

        @StateSet
        static interface States {

            @Initial
            @Function(transition = Transitions.S1_X.class, value = { S1_B.class })
            static interface S1_A {}
            @End
            static interface S1_B {}
        }
        @TransitionSet
        static interface Transitions {

            static interface S1_X {}
        }
    }
    @LifecycleMeta(PS1.class)
    static interface PStateIndicatorInterface {

        @Transition(S1_X.class)
        void doX();

        @StateIndicator
        String getState();
    }
    @LifecycleMeta(PS1.class)
    static interface PStateIndicatorConverterInterface {

        @Transition(S1_X.class)
        void doX();

        @StateIndicator
        @Converter(StateConverterImpl.class)
        Integer getState();
    }
    public static class StateConverterImpl implements StateConverter<Integer> {

        @Override
        public String toState(Integer t) {
            switch (t.intValue()) {
                case 1:
                    return PS1.States.S1_A.class.getSimpleName();
                case 2:
                    return PS1.States.S1_B.class.getSimpleName();
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public Integer fromState(String state) {
            switch (state) {
                case "S1_A":
                    return 1;
                case "S2_A":
                    return 2;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
    @LifecycleMeta(PS1.class)
    static interface NStateIndicator {

        @Transition(S1_X.class)
        void doX();

        @StateIndicator
        String getState();

        // Should not have public stateSetter
        void setState(String state);
    }
    @LifecycleMeta(PS1.class)
    static interface NStateIndicatorConverter {

        @Transition(S1_X.class)
        void doX();

        @StateIndicator
        @Converter(StateConverterImpl.class)
        Integer getState();

        // Should not have public stateSetter
        void setState(Integer state);
    }
    @LifecycleMeta(PS1.class)
    static class PrivateStateFieldClass {

        @StateIndicator
        private String state;

        @Transition(S1_X.class)
        public void doX() {}
    }
    @LifecycleMeta(PS1.class)
    static class NPublicStateFieldClass {

        @StateIndicator
        public String state;

        @Transition(S1_X.class)
        public void doX() {}
    }
    @LifecycleMeta(PS1.class)
    static class PrivateStateFieldConverterClass {

        @StateIndicator
        @Converter(StateConverterImpl.class)
        private Integer state;

        @Transition(S1_X.class)
        public void doX() {}
    }
    @LifecycleMeta(PS1.class)
    static class NPublicStateFieldConverterClass {

        @StateIndicator
        @Converter(StateConverterImpl.class)
        public String state;

        @Transition(S1_X.class)
        public void doX() {}
    }
    @LifecycleMeta(PS1.class)
    static class PrivateStateSetterClass {

        private String state;

        @Transition(S1_X.class)
        public void doX() {}

        @StateIndicator
        public String getState() {
            return state;
        }

        @SuppressWarnings("unused")
        private void setState(String state) {
            this.state = state;
        }
    }
    @LifecycleMeta(PS1.class)
    static class NPublicStateSetterClass {

        private String state;

        @Transition(S1_X.class)
        public void doX() {}

        @StateIndicator
        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
