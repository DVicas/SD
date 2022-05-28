package clientSide.entities;

/**
 * Waiter cloning.
 *
 * It specifies his own attributes. Implementation of a client-server model of
 * type 2 (server replication). Communication is based on a communication
 * channel under the TCP protocol.
 */

public interface WaiterCloning {

	/**
	 * Set waiter id
	 * 
	 * @param id waiter id
	 */
	public void setWaiterId(int id);

	/**
	 * Get waiter id
	 * 
	 * @return waiter id
	 */
	public int getWaiterId();

	/**
	 * Set Waiter state
	 * 
	 * @param state new waiter state
	 */
	public void setWaiterState(int state);

	/**
	 * Get Waiter state
	 * 
	 * @return waiter state
	 */
	public int getWaiterState();
}
