/**
 * 
 */
package tech.ya.hh.players;

import com.fasterxml.jackson.core.JsonProcessingException;

import tech.ya.hh.message.MessageProcess;

/**
 *
 *         Player interface for thread game that put and take messages from a queue
 */
public interface Player
{

    <T extends MessageProcess> void putMessage(T dispatcher) throws InterruptedException, JsonProcessingException;

    void takeMessage() throws InterruptedException;

}
