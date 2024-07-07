/**
 * 
 */
package tech.ya.hh;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import tech.ya.hh.constant.Constant;
import tech.ya.hh.message.MessageProcess;
import tech.ya.hh.message.SocketMessageProcess;
import tech.ya.hh.players.Initiator;
import tech.ya.hh.players.PlayerSecond;
import tech.ya.hh.players.enums.PlayerTypes;
import tech.ya.hh.socket.client.InitiatorClient;
import tech.ya.hh.socket.client.PlayerSecondClient;
import tech.ya.hh.socket.server.SocketServer;

/**
 * 
 *         Main entry point of the application with 2 different games depending on the args input.
 * 
 *         {@link #playSocket} will start a game simulating two client communicating over a network with sockets
 * 
 *         {@link #playThread} will start a game of two simple Thread communicating with queues
 * 
 *         Both games anyway are based on a count down that will stop after required condition is satisfied for the initiator.
 * 
 *         {@link CountDownLatch} will help to wait for the condition to be completed
 */
public class Main extends AbstractMain
{
    private static Main instance = new Main();

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        if ("SINGLEPROCESS".equals(instance.getProcesses()))
        {
            instance.playThread();
        }

        if ("MULTIPROCESS".equals(instance.getProcesses()))
        {
            instance.startServer();
            instance.playSocket();
        }

    }

    /**
     * @throws IOException
     * 
     */
    public void startServer() throws IOException
    {

        new SocketServer(Constant.socketPort);

    }

    private void playSocket() throws Exception
    {

        InitiatorClient initiatorClient = new InitiatorClient(Constant.localhost, Constant.socketPort, getLatch(), getCountDown());

        play(initiatorClient, new PlayerSecondClient(Constant.localhost, Constant.socketPort), getLatch());
    }

    public void play(InitiatorClient initiator, PlayerSecondClient playerSecond, CountDownLatch latch) throws InterruptedException, IOException
    {
        System.out.println("Game started ...");

        SocketMessageProcess initiatorDispatcher = new SocketMessageProcess();
        initiatorDispatcher.setPlayerType(PlayerTypes.INITIATOR);
        initiatorDispatcher.setSocket(initiator.getSocketString());

        initiatorDispatcher.setMessage("Message number 1 form InitiatorClient : Hello world!");

        initiator.send(initiatorDispatcher);

        latch.await();

        this.stopCondition(instance.getProcesses());

    }

    private void playThread() throws InterruptedException
    {
        Initiator inititator = new Initiator(getInitiatorQueue(), getCountDown(), getPlayerSecondQueue(), getLatch());
        PlayerSecond playerSecond = new PlayerSecond(getPlayerSecondQueue(), getInitiatorQueue());

        play(inititator, playerSecond, getLatch());
    }

    public void play(Initiator initiator, PlayerSecond playerSecond, CountDownLatch latch) throws InterruptedException
    {
        System.out.println("Game started ...");

        /**
         * Initiator send first message
         */
        MessageProcess dispatcher = new MessageProcess();
        dispatcher.setPlayerType(PlayerTypes.INITIATOR);
        dispatcher.setMessage("Message number 1 from Initiator : Hello world!");
        initiator.putMessage(dispatcher);

        /**
         * Start threads and wait until count down latch goes to zero
         */
        initiator.setDaemon(true);
        initiator.start();

        playerSecond.setDaemon(true);
        playerSecond.start();

        latch.await();

        System.out.println(PlayerTypes.INITIATOR.getDescription() + " terminated the game ...");

        this.stopCondition(instance.getProcesses());
    }

    public void stopCondition(String process){
        System.out.println(process + " is Exited Gracefully(!)");
    }
}
