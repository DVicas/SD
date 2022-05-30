package clientSide.stubs;

import clientSide.entities.*;
import genclass.GenericIO;
import commInfra.*;

public class KitchenStub {
	/**
	 * Name of the platform where the kitchen Server is located
	 */
	private String serverHostName;

	/**
	 * Port number for listening to service requests
	 */
	private int serverPortNum;

	/**
	 * Instantiation of a stub to the kitchen
	 * 
	 * @param serverHostName name of the platform where is located the kitchen
	 *                       Server
	 * @param serverPortNum  port number for listening to service requests
	 */
	public KitchenStub(String serverHostName, int serverPortNum) {
		this.serverHostName = serverHostName;
		this.serverPortNum = serverPortNum;
	}

	/**
	 * 
	 * 
	 */
	public void watchTheNews() {
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
		outMessage = new Message(MessageType.REQWTN, ((Chef) Thread.currentThread()).getChefState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.WTNDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getChefState() < ChefStates.WAITING_FOR_AN_ORDER || inMessage.getChefState() > ChefStates.CLOSING_SERVICE) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Chef State!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		((Chef) Thread.currentThread()).setChefState(inMessage.getChefState());
		com.close();

	}

	public void startPreparation() {
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
		outMessage = new Message(MessageType.REQSTP, ((Chef) Thread.currentThread()).getChefState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.STPDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getChefState() < ChefStates.WAITING_FOR_AN_ORDER || inMessage.getChefState() > ChefStates.CLOSING_SERVICE) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Chef State!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		((Chef) Thread.currentThread()).setChefState(inMessage.getChefState());
		com.close();
	}

	public void proceedToPresentation() {
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
		outMessage = new Message(MessageType.REQPTP, ((Chef) Thread.currentThread()).getChefState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.PTPDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getChefState() < ChefStates.WAITING_FOR_AN_ORDER || inMessage.getChefState() > ChefStates.CLOSING_SERVICE) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Chef State!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		((Chef) Thread.currentThread()).setChefState(inMessage.getChefState());
		com.close();
	}

	public void haveNextPortionReady() {
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
		outMessage = new Message(MessageType.REQHNPR, ((Chef) Thread.currentThread()).getChefState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.HNPRDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getChefState() < ChefStates.WAITING_FOR_AN_ORDER || inMessage.getChefState() > ChefStates.CLOSING_SERVICE) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Chef State!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		((Chef) Thread.currentThread()).setChefState(inMessage.getChefState());
		com.close();
	}

	public void continuePreparation() {
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
		outMessage = new Message(MessageType.REQCP, ((Chef) Thread.currentThread()).getChefState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.CPDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getChefState() < ChefStates.WAITING_FOR_AN_ORDER || inMessage.getChefState() > ChefStates.CLOSING_SERVICE) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Chef State!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		((Chef) Thread.currentThread()).setChefState(inMessage.getChefState());
		com.close();
	}

	public boolean haveAllPortionsBeenDelivered() {
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
		outMessage = new Message(MessageType.REQHAPBD);

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.HAPBDDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		com.close();

		return inMessage.getAllPortionsBeenDelivered();
	}

	/**
	 * 
	 * @return
	 */

	public boolean hasTheOrderBeenCompleted() {
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
		outMessage = new Message(MessageType.REQHOBC);

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.HOBCDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		com.close();

		return inMessage.getHasOrderBeenCompleted();
	}

	/**
	 * 
	 */

	public void cleanUp() {
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
		outMessage = new Message(MessageType.REQCU, ((Chef) Thread.currentThread()).getChefState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.CUDONE)) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Message Type!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		if (inMessage.getChefState() < ChefStates.WAITING_FOR_AN_ORDER || inMessage.getChefState() > ChefStates.CLOSING_SERVICE) {
			GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid Chef State!");
			GenericIO.writelnString(inMessage.toString());
			System.exit(1);
		}

		((Chef) Thread.currentThread()).setChefState(inMessage.getChefState());
		com.close();
	}

	/**
	 * 
	 */

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
		outMessage = new Message(MessageType.REQRTB, ((Waiter) Thread.currentThread()).getWaiterState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.RTBDONE) /*
															 * && (inMessage.getMsgType() !=
															 * MessageType.FALTA_DAR_NOME_A_ESTA_MERDA)
															 */) {
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

	public void handNoteToChef() {
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
		outMessage = new Message(MessageType.REQHNTC, ((Waiter) Thread.currentThread()).getWaiterState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.HNTCDONE)) {
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

	public void collectPortion() {
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
		outMessage = new Message(MessageType.REQCPOR, ((Waiter) Thread.currentThread()).getWaiterState());

		com.writeObject(outMessage);
		inMessage = (Message) com.readObject();

		if ((inMessage.getMsgType() != MessageType.CPORDONE)) {
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
