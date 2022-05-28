package clientSide.entities;

/**
 * Chef cloning.
 *
 * It specifies his own attributes. Implementation of a client-server model of
 * type 2 (server replication). Communication is based on a communication
 * channel under the TCP protocol.
 */
public interface ChefCloning {

	/**
	 * Set chef id
	 * 
	 * @param id
	 */

	public void setChefId(int id);

	/**
	 * Get chef id
	 * 
	 * @return chef id
	 */
	public int getChefId();

	/**
	 * Set chef state
	 * 
	 * @param state new chef state
	 */
	public void setChefState(int state);

	/**
	 * Get chef state
	 * 
	 * @return chef state
	 */
	public int getChefState();
}
