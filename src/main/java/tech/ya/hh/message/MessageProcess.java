/**
 * 
 */
package tech.ya.hh.message;

import tech.ya.hh.players.enums.PlayerTypes;


public class MessageProcess
{

    private String message;
    private PlayerTypes playerType;
    private boolean isGame = true;

    public MessageProcess()
    {
    }

    public MessageProcess(PlayerTypes playerType)
    {
        this.playerType = playerType;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public PlayerTypes getPlayerType()
    {
        return playerType;
    }

    public void setPlayerType(PlayerTypes playerType)
    {
        this.playerType = playerType;
    }

    public boolean isGame()
    {
        return isGame;
    }

    public void setGame(boolean isGame)
    {
        this.isGame = isGame;
    }
}
