/**
 * 
 */
package tech.ya.hh.players;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import tech.ya.hh.message.MessageProcess;
import tech.ya.hh.players.enums.PlayerTypes;


public class Initiator extends Thread implements Player
{

    private AtomicInteger countDown;

    private BlockingQueue<MessageProcess> queue;
    private BlockingQueue<MessageProcess> playerSecondQueue;
    private CountDownLatch latch;
    private int sentMessages = 1;

    private Object lock = new Object();


    public Initiator(BlockingQueue<MessageProcess> queue, AtomicInteger countDown, BlockingQueue<MessageProcess> playerSecondQueue, CountDownLatch latch)
    {
        this.queue = queue;
        this.countDown = countDown;
        this.playerSecondQueue = playerSecondQueue;
        this.latch = latch;
    }

    @Override
    public void run()
    {
        Thread.currentThread().setName(PlayerTypes.INITIATOR.getDescription());

        MessageProcess messageProcess = new MessageProcess();
        messageProcess.setPlayerType(PlayerTypes.INITIATOR);

        System.out.println(PlayerTypes.INITIATOR.getDescription() + " thread running");

        while (countDown.get() > 0)
        {
            try
            {
                synchronized (lock)
                {

                    takeMessage();

                    countDown.decrementAndGet();
                    latch.countDown();

                    Thread.sleep(1000);

                    messageProcess.setMessage(Thread.currentThread().getName() + " is sending message n." + ++sentMessages);

                    putMessage(messageProcess);

                }
            }
            catch (InterruptedException e)
            {
            }
        }

    }

    /**
     * @throws InterruptedException
     */
    @Override
    public void takeMessage() throws InterruptedException
    {
        System.out.println(Thread.currentThread().getName() + " is waiting for messages");

        MessageProcess messageProcess = queue.take();

        System.out.println(Thread.currentThread().getName() + " has found a new message : " + messageProcess.getMessage());
    }

    /**
     * @throws InterruptedException
     */
    @Override
    public void putMessage(MessageProcess messageProcess) throws InterruptedException
    {
        System.out.println(Thread.currentThread().getName() + " is sending a message");

        playerSecondQueue.add(messageProcess);
    }

}
