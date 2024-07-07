/**
 * 
 */
package tech.ya.hh.players.enums;


public enum PlayerTypes
{

    INITIATOR("Intiator"), PLAYERSECOND("PlayerSecond");

    private String description;

    PlayerTypes(String description)
    {
        this.description = description;

    }

    public String getDescription()
    {
        return description;
    }

}
