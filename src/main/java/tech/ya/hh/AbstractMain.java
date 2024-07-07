/**
 * 
 */
package tech.ya.hh;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import tech.ya.hh.constant.Constant;
import tech.ya.hh.constant.Processes;
import tech.ya.hh.message.MessageProcess;

/**
 *
 *         Abstract main class used to define common objects for both games and also useful for tests
 */
public class AbstractMain
{
    public  String processes = Processes.SINGLEPROCESS.getProcess();

    public  String getProcesses() { return processes; }

    private AtomicInteger countDown = new AtomicInteger(Constant.numberOfMessages);

    private CountDownLatch latch = new CountDownLatch(Constant.numberOfMessages);

    private BlockingQueue<MessageProcess> initiatorQueue = new LinkedBlockingQueue<>(Constant.numberOfMessages);

    private BlockingQueue<MessageProcess> playerSecondQueue = new LinkedBlockingQueue<>(Constant.numberOfMessages);

    public AtomicInteger getCountDown()
    {
        return countDown;
    }

    public CountDownLatch getLatch()
    {
        return latch;
    }

    public BlockingQueue<MessageProcess> getInitiatorQueue()
    {
        return initiatorQueue;
    }

    public BlockingQueue<MessageProcess> getPlayerSecondQueue()
    {
        return playerSecondQueue;
    }

}
