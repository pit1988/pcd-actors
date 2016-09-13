package it.unipd.math.pcd.actors;

/**
 * Abstract implementation of message.
 *
 * @author Michele Caovilla
 * @version 1.0
 * @since 1.0
 */
public abstract class AbsMessage<T extends Message> {
    protected final T message;
    protected final ActorRef<T> sender;

    protected AbsMessage(T message, ActorRef<T> sender) {
        this.message = message;
        this.sender = sender;
    }

    public abstract Message getMessage();

    public abstract ActorRef getSender();
}
