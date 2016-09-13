package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.AbsMessage;
import it.unipd.math.pcd.actors.ActorRef;
import it.unipd.math.pcd.actors.Message;

/**
 * Concrete implementation of AbsMessage.
 *
 * @author Michele Caovilla
 * @version 1.0
 * @since 1.0
 */
public final class MessageImpl<T extends Message> extends AbsMessage<T> {

    public MessageImpl(T message, ActorRef<T> sender) {
        super(message, sender);
    }

    @Override
    public Message getMessage() {
        return super.message;
    }

    @Override
    public ActorRef getSender() {
        return super.sender;
    }
}
