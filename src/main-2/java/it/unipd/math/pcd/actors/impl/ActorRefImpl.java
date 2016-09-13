/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Riccardo Cardin
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p/>
 */

package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.*;
import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;

/**
 * Concrete implementation of actor reference.
 *
 * @author Pietro Gabelli 
 * @version 1.0
 * @since 1.0
 */
public class ActorRefImpl<T extends Message> implements ActorRef<T> {

    private final AbsActorSystem system;

    /**
     * ActorRefImpl constructor. Requires an instance of the ActorSystem.
     * @param system the instance of the BaseActorSystem object
     */
    public ActorRefImpl(AbsActorSystem sys) {
        this.system = sys;
    }

    /**
     * Check if an instance of an ActorRef is the same of the current object instance.
     * @param ar ActorRef compared to the current instance
     * @return 0 if the instance is the same or the actors are the same, 1 otherwise.
     */
    @Override
    public int compareTo(ActorRef ar) {
        if (this == ar)
            return 0;
        else {
            AbsActor actor1 = (AbsActor) system.getActor(this);
            AbsActor actor2 = (AbsActor) system.getActor(ar);
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
          receiver.addToMailbox(new MessageImpl(message, this));
    }
}
