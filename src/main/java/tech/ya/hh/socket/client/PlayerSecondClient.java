/**
 * 
 */
package tech.ya.hh.socket.client;

import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import com.fasterxml.jackson.core.JsonProcessingException;

import tech.ya.hh.message.MessageProcess;
import tech.ya.hh.message.SocketMessageProcess;
import tech.ya.hh.players.Player;
import tech.ya.hh.players.enums.PlayerTypes;
import tech.ya.hh.socket.server.SocketServer;


public class PlayerSecondClient extends AbstractClient implements Player
{
    private int sentMessages = 0;

    public PlayerSecondClient(String IPAddress, int port) throws Exception
    {
        setSocket(new Socket(IPAddress, port));
        setMessages(new LinkedBlockingQueue<SocketMessageProcess>());
        setServer(new ConnectionToServer(getSocket()));

        SocketMessageProcess dispatcherInitiator = new SocketMessageProcess();
        dispatcherInitiator.setPlayerType(PlayerTypes.PLAYERSECOND);
        dispatcherInitiator.setSocket(getSocketString());

        Thread messageHandling = new Thread()
        {
            public void run()
            {
                while (true)
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
        dispatcher.setMessage("PlayerSecond is sending message number " + ++sentMessages);

        send((SocketMessageProcess) dispatcher);

    }

    @Override
    public void takeMessage() throws InterruptedException
    {
        SocketMessageProcess message = getMessages().take();

        System.out.println("PlayerSecond says ... Message Received: " + message.getMessage());

    }

}
