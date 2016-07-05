package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.*;
import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;

/**
 * Concrete implementation of actor reference.
 *
 * @author Pietro Gabelli * @version 1.0
 * @since 1.0
 */
public class AbsActorRef<T extends Message> implements ActorRef<T> {

    private final AbsActorSystem system;

    /**
     * AbsActorRef constructor. Requires an instance of the ActorSystem.
     * @param system the instance of the BaseActorSystem object
     */
    public AbsActorRef(AbsActorSystem sys) {
        system = sys;
    }

    /**
     * Check if an instance of an ActorRef is the same of the current object instance.
     * @param o ActorRef compared to the current instance
     * @return 0 if the instance is the same or the actors are the same, 1 otherwise.
     */
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
    /**
     * Send a {@code message} to another actor.
     * @param message The message to send
     * @param to The actor to which sending the message
     */

    @Override
    public void send(T message, ActorRef to) {
        AbsActor receiver = (AbsActor) system.getActor(to);

        if (receiver == null || receiver.isStopped())
            throw new NoSuchActorException();
        else
          receiver.addToMailbox(new AbsMessage(message, this));
    }
}

    // @Override
    // public void send(T message, ActorRef to) {
    //     AbsActor receiver = (AbsActor) actorSystem.getActor(to);
    //     receiver.scheduleMessage(message, this);
    // }