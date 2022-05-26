package clientSide.entities;

/**
 *    Student cloning.
 *
 *      It specifies his own attributes.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on a communication channel under the TCP protocol.
 */
public interface StudentCloning {

	/**
	 * Set Student Cloning
	 * 
	 * @param id
	 */
	
	public void setStudentId(int id);
	
	/**
	 * Get Student id
	 * 
	 * @return student id
	 */
	public int getStudentId();
	
	/**
	 * Set Student state
	 * 
	 * @param state new student state
	 */
	public void setStudentState(int state);
	
	/**
	 * Get Student state
	 * 
	 * @return student state
	 */
	public int getStudentState();
}
