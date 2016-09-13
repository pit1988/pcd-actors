package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.AbsActorSystem;
import it.unipd.math.pcd.actors.ActorRef;

/**
 * Concrete implementation of the actor system.
 *
 * @author Michele Caovilla
 * @version 1.0
 * @since 1.0
 */
public class ActorSystemImpl extends AbsActorSystem {

    @Override
    protected ActorRef createActorReference(ActorMode mode) {

        if (mode == ActorMode.REMOTE)
            throw new IllegalArgumentException();
        else
            return new ActorRefImpl(this);
    }
}