
package tech.ya.hh.players;

import java.util.concurrent.BlockingQueue;

import tech.ya.hh.message.MessageProcess;
import tech.ya.hh.players.enums.PlayerTypes;

/**
 *
 *         Generic player of the game communicating with {@link Initiator} through thread safe {@link BlockingQueue} that waits until a queue has
 *         messages to process
 */
public class PlayerSecond extends Thread implements Player
{

    private BlockingQueue<MessageProcess> queue;
    private BlockingQueue<MessageProcess> othersQueue;
    private int sentMessages = 0;

    private Object lock = new Object();

    /**
     * 
     */
    public PlayerSecond(BlockingQueue<MessageProcess> queue, BlockingQueue<MessageProcess> othersQueue)
    {
        this.queue = queue;
        this.othersQueue = othersQueue;
    }

    @Override
    public void run()
    {
        Thread.currentThread().setName(PlayerTypes.PLAYERSECOND.getDescription());

        MessageProcess messageProcess = new MessageProcess();
        messageProcess.setPlayerType(PlayerTypes.PLAYERSECOND);

        System.out.println(PlayerTypes.PLAYERSECOND.getDescription() + " thread running");

        while (true)
        {

            try
            {
                synchronized (lock)
                {
                    takeMessage();

                    messageProcess.setMessage(Thread.currentThread().getName() + " is sending message n. " + ++sentMessages);

                    putMessage(messageProcess);

                    Thread.sleep(1000);

                }
            }
            catch (InterruptedException e)
            {
            }
        }

    }

    /**
     * @throws InterruptedException
     * 
     */
    @Override
    public void putMessage(MessageProcess messageProcess) throws InterruptedException
    {
        System.out.println(Thread.currentThread().getName() + " is sending a message");

        othersQueue.put(messageProcess);
    }

    /**
     * @throws InterruptedException
     */
    @Override
    public void takeMessage() throws InterruptedException
    {
        String user = Thread.currentThread().getName();
        System.out.println(user);
        System.out.println(Thread.currentThread().getName() + " is waiting for messages");
        MessageProcess messageProcess = queue.take();
        System.out.println(Thread.currentThread().getName() + " has found a new message : " + messageProcess.getMessage());
    }

}
