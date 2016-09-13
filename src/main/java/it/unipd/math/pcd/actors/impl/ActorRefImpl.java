package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.*;
import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;

/**
 * Concrete implementation of actor reference.
 *
 * @author Michele Caovilla
 * @version 1.0
 * @since 1.0
 */
public class ActorRefImpl<T extends Message> implements ActorRef<T> {

    private final AbsActorSystem system;

    public ActorRefImpl(AbsActorSystem system) {
        this.system = system;
    }

    @Override
    public int compareTo(ActorRef o) {
        if (this == o)
            return 0;
        else {
            AbsActor actor1 = (AbsActor) system.getActor(this);
            AbsActor actor2 = (AbsActor) system.getActor(o);

            return (actor1.getSelfRef() == actor2.getSelfRef()) ? 0 : 1;
        }
    }

    @Override
    public void send(T message, ActorRef to) {
        AbsActor actor = (AbsActor) system.getActor(to);

        if (actor == null || actor.isStopped())
            throw new NoSuchActorException();
        else
          actor.addToMailbox(new MessageImpl(message, this));
    }
}
