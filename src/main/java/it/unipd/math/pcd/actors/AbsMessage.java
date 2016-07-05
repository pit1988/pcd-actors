package it.unipd.math.pcd.actors;

/**
 * Abstract implementation of message.
 *
 * @author Pietro Gabelli
 * @version 1.0
 * @since 1.0
 */
public abstract class AbsMessage<T extends Message> {
    protected final T message;
    protected final ActorRef<T> sender;

    protected AbsMessage(T msg, ActorRef<T> sndr) {
        this.message = msg;
        this.sender = sndr;
    }

    public abstract Message getMessage();

    public abstract ActorRef getSender();
}
