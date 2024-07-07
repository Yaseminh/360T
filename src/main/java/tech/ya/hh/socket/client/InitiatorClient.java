/**
 * 
 */
package tech.ya.hh.socket.client;

import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.core.JsonProcessingException;

import tech.ya.hh.message.MessageProcess;
import tech.ya.hh.message.SocketMessageProcess;
import tech.ya.hh.players.Player;
import tech.ya.hh.players.enums.PlayerTypes;

public class InitiatorClient extends AbstractClient implements Player
{
    private AtomicInteger countDown;
    private CountDownLatch latch;
    private int sentMessages = 1;

    public InitiatorClient(String IPAddress, int port, CountDownLatch latchIn, AtomicInteger countDownIn) throws Exception
    {
        setSocket(new Socket(IPAddress, port));
        setMessages(new LinkedBlockingQueue<SocketMessageProcess>());
        setServer(new ConnectionToServer(getSocket()));

        this.latch = latchIn;
        this.countDown = countDownIn;

        SocketMessageProcess dispatcherInitiator = new SocketMessageProcess();
        dispatcherInitiator.setPlayerType(PlayerTypes.INITIATOR);
        dispatcherInitiator.setSocket(getSocketString());

        Thread messageHandling = new Thread()
        {
            public void run()
            {
                while (countDown.get() > 0)
                {
                    try
                    {
                        takeMessage();

                        putMessage(dispatcherInitiator);
                    }
                    catch (InterruptedException | JsonProcessingException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            }

        };
        messageHandling.setDaemon(true);
        messageHandling.start();
    }

    @Override
    public <T extends MessageProcess> void putMessage(T dispatcher) throws InterruptedException, JsonProcessingException
    {
        dispatcher.setMessage("Initiator is sending message number " + ++sentMessages);

        send((SocketMessageProcess) dispatcher);

    }

    @Override
    public void takeMessage() throws InterruptedException
    {
        SocketMessageProcess message = getMessages().take();

        System.out.println("Initiator says ... Message Received: " + message.getMessage());

        countDown.decrementAndGet();
        latch.countDown();

        Thread.sleep(1000);
    }

}
