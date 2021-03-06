package net.madz.lifecycle.meta.instance;

import net.madz.bcel.intercept.UnlockableStack;
import net.madz.lifecycle.LifecycleContext;
import net.madz.lifecycle.meta.MetaObject;
import net.madz.lifecycle.meta.MultiKeyed;
import net.madz.lifecycle.meta.impl.builder.CallbackObject;
import net.madz.lifecycle.meta.template.RelationConstraintMetadata;
import net.madz.lifecycle.meta.template.StateMetadata;

public interface StateObject<S> extends MetaObject<StateObject<S>, StateMetadata> , MultiKeyed {

    void verifyValidWhile(Object target, RelationConstraintMetadata[] relation, Object relationInstance, UnlockableStack stack);

    void verifyInboundWhile(Object transitionKey, Object target, String nextState, RelationConstraintMetadata[] relation, Object relationInstance,
            UnlockableStack stack);

    void invokeFromPreStateChangeCallbacks(LifecycleContext<?, S> callbackContext);

    void invokeToPreStateChangeCallbacks(LifecycleContext<?, S> callbackContext);

    void invokeFromPostStateChangeCallbacks(LifecycleContext<?, S> callbackContext);

    void invokeToPostStateChangeCallbacks(LifecycleContext<?, S> callbackContext);

    void addPreToCallbackObject(Class<?> stateClass, CallbackObject callbackObject);

    void addPreFromCallbackObject(Class<?> from, CallbackObject callbackObject);

    void addPostToCallbackObject(Class<?> to, CallbackObject item);

    void addPostFromCallbackObject(Class<?> from, CallbackObject item);
}
