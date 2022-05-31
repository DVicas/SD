package serverSide.sharedRegions;

import clientSide.entities.*;
import commInfra.*;
import serverSide.entities.GeneralReposClientProxy;

/**
 * Interface to the General Repository of Information.
 *
 * It is responsible to validate and process the incoming message, execute the
 * corresponding method on the General Repository and generate the outgoing
 * message. Implementation of a client-server model of type 2 (server
 * replication). Communication is based on a communication channel under the TCP
 * protocol.
 */

public class GeneralReposInterface {
	/**
	 * Reference to the general repository.
	 */

	private final GeneralRepos repos;

	/**
	 * Instantiation of an interface to the general repository.
	 *
	 * @param repos reference to the general repository
	 */

	public GeneralReposInterface(GeneralRepos repos) {
		this.repos = repos;
	}

	/**
	 * Processing of the incoming messages.
	 *
	 * Validation, execution of the corresponding method and generation of the
	 * outgoing message.
	 *
	 * @param inMessage service request
	 * @return service reply
	 * @throws MessageException if the incoming message is not valid
	 */

	public Message processAndReply(Message inMessage) throws MessageException {
		Message outMessage = null; // mensagem de resposta
		
		switch(inMessage.getMsgType())
		{
		// verify Chef state
		case MessageType.STCST:
			if (inMessage.getChefState() < ChefStates.WAITING_FOR_AN_ORDER || inMessage.getChefState() > ChefStates.CLOSING_SERVICE)
				throw new MessageException ("Invalid Chef state!", inMessage);
			break;
		// verify Waiter state
		case MessageType.STWST:
			if (inMessage.getWaiterState() < WaiterStates.APPRAISING_SITUATION || inMessage.getWaiterState() > WaiterStates.RECEIVING_PAYMENT)
				throw new MessageException("Invalid Waiter state!", inMessage);
			break;
		// verify Student state
		case MessageType.STSST:
		case MessageType.STSST2:
			if (inMessage.getStudentState() < StudentStates.GOING_TO_THE_RESTAURANT || inMessage.getStudentState() > StudentStates.GOING_HOME)
				throw new MessageException("Invalid Student state!", inMessage);
			break;
		// verify only message type
		case MessageType.STCOUR:
		case MessageType.STPOR:
		case MessageType.STUSAT:
		case MessageType.STUSATL:
		case MessageType.SHUT:
			break;
		default:
			throw new MessageException ("Invalid message type!", inMessage);
		}
		
		/* Processing of the incoming message */

		switch(inMessage.getMsgType())
		{
		case MessageType.STCST:
			repos.setChefState(inMessage.getChefState());
			outMessage = new Message(MessageType.CSTDONE);
			break;
		case MessageType.STWST:
			repos.setWaiterState(inMessage.getWaiterState());
			outMessage = new Message(MessageType.WSTDONE);
			break;
		case MessageType.STSST:
		case MessageType.STSST2:
			if (inMessage.getMsgType() == MessageType.SSTDONE) {
				repos.setStudentState(inMessage.getStudentId(), inMessage.getStudentState());
				outMessage = new Message(MessageType.SSTDONE);
				break;
			} else { 
				repos.setStudentState(inMessage.getStudentId(), inMessage.getStudentState(), inMessage.getHold());
				outMessage = new Message(MessageType.SSTDONE);
			}
			break;
		case MessageType.STCOUR:
			((GeneralReposClientProxy) Thread.currentThread()).setValue(inMessage.getNCourses());
			repos.setnCourses(((GeneralReposClientProxy) Thread.currentThread()).getValue());
			outMessage = new Message(MessageType.COURDONE);
			break;
		case MessageType.STPOR:
			((GeneralReposClientProxy) Thread.currentThread()).setValue(inMessage.getNPortions());
			repos.setnPortions(((GeneralReposClientProxy) Thread.currentThread()).getValue());
			outMessage = new Message(MessageType.PORDONE);
			break;
		case MessageType.STUSAT:
			repos.updateSeatsAtTable(inMessage.getSeatAtTable(), inMessage.getStudentId());
			outMessage = new Message(MessageType.SATDONE);
			break;
		case MessageType.STUSATL:
			repos.updateSeatsAtTable(inMessage.getStudentId(), -1);
			outMessage = new Message(MessageType.ATLDONE);
			break;
		case MessageType.SHUT:
			repos.shutdown();
			outMessage = new Message(MessageType.SHUTDONE);
			break;
		}
		return (outMessage);
	}

}