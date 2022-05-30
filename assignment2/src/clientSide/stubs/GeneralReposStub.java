package clientSide.stubs;

import commInfra.ClientCom;
import commInfra.Message;
import commInfra.MessageType;
import genclass.GenericIO;

/**
 * Stub to the general repository.
 *
 * It instantiates a remote reference to the general repository. Implementation
 * of a client-server model of type 2 (server replication). Communication is
 * based on a communication channel under the TCP protocol.
 */

public class GeneralReposStub {
	/**
	 * Name of the platform where is located the general repository server.
	 */

	private String serverHostName;

	/**
	 * Port number for listening to service requests.
	 */

	private int serverPortNum;

	/**
	 * Instantiation of a stub to the general repository.
	 *
	 * @param serverHostName name of the platform where is located the barber shop
	 *                       server
	 * @param serverPortNumb port number for listening to service requests
	 */

	public GeneralReposStub(String serverHostName, int serverPortNumb) {
		this.serverHostName = serverHostName;
		this.serverPortNum = serverPortNumb;
	}

//	public void initSimulation(String fileName, int nIter) {
//		ClientCom com; // communication channel
//		Message outMessage, // outgoing message
//				inMessage; // incoming message
//
//		com = new ClientCom(serverHostName, serverPortNum);
//		while (!com.open()) {
//			try {
//				Thread.sleep((long) (1000));
//			} catch (InterruptedException e) {
//			}
//		}
//		outMessage = new Message(MessageType.SETNFIC, fileName, nIter);
//		com.writeObject(outMessage);
//		inMessage = (Message) com.readObject();
//		if (inMessage.getMsgType() != MessageType.NFICDONE) {
//			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
//			GenericIO.writelnString(inMessage.toString());
//			System.exit(1);
//		}
//		com.close();
//	}

	/**
	 * Set student state.
	 *
	 * @param id    student id
	 * @param state student state
	 */

	public void setStudentState(int id, int state) {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.sleep((long) (1000));
			} catch (InterruptedException e) {
			}
		}
		outMessage = new Message(MessageType.STSST, id, state);
		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();
		if (inMessage.getMsgType() != MessageType.SACK) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}
		com.close();
	}
	
	/**
	 * Write in the logging file the updated student state
	 * @param id student id
	 * @param state student state to be set
	 * @param hold specifies if prints line of report status
	 */
	public void setStudentState(int id, int state, boolean hold)
	{
	   	ClientCom com;					//Client communication
		Message outMessage, inMessage; 	//outGoing and inGoing messages
			
		com = new ClientCom (serverHostName, serverPortNum);
		//Wait for a connection to be established
		while(!com.open())
		{	try 
		  	{ Thread.currentThread ().sleep ((long) (10));
		  	}
			catch (InterruptedException e) {}
		}
		
		outMessage = new Message (MessageType.STSST2, id, state, hold);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.SACK)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();		
	}

	/**
	 * Set chef state.
	 *
	 * @param state chef state
	 */

	public void setChefState(int state) {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.sleep((long) (1000));
			} catch (InterruptedException e) {
			}
		}
		outMessage = new Message(MessageType.STCST, 1, state);
		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();
		
		if (inMessage.getMsgType() != MessageType.SACK) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}
		com.close();
	}

	/**
	 * Set waiter state.
	 *
	 * @param state waiter state
	 */

	public void setWaiterState(int state) {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.sleep((long) (1000));
			} catch (InterruptedException e) {
			}
		}
		outMessage = new Message(MessageType.STWST, 1, state);
		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();
		if (inMessage.getMsgType() != MessageType.SACK) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}
		com.close();
	}

	/**
	 * Set variable nCourses and report in the logging file
	 * @param n nCourses to set
	 */
	public void setnCourses(int nCourses)
	{
	   	ClientCom com;					//Client communication
		Message outMessage, inMessage; 	//outGoing and inGoing messages
			
		com = new ClientCom (serverHostName, serverPortNum);
		//Wait for a connection to be established
		while(!com.open())
		{	try 
		  	{ Thread.currentThread ().sleep ((long) (10));
		  	}
			catch (InterruptedException e) {}
		}
		
		outMessage = new Message (MessageType.STCOUR, nCourses);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.SACK)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();		
	}
	
	/**
	 * Set variable nPortions and report status in the logging file
	 * @param value nCourses value to set
	 */
	public void setnPortions(int value)
	{
	   	ClientCom com;					//Client communication
		Message outMessage, inMessage; 	//outGoing and inGoing messages
			
		com = new ClientCom (serverHostName, serverPortNum);
		//Wait for a connection to be established
		while(!com.open())
		{	try 
		  	{ Thread.currentThread ().sleep ((long) (10));
		  	}
			catch (InterruptedException e) {}
		}
		
		outMessage = new Message (MessageType.STPOR, value);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.SACK)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();		
	}
	
	public void updateSeatsAtTable(int studentID, int studentSeat) {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.sleep((long) (1000));
			} catch (InterruptedException e) {
			}
		}
		outMessage = new Message(MessageType.STUSAT, studentID, studentSeat);

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if (inMessage.getMsgType() != MessageType.SACK/* MessageType.USSEATDONE */) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}
		com.close();
	}
	
	/**
	 * Update the leaving of a student in the seats of the table
	 * @param id student id to leave table
	 */
	public void updateSeatsAtLeaving(int id)
	{
	   	ClientCom com;					//Client communication
		Message outMessage, inMessage; 	//outGoing and inGoing messages
			
		com = new ClientCom (serverHostName, serverPortNum);
		//Wait for a connection to be established
		while(!com.open())
		{	try 
		  	{ Thread.currentThread ().sleep ((long) (10));
		  	}
			catch (InterruptedException e) {}
		}
		
		outMessage = new Message (MessageType.STUSATL, id);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.SACK)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();		
	}
	

	public void shutdown() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.sleep((long) (1000));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.SHUT);

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if (inMessage.getMsgType() != MessageType.SHUTDONE) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}
		com.close();
	}
}