package serverSide.entities;

import serverSide.sharedRegions.*;
import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

/**
 * Service provider agent for access to the Table.
 *
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class TableClientProxy extends Thread implements StudentCloning, WaiterCloning, ChefCloning {

	/**
	 * Number of instantiated threads.
	 */

	private static int nProxy = 0;

	/**
	 * Communication channel.
	 */

	private ServerCom sconi;

	/**
	 * Interface to the Table.
	 */

	private TableInterface tableInter;

	/**
	 * Student identification.
	 */

	private int studentId;

	/**
	 * Student state.
	 */

	private int studentState;

	/**
	 * Chef state.
	 */

	private int chefState;

	/**
	 * Waiter state.
	 */

	private int waiterState;

	/**
	 * Instantiation of a client proxy.
	 *
	 * @param sconi      communication channel
	 * @param tableInter interface to the Table
	 */

	public TableClientProxy(ServerCom sconi, TableInterface tableInter) {
		super("TableProxy_" + TableClientProxy.getProxyId());
		this.sconi = sconi;
		this.tableInter = tableInter;
	}

	/**
	 * Generation of the instantiation identifier.
	 *
	 * @return instantiation identifier
	 */

	private static int getProxyId() {
		Class<?> cl = null; // representation of the TableProxy object in JVM
		int proxyId; // instantiation identifier

		try {
			cl = Class.forName("serverSide.entities.TableClientProxy");
		} catch (ClassNotFoundException e) {
			GenericIO.writelnString("Data type TableClientProxy was not found!");
			e.printStackTrace();
			System.exit(1);
		}
		synchronized (cl) {
			proxyId = nProxy;
			nProxy += 1;
		}
		return proxyId;
	}

	/**
	 * Life cycle of the service provider agent.
	 */

	@Override
	public void run() {
		Message inMessage = null, // service request
				outMessage = null; // service reply

		/* service providing */

		inMessage = (Message) sconi.readObject(); // get service request
		try {
			outMessage = tableInter.processAndReply(inMessage); // process it
		} catch (MessageException e) {
			GenericIO.writelnString("Thread " + getName() + ": " + e.getMessage() + "!");
			GenericIO.writelnString(e.getMessageVal().toString());
			System.exit(1);
		}
		sconi.writeObject(outMessage); // send service reply
		sconi.close(); // close the communication channel
	}

	/**
	 * Set student id.
	 *
	 * @param id student id
	 */

	public void setStudentId(int id) {
		studentId = id;
	}

	/**
	 * Get student id.
	 *
	 * @return student id
	 */

	public int getStudentId() {
		return studentId;
	}

	/**
	 * Set student state.
	 *
	 * @param state new student state
	 */

	public void setStudentState(int state) {
		studentState = state;
	}

	/**
	 * Get student state.
	 *
	 * @return student state
	 */

	public int getStudentState() {
		return studentState;
	}

	/**
	 * Set waiter state.
	 *
	 * @param state new waiter state
	 */

	public void setWaiterState(int state) {
		waiterState = state;
	}

	/**
	 * Get waiter state.
	 *
	 * @return waiter state
	 */

	public int getWaiterState() {
		return waiterState;
	}

	/**
	 * Set chef state.
	 *
	 * @param state new chef state
	 */

	public void setChefState(int state) {
		chefState = state;
	}

	/**
	 * Get chef state.
	 *
	 * @return chef state
	 */

	public int getChefState() {
		return chefState;
	}

	@Override
	public void setChefId(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getChefId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setWaiterId(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getWaiterId() {
		// TODO Auto-generated method stub
		return 0;
	}

		/**
	 * Set studentBeingAnswered Id
	 * 	@param id studentBeingAnswered ID
	 */
	public void setStudentBeingAnswered(int id) {	studentBeingAnswered = id; }

	/**
	 * Get studentBeingAnswered Id
	 *	@return id studentBeingAnswered
	 */
	public int getStudentBeingAnswered() { return studentBeingAnswered;	}

}