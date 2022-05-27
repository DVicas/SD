package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

public class BarStub {
	
	/**
	 * Name of the platform where is located the bar Server
	 */
	private String serverHostName;
	
	/**
	 * Port number for listening to service requests
	 */
	private int serverPortNum;
	
	
	/**
	 * Instantiation of a stub to the Bar
	 * @param serverHostName name of the platform where is located the bar Server
	 * @param serverPortNum port number for listening to service requests
	 */
	public BarStub(String serverHostName, int serverPortNum) {
		this.serverHostName = serverHostName;
		this.serverPortNum  = serverPortNum;
	}
	
	
	public void enter() {
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
	    outMessage = new Message(MessageType.REQENT, ((Student) Thread.currentThread()).getStudentId(), ((Student) Thread.currentThread()).getStudentState());
	    
	    com.writeObject(outMessage);
	    inMessage = (Message) com.readObject();
	    
	    //TODO Message Types - enter
	    if((inMessage.getMsgType() != MessageType.ENTDONE)) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Message Type!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    if(inMessage.getStudentId() != ((Student) Thread.currentThread()).getStudentId()) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Student ID!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    if((inMessage.getStudentState() != StudentStates.GOING_TO_THE_RESTAURANT)) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Student State!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    ((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
	    com.close();
		
		//TODO table.seatAtTable
	}
	
	public int callWaiter() {
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
	    outMessage = new Message(MessageType.REQCW, ((Student) Thread.currentThread()).getStudentId(), ((Student) Thread.currentThread()).getStudentState());
	    
	    com.writeObject(outMessage);
	    inMessage = (Message) com.readObject();
	    
	    //TODO Message Types - enter
	    if((inMessage.getMsgType() != MessageType.CWDONE)) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Message Type!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    if(inMessage.getStudentId() != ((Student) Thread.currentThread()).getStudentId()) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Student ID!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    ((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
	    
	    return 0;
//        return inMessage.getWaiterID();   // rever porque só ha 1 waiter
	}
	
	public void signalWaiter() {
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
	    outMessage = new Message(MessageType.REQSW, ((Student) Thread.currentThread()).getStudentId(), ((Student) Thread.currentThread()).getStudentState());
	    
	    com.writeObject(outMessage);
	    inMessage = (Message) com.readObject();
	    
	    //TODO Message Types - enter
	    if((inMessage.getMsgType() != MessageType.SWDONE)) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Message Type!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    if(inMessage.getStudentId() != ((Student) Thread.currentThread()).getStudentId()) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Student ID!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    ((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
	    com.close();
	}
	
	public void exit() {
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
	    outMessage = new Message(MessageType.REQEXIT, ((Student) Thread.currentThread()).getStudentId(), ((Student) Thread.currentThread()).getStudentState());
	    
	    com.writeObject(outMessage);
	    inMessage = (Message) com.readObject();
	    
	    //TODO Message Types - enter
	    if((inMessage.getMsgType() != MessageType.EXITDONE)) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Message Type!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    if(inMessage.getStudentId() != ((Student) Thread.currentThread()).getStudentId()) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Student ID!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    if((inMessage.getStudentState() != StudentStates.GOING_HOME)) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Student State!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    ((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
	    com.close();
	}
	
	public boolean lookAround() {
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
	    outMessage = new Message(MessageType.REQLA, ((Waiter) Thread.currentThread()).getWaiterState());
	    
	    com.writeObject(outMessage);
	    inMessage = (Message) com.readObject();
	    
	    //TODO Message Types - enter
	    if((inMessage.getMsgType() != MessageType.LADONE)) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Message Type!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    //if((inMessage.getWaiterState() < WaiterStates.APPRAISING_SITUATION && inMessage.getWaiterState() > WaiterStates.RECEIVING_PAYMENT)) {
	    if(inMessage.getWaiterState() != WaiterStates.APPRAISING_SITUATION) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Waiter State!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    ((Waiter) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
	    com.close();
            
        return inMessage.getRequest();
	}
	
	public boolean sayGoodbye() {
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
	    outMessage = new Message(MessageType.REQSG, ((Waiter) Thread.currentThread()).getWaiterState());
	    
	    com.writeObject(outMessage);
	    inMessage = (Message) com.readObject();
	    
	    //TODO Message Types - enter
	    if((inMessage.getMsgType() != MessageType.SGDONE)) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Message Type!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    ((Waiter) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
	    com.close();
	    
	    return (inMessage.getMsgType() == MessageType.SGDONE);
	}
	
	public void preprareTheBill() {
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
	    outMessage = new Message(MessageType.REQPB, ((Waiter) Thread.currentThread()).getWaiterState());
	    
	    com.writeObject(outMessage);
	    inMessage = (Message) com.readObject();
	    
	    //TODO Message Types - enter
	    if((inMessage.getMsgType() != MessageType.PBDONE)) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Message Type!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    if((inMessage.getWaiterState() != WaiterStates.PROCESSING_THE_BILL)) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Waiter State!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    ((Waiter) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
	    com.close();
	}
	
	
	public boolean alertTheWaiter() {
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
	    outMessage = new Message(MessageType.REQAL, ((Chef) Thread.currentThread()).getChefState());
	    
	    com.writeObject(outMessage);
	    inMessage = (Message) com.readObject();
	    
	    //TODO Message Types - enter
	    if((inMessage.getMsgType() != MessageType.ALDONE)) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Message Type!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    if((inMessage.getChefState() != ChefStates.DELIVERING_THE_PORTIONS)) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Chef State!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    ((Chef) Thread.currentThread()).setChefState(inMessage.getChefState());
	    com.close();
            
            return (inMessage.getMsgType() == MessageType.ALDONE);
	}
	
	public int getStudentBeingAnswered() {
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
	    outMessage = new Message(MessageType.REQGSBA, ((Waiter) Thread.currentThread()).getWaiterState());
	    
	    com.writeObject(outMessage);
	    inMessage = (Message) com.readObject();
	    
	    //TODO Message Types - enter
	    if((inMessage.getMsgType() != MessageType.GSBADONE)) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid Message Type!");
	    	GenericIO.writelnString(inMessage.toString());
	    	System.exit(1);
	    }
	    
	    ((Waiter) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
	    com.close();
	    
	    return inMessage.getStudentId();
	}
	
	
	/**
	 *   Operation server shutdown.
	 *
	 *   New operation.
	 */
	public void shutdown(){
		ClientCom com;                                                 // communication channel
		Message outMessage,                                            // outgoing message
				inMessage;                                             // incoming message
	
		com = new ClientCom(serverHostName, serverPortNum);
		while (!com.open ()) {
			try {
				Thread.sleep((long) (1000));
	        }
	        catch (InterruptedException e) {}
		}
		
		//MESSAGES
	    outMessage = new Message(MessageType.SHUT);
	    
	    com.writeObject(outMessage);
	    inMessage = (Message) com.readObject();
	    
	    if (inMessage.getMsgType() != MessageType.SHUTDONE) {
	    	GenericIO.writelnString("Thread "+Thread.currentThread().getName()+": Invalid message type!");
	        GenericIO.writelnString(inMessage.toString());
	        System.exit(1);
	    }
	    com.close();
	}
}
