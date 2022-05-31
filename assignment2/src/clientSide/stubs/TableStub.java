/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

/**
 * Stub to the Table.
 *
 * It instantiates a remote reference to the table. Implementation of a
 * client-server model of type 2 (server replication). Communication is based on
 * a communication channel under the TCP protocol.
 */
public class TableStub {
	/**
	 * Name of the platform where is located the table Server
	 */
	private String serverHostName;

	/**
	 * Port number for listening to service requests
	 */
	private int serverPortNum;

	/**
	 * Instantiation of a stub to the Table
	 * 
	 * @param serverHostName name of the platform where is located the table Server
	 * @param serverPortNum  port number for listening to service requests
	 */
	public TableStub(String serverHostName, int serverPortNum) {
		this.serverHostName = serverHostName;
		this.serverPortNum = serverPortNum;
	}
	
	public void setFirstStudent(int studentId) {
		ClientCom com;                                                 // communication channel
	    Message outMessage,                                            // outgoing message
	            inMessage;                                             // incoming message

	    com = new ClientCom (serverHostName, serverPortNum);
	    while (!com.open()) {
	    	try {
	    		Thread.currentThread().sleep((long)(10));
	    	} catch(InterruptedException e) {}
	    }
	    
	    //MESSAGES
	    outMessage = new Message(MessageType.REQSFTA, studentId);
	    
	    com.writeObject(outMessage);
	    inMessage = (Message) com.readObject();
	    
	    if((inMessage.getMsgType() != MessageType.SFTADONE)) { 
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Message Type!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
//	    if(inMessage.getStudentId() != studentId) {
//	    	GenericIO.writelnString("Thread Student"+inMessage.getStudentId()+": Invalid Student ID!");
//	    	GenericIO.writelnString(inMessage.toString());
//	    	System.exit(1);
//	    }
	    
	    com.close();
	}
	
	public void setLastStudent(int studentId) {
		ClientCom com;                                                 // communication channel
	    Message outMessage,                                            // outgoing message
	            inMessage;                                             // incoming message

	    com = new ClientCom (serverHostName, serverPortNum);
	    while (!com.open()) {
	    	try {
	    		Thread.currentThread().sleep((long)(10));
	    	} catch(InterruptedException e) {}
	    }
	    
	    //MESSAGES
	    outMessage = new Message(MessageType.REQSLTA, studentId);
	    
	    com.writeObject(outMessage);
	    inMessage = (Message) com.readObject();
	    
	    if((inMessage.getMsgType() != MessageType.SLTADONE)) { 
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Message Type!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
//	    if(inMessage.getStudentId() != studentId) {
//	    	GenericIO.writelnString("Thread Student"+inMessage.getStudentId()+": Invalid Student ID!");
//	    	GenericIO.writelnString(inMessage.toString());
//	    	System.exit(1);
//	    }
	    
	    com.close();
	}

	public void saluteTheClient(int studentIDBeingAnswered) {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQSC, studentIDBeingAnswered,
				((Waiter) Thread.currentThread()).getWaiterState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.SCDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if(inMessage.getStudentBeingAnswered() != -1)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		if(inMessage.getWaiterState() < WaiterStates.APPRAISING_SITUATION || inMessage.getWaiterState() > WaiterStates.RECEIVING_PAYMENT)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid waiter state!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}

		((Waiter) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
		com.close();
	}

	public void returnToBar() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQRB, ((Waiter) Thread.currentThread()).getWaiterState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.RBDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getWaiterState() < WaiterStates.APPRAISING_SITUATION || inMessage.getWaiterState() > WaiterStates.RECEIVING_PAYMENT) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Waiter State!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		((Waiter) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
		com.close();
	}

	public void getThePad() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQGP, ((Waiter) Thread.currentThread()).getWaiterState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.GPDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getWaiterState() < WaiterStates.APPRAISING_SITUATION || inMessage.getWaiterState() > WaiterStates.RECEIVING_PAYMENT) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Waiter State!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		((Waiter) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
		com.close();
	}

	public boolean haveAllClientsBeenServed() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQHACBS, ((Waiter) Thread.currentThread()).getWaiterState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.HACBSDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		com.close();

