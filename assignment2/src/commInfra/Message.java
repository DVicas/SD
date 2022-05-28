package commInfra;

import java.io.Serializable;

/**
 * Internal structure of the exchanged messages.
 *
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */

public class Message implements Serializable {

	/**
	 * Serialization key.
	 */
	private static final long serialVersionUID = 2022L;

	/**
	 * Name of the logging file
	 */
	private String logFileName;

	/**
	 * Message type
	 */

	private int msgType = -1;

	/**
	 * Chef state
	 */
	private int chefState = -1;

	/**
	 * Waiter state
	 */
	private int waiterState = -1;

	/**
	 * Student identification
	 */
	private int studentId = -1;

	/**
	 * Student state
	 */
	private int studentState = -1;

	/**
	 * Number of iterations
	 */

	private int nIter = -1;

	private int var1 = -1;

	private int var2 = -1;

	private Boolean check = null;

	private Request request = null;

	public Message(int type) {
		this.msgType = type;
	}

	public Message(int type, int state) {
		this.msgType = type;
		if (type >= 40 && type <= 59)
			this.waiterState = state;
		else if (type >= 60 && type <= 75)
			this.chefState = state;
		else if (type > 86) {
			this.waiterState = state;
			this.chefState = state;
		}
	}

	public Message(int type, Boolean check, int state) {
		this.msgType = type;
		this.check = check;
		if (type >= 40 && type <= 59)
			this.waiterState = state;
		else if (type >= 60 && type <= 75)
			this.chefState = state;
		else if (type > 86) {
			this.waiterState = state;
			this.chefState = state;
		}

	}

	public Message(int type, Request request, int state) {
		this.msgType = type;
		this.request = request;
		if (type >= 40 && type <= 59)
			this.waiterState = state;
	}

	public Message(int type, int studentId, int state) {
		this.msgType = type;
		this.studentId = studentId;
		if (type >= 0 && type <= 39)
			this.studentState = state;
		else if (type >= 40 && type <= 59)
			this.waiterState = state;
	}

	public Message(int type, Boolean check, int studentId, int state) {
		this.msgType = type;
		this.studentId = studentId;
		if (type >= 0 && type <= 39)
			this.studentState = state;
		else if (type >= 40 && type <= 59)
			this.waiterState = state;
		this.check = check;
	}

	public Message(int type, int var1, String fileName) {
		this.msgType = type;
		this.var1 = var1;
	}

	public Message(int type, int var1, int var2, String fileName) {
		this.msgType = type;
		this.var1 = var1;
		this.var2 = var2;
	}

	public Message(int type, String fileName, int nIter) {
		this.msgType = type;
		this.logFileName = fileName;
		this.nIter = nIter;
	}

	public int getMsgType() {
		return msgType;
	}

	public Boolean getCheck() {
		return check;
	}

	public char getRequest() {
		return request.type;
	}

	public int getStudentId() {
		return studentId;
	}

	public int getStudentState() {
		return studentState;
	}

	public int getWaiterState() {
		return waiterState;
	}

	public int getChefState() {
		return chefState;
	}

	public int getVar1() {
		return var1;
	}

	public int getVar2() {
		return var2;
	}

	public int getNIter() {
		return nIter;
	}

	public String getLogFileName() {
		return logFileName;
	}

	@Override
	public String toString() {
		return ("Message type: " + msgType + "\nCheck: " + check + "\nRequest: " + request + "\nStudent ID: "
				+ studentId + "\nStudent State: " + studentState + "\nWaiter State: " + waiterState + "\nChef State: "
				+ chefState + "\nVar 1: " + var1 + "\nVar 2: " + var2);
	}

}
