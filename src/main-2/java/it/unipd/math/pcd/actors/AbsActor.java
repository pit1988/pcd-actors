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
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */

/**
 * Defines common properties of all actors.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
package it.unipd.math.pcd.actors;

import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;
import it.unipd.math.pcd.actors.impl.MessageImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Defines common properties of all actors.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public abstract class AbsActor<T extends Message> implements Actor<T> {

    /**
     * Queue of the tasks of the current actor.
     */
    private final ExecutorService mailbox = Executors.newSingleThreadExecutor();

    /**
     * Self-reference of the actor
     */
    protected ActorRef<T> self;

    /**
     * Sender of the current message
     */
    protected ActorRef<T> sender;

    /*
     * Sets the self-referece.
     *
     * @param self The reference to itself
     * @return The actor.
     */
    protected final Actor<T> setSelf(ActorRef<T> self) {
        this.self = self;
        return this;
    }

    //from here

    //leave method
    /**
     * Sets the referece to the sender of current message.
     *
     * @param sender The reference to the sender
     */
    protected final void setSender(ActorRef<T> sender) {
        this.sender = sender;
    }

    /**
     * Stops gracefully the current actor.
     */
    public void stopActor() {
        mailbox.shutdown();
    }

    /**
     * Returns if the actor is stopped.
     */
    public boolean isStopped() {
        return mailbox.isShutdown();
    }

    /*
     * Returns a reference to itself.
     *
     * @return The reference to the current actor.
     */
    public ActorRef<T> getSelfRef() {
        return self;
    }


    /**
     * Adds a message to the mailbox queue. The queue will execute the message after the execution of
     * the previous messages.
     *
     * @throws it.unipd.math.pcd.actors.exceptions.NoSuchActorException
     */
    public void addToMailbox(MessageImpl message) throws NoSuchActorException {
        MessageTask task = new MessageTask(message);

        try {
            if (isStopped())
                throw new NoSuchActorException("Actor stopped");
                /*
                before commit 09d7c748bdff11099450bd7a39ea046a2a1012b4 was
                throw new UnsupportedMessageException((Message) task.message);
                */
            mailbox.execute(task);

        } catch (RejectedExecutionException ex) { //In case actor is stopped between the status check and the addition of the task
            throw new NoSuchActorException("Actor stopped");
                /*
                before commit 09d7c748bdff11099450bd7a39ea046a2a1012b4 was
                throw new UnsupportedMessageException((Message) task.message);
                */
        }
    }

    /**
     * Internal hidden class. It creates a task from a message.
     */
    private class MessageTask implements Runnable {
        public final AbsMessage message;
        public final AbsActor actor;

        MessageTask(MessageImpl message) {
            this.message = message;
            this.actor = (AbsActor) AbsActor.this;
        }

        @Override
        public void run() {
            actor.setSender(message.getSender());
            actor.receive(message.getMessage());
        }
    }
}


// /**
//  * The MIT License (MIT)
//  * <p/>
//  * Copyright (c) 2015 Riccardo Cardin
//  * <p/>
//  * Permission is hereby granted, free of charge, to any person obtaining a copy
//  * of this software and associated documentation files (the "Software"), to deal
//  * in the Software without restriction, including without limitation the rights
//  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//  * copies of the Software, and to permit persons to whom the Software is
//  * furnished to do so, subject to the following conditions:
//  * <p/>
//  * The above copyright notice and this permission notice shall be included in all
//  * copies or substantial portions of the Software.
//  * <p/>
//  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//  * SOFTWARE.
//  * <p/>
//  * Please, insert description here.
//  *
//  * @author Riccardo Cardin
//  * @version 1.0
//  * @since 1.0
//  */

// /**
//  * Please, insert description here.
//  *
//  * @author Riccardo Cardin
//  * @version 1.0
//  * @since 1.0
//  */
// package it.unipd.math.pcd.actors;

// import it.unipd.math.pcd.actors.exceptions.*; 

// import java.util.concurrent.ConcurrentLinkedQueue;

// /**
//  * Defines common properties of all actors.
//  *
//  * @author Riccardo Cardin
//  * @version 1.0
//  * @since 1.0
//  */
// public abstract class AbsActor<T extends Message> implements Actor<T> {

//     /**
//      * Self-reference of the actor
//      */
//     protected ActorRef<T> self;

//     /**
//      * Sender of the current message
//      */
//     protected ActorRef<T> sender;
    
//     /**
//      * Actor's mailbox
//      */
//     protected final ConcurrentLinkedQueue<Message> mailbox = new ConcurrentLinkedQueue<Message>();
    
//     /**
//      * Represents the current state of the actor
//      */
//     private volatile boolean stopped = false;
    
//     /**
//      * Thread that performs add and pop actions
//      */
//     private ActorThread actorThread = new ActorThread();
    
//     public AbsActor(){
//         actorThread.start();
//     }
    
//     private class ActorThread extends Thread{
        
//         public ActorThread(){
//             this.setDaemon(true);
//         }
        
//         public void run(){
//             while(!stopped){
//                 try{
//                     synchronized(mailbox){
//                         while(mailbox.size() == 0 && !stopped) mailbox.wait(); 
//                         while(mailbox.size() != 0)
//                         {
//                             Message message = mailbox.poll();
//                             receive((T) message);
//                         }
//                     }
//                 }
//                 catch(InterruptedException e){
//                     e.printStackTrace();
//                 }
//             }
//         }
        
//         /**
//          * Adds a message to mailbox
//          *
//          * @param message The message to send
//          */
//         public void addNewMessage(Message message){ 
//             synchronized(mailbox){
//                 if(stopped) throw new NoSuchActorException();
//                 mailbox.add(message);
//                 mailbox.notifyAll();
//             }
//         }
        
//     }

//     /**
//      * Sets the self-referece.
//      *
//      * @param self The reference to itself
//      * @return The actor.
//      */
//     protected final Actor<T> setSelf(ActorRef<T> self) {
//         this.self = self;
//         return this;
//     }
    
//     /**
//      * Sets the sender of a message.
//      *
//      * @param ref The sender
//      */
//     public void setSender(ActorRef ref){
//         this.sender = ref;
//     }
    
//     /**
//      * Add a new message to mailbox
//      */
//     public void addMessage(Message message){
//         actorThread.addNewMessage(message);
//     }
    
//     /**
//      * Stops the actor
//      * 
//      */
//     public void stopAbsActor(){
//         stopped = true;
//         synchronized(mailbox){
//             while(!mailbox.isEmpty()){
//                 Message message = mailbox.poll();
//                 receive((T) message);
//             }
//             mailbox.notify();
//         }
//     }
// }