		return inMessage.getAllClientsBeenServed();
	}

	public void deliverPortion() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQDP, ((Waiter) Thread.currentThread()).getWaiterState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.DPDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		com.close();
	}

	public void presentTheBill() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQPB, ((Waiter) Thread.currentThread()).getWaiterState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.PBDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getWaiterState() < WaiterStates.APPRAISING_SITUATION || inMessage.getWaiterState() > WaiterStates.RECEIVING_PAYMENT) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Waiter State!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		((Waiter) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
		com.close();
	}

	public int getFirstStudent() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQGFTA, ((Student) Thread.currentThread()).getStudentId(),
				((Student) Thread.currentThread()).getStudentState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.GFTADONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		com.close();

		return inMessage.getFirstToArrive();
	}

	public int getLastToEat() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQGLTE, ((Student) Thread.currentThread()).getStudentId(),
				((Student) Thread.currentThread()).getStudentState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.GLTEDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		com.close();

		return inMessage.getLastToEat();
	}

	public void seatAtTable() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQSAT, ((Student) Thread.currentThread()).getStudentId(),
				((Student) Thread.currentThread()).getStudentState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.SATDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getStudentId() != ((Student) Thread.currentThread()).getStudentId()) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Student ID!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getStudentState() < StudentStates.GOING_TO_THE_RESTAURANT || inMessage.getStudentState() > StudentStates.GOING_HOME) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Student State!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}
		
		((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
		
		com.close();
	}

	public void readTheMenu() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQRM, ((Student) Thread.currentThread()).getStudentId(),
				((Student) Thread.currentThread()).getStudentState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.RMDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getStudentId() != ((Student) Thread.currentThread()).getStudentId()) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Student ID!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getStudentState() < StudentStates.GOING_TO_THE_RESTAURANT || inMessage.getStudentState() > StudentStates.GOING_HOME) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Student State!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
		com.close();
	}

	public void prepareOrder() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQPO, ((Student) Thread.currentThread()).getStudentId(),
				((Student) Thread.currentThread()).getStudentState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.PODONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getStudentState() < StudentStates.GOING_TO_THE_RESTAURANT || inMessage.getStudentState() > StudentStates.GOING_HOME) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Student State!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
		com.close();
	}

	public boolean hasEverybodyChosen() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQEHC, ((Student) Thread.currentThread()).getStudentId(),
				((Student) Thread.currentThread()).getStudentState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.EHCDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		com.close();

		return inMessage.getEverybodyHasChosen();
	}

	public void addUpOnesChoices() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQAUOC);

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.AUOCDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		com.close();
	}

	public void describeOrder() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQDO, ((Student) Thread.currentThread()).getStudentId(),
				((Student) Thread.currentThread()).getStudentState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.DODONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		com.close();
	}

	public void joinTalk() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQJT, ((Student) Thread.currentThread()).getStudentId(),
				((Student) Thread.currentThread()).getStudentState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.JTDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getStudentState() < StudentStates.GOING_TO_THE_RESTAURANT || inMessage.getStudentState() > StudentStates.GOING_HOME) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Student State!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
		com.close();
	}

	public void informCompanion() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQIC, ((Student) Thread.currentThread()).getStudentId(),
				((Student) Thread.currentThread()).getStudentState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.ICDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getStudentId() != ((Student) Thread.currentThread()).getStudentId()) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Student ID!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getStudentState() < StudentStates.GOING_TO_THE_RESTAURANT || inMessage.getStudentState() > StudentStates.GOING_HOME) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Student State!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
		com.close();
	}

	public void startEating() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQSE, ((Student) Thread.currentThread()).getStudentId(),
				((Student) Thread.currentThread()).getStudentState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.SEDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getStudentId() != ((Student) Thread.currentThread()).getStudentId()) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Student ID!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getStudentState() < StudentStates.GOING_TO_THE_RESTAURANT || inMessage.getStudentState() > StudentStates.GOING_HOME) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Student State!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
		com.close();
	}

	public void endEating() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQEE, ((Student) Thread.currentThread()).getStudentId(),
				((Student) Thread.currentThread()).getStudentState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.EEDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getStudentId() != ((Student) Thread.currentThread()).getStudentId()) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Student ID!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getStudentState() < StudentStates.GOING_TO_THE_RESTAURANT || inMessage.getStudentState() > StudentStates.GOING_HOME) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Student State!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
		com.close();
	}

	// talvez seja este o nome hasEverybodyFinishedEating()
	public boolean hasEveryoneFinishedPortion() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQHEFE, ((Student) Thread.currentThread()).getStudentId(),
				((Student) Thread.currentThread()).getStudentState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.HEFEDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getStudentId() != ((Student) Thread.currentThread()).getStudentId()) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Student ID!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		com.close();

		return inMessage.getHasEveryoneFinishedPortion();
	}

	public void honourTheBill() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQHB, ((Student) Thread.currentThread()).getStudentId(),
				((Student) Thread.currentThread()).getStudentState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.HBDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		com.close();
	}

	public boolean everyoneHasEaten() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQHACBE, ((Student) Thread.currentThread()).getStudentId(),
				((Student) Thread.currentThread()).getStudentState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.HACBEDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		com.close();

		return inMessage.getEveryoneHasEaten();
	}

	public boolean shouldHaveArrivedEarlier() {
		ClientCom com; // communication channel
		Message outMessage, // outgoing message
				inMessage; // incoming message

		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open()) {
			try {
				Thread.currentThread().sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		// MESSAGES
		outMessage = new Message(MessageType.REQSHAE, ((Student) Thread.currentThread()).getStudentId(),
				((Student) Thread.currentThread()).getStudentState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.SHAEDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getStudentId() != ((Student) Thread.currentThread()).getStudentId()) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Student ID!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getStudentState() < StudentStates.GOING_TO_THE_RESTAURANT || inMessage.getStudentState() > StudentStates.GOING_HOME) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Student State!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
		com.close();

		return inMessage.getArrivedEarlier();
	}

	/**
	 * Operation server shutdown.
	 *
	 * New operation.
	 */
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