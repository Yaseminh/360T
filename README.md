
## Task ##

Given a Player class - an instance of which can communicate with other Players.

The requirements are as follows:

1. create 2 Player instances
2. one of the players should send a message to second player (let's call this player "initiator")
3. when a player receives a message, it should reply with a message that contains the received message concatenated with the value of a counter holding the number of messages this player already sent.
4. finalize the program (gracefully) after the initiator sent 10 messages and received back 10 messages (stop condition)
5. both players should run in the same java process (strong requirement)
6. document for every class the responsibilities it has.
7. additional challenge (nice to have) opposite to 5: have every player in a separate JAVA process.

Please use core Java as much as possible without additional frameworks like Spring etc; focus on design and not on the technology.
Please include a maven project with the source code to build the jar and a shell script to start the program.
Everything not specified is to be decided by you; everything specified is a hard requirement.

You should send your source code as an archive attached to the e-mail; inline links for downloading will be ignored.

## Install and run ##

To use the application you need java 8 installed in your machine. 
For the socket game, address 61050 must not be in use, otherwise change the address in the Constant class to one that can be used to run the application

There is a default profiles with which you can install the application

To install :

	- mvn clean install

To run :

There are two games to play (both of sh files must have execute rights, see command chmod +x)

1) Game based on one process :

	./threadgame.sh

2) Game based on socket communication, simulating multi processes:

	./socketgame.sh