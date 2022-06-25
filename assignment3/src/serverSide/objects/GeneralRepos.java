package serverSide.objects;

import java.util.Objects;
import serverSide.main.*;
import genclass.GenericIO;
import genclass.TextFile;
import interfaces.GeneralReposInterface;
import clientSide.entities.*;
import java.rmi.*;

/**
 * General Repository
 *
 * It is responsible to keep the visible internal state of the problem and to
 * provide means for it to be printed in the logging file. It is implemented as
 * an implicit monitor. All public methods are executed in mutual exclusion.
 * There are no internal synchronisation points.
 */

public class GeneralRepos implements GeneralReposInterface {
	/**
	 * Name of the logging file.
	 */

	private final String logFileName;

	/**
	 * State of the Chef
	 */

	private int chefState;

	/**
	 * State of the Waiter
	 */

	private int waiterState;

	/**
	 * State of the Chef
	 */

	private int[] studentState;

	/**
	 * Number of courses delivered (not sure)
	 */

	private int nCourses;

	/**
	 * Number of Portions delivered (not sure)
	 */

	private int nPortions;

	/**
	 * Seats at the table
	 */
	private int[] seatsAtTable;

	/**
	 * Number of entities that requested shutdown
	 */
	private int nEntities;

	/**
	 * Instantiation of a general repository object.
	 */

	public GeneralRepos() {
		this("");
	}

	/**
	 * Instantiation of a general repository object.
	 *
	 * @param logFileName name of the logging file
	 */

	public GeneralRepos(String logFileName) {
		nEntities = 0;
		if ((logFileName == null) || Objects.equals(logFileName, ""))
			this.logFileName = "logger";
		else
			this.logFileName = logFileName;
		chefState = ChefStates.WAITING_FOR_AN_ORDER;
		waiterState = WaiterStates.APPRAISING_SITUATION;
		studentState = new int[SimulPar.N];
		for (int i = 0; i < SimulPar.N; i++)
			studentState[i] = StudentStates.GOING_TO_THE_RESTAURANT;
		nCourses = 0;
		nPortions = 0;
		seatsAtTable = new int[SimulPar.N];
		for (int i = 0; i < SimulPar.N; i++) {
			seatsAtTable[i] = -1;
		}
		reportInitialStatus();
	}

	/**
	 * Write the header to the logging file.
	 *
	 * The chef and the waiter are sleeping and the students are going to the
	 * restaurant.
	 */

	private void reportInitialStatus() {
		TextFile log = new TextFile(); // instantiation of a text file handler

		if (!log.openForWriting(".", logFileName)) {
			GenericIO.writelnString("The operation of creating the file " + logFileName + " failed!");
			System.exit(1);
		}
		log.writelnString("\t\t\t\t\t\t  The Restaurant - Description of the internal state");
		log.writelnString(
				"\n\tChef\tWaiter\tStu0\tStu1\tStu2\tStu3\tStu4\tStu5\tStu6\tNCourse\tNPortion\t\t\t\t\t\t\tTable\n");
		log.writelnString(
				"\tState\tState\tState\tState\tState\tState\tState\tState\tState\t\t\t\t\t  Seat0\t  Seat1\t  Seat2\t  Seat3\t  Seat4\t  Seat5\t  Seat6\n");
		if (!log.close()) {
			GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
			System.exit(1);
		}
	}

	/**
	 * Write a state line at the end of the logging file.
	 *
	 * The current state of the barbers and the customers is organized in a line to
	 * be printed. Internal operation.
	 */

	private void reportStatus() {
		TextFile log = new TextFile(); // instantiation of a text file handler
		String line = ""; // state line to be printed
		if (!log.openForAppending(".", logFileName)) {
			GenericIO.writelnString("The operation of opening for appending the file " + logFileName + " failed!");
			System.exit(1);
		}
		switch (chefState) {
		case ChefStates.WAITING_FOR_AN_ORDER:
			line += "\tWAFOR";
			break;
		case ChefStates.PREPARING_THE_COURSE:
			line += "\tPRPCS";
			break;
		case ChefStates.DISHING_THE_PORTIONS:
			line += "\tDSHPT";
			break;
		case ChefStates.DELIVERING_THE_PORTIONS:
			line += "\tDLVPT";
			break;
		case ChefStates.CLOSING_SERVICE:
			line += "\tCLSSV";
			break;
		}

		switch (waiterState) {
		case WaiterStates.APPRAISING_SITUATION:
			line += "\tAPPST";
			break;
		case WaiterStates.PRESENTING_THE_MENU:
			line += "\tPRSMN";
			break;
		case WaiterStates.TAKING_THE_ORDER:
			line += "\tTKODR";
			break;
		case WaiterStates.PLACING_THE_ORDER:
			line += "\tPCODR";
			break;
		case WaiterStates.WAITING_FOR_PORTION:
			line += "\tWTFPT";
			break;
		case WaiterStates.PROCESSING_THE_BILL:
			line += "\tPRCBL";
			break;
		case WaiterStates.RECEIVING_PAYMENT:
			line += "\tRECPM";
			break;
		}

		for (int i = 0; i < SimulPar.N; i++) {
			switch (studentState[i]) {
			case StudentStates.GOING_TO_THE_RESTAURANT:
				line += "\tGGTRT";
				break;
			case StudentStates.TAKING_A_SEAT_AT_THE_TABLE:
				line += "\tTKSTT";
				break;
			case StudentStates.SELECTING_THE_COURSES:
				line += "\tSELCS";
				break;
			case StudentStates.ORGANIZING_THE_ORDER:
				line += "\tOGODR";
				break;
			case StudentStates.CHATTING_WITH_COMPANIONS:
				line += "\tCHTWC";
				break;
			case StudentStates.PAYING_THE_MEAL:
				line += "\tPYTML";
				break;
			case StudentStates.ENJOYING_THE_MEAL:
				line += "\tEJTML";
				break;
			case StudentStates.GOING_HOME:
				line += "\tGGHOM";
				break;
			}
		}

		line += "\t\t" + nCourses + "\t\t" + nPortions;

		for (int i = 0; i < SimulPar.N; i++) {
			line += "\t\t";
			if (seatsAtTable[i] == -1) {
				line += "-";
			} else {
				line += seatsAtTable[i];
			}
		}

		log.writelnString(line);
		if (!log.close()) {
			GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
			System.exit(1);
		}
	}


	/**
	 * Write in the logging file the new chef state
	 * 
	 * @param value chef state to set
	 */
    @Override
	public synchronized void setChefState(int value) throws RemoteException{
		this.chefState = value;
		reportStatus();
	}

	/**
	 * Write in the logging file the new waiter state
	 * 
	 * @param value waiter state to set
	 */
    @Override
	public synchronized void setWaiterState(int value) throws RemoteException
    {
		this.waiterState = value;
		reportStatus();
	}

	/**
	 * Write in the logging file the updated student state
	 * 
	 * @param id    student id
	 * @param value student state to set
	 */
    @Override
	public synchronized void setStudentState(int id, int value) throws RemoteException {
		this.studentState[id] = value;
		reportStatus();
	}

	/**
	 * Update student state
	 * 
	 * @param id    student id
	 * @param value student state to set
	 * @param hold  specifies if prints line of report status
	 */
    @Override
	public synchronized void setStudentState(int id, int value, boolean hold) throws RemoteException{
		this.studentState[id] = value;
	}

	/**
	 * Set variable nCourses and report status in the logging file
	 * 
	 * @param value nCourses value to set
	 */
    @Override
	public synchronized void setnCourses(int value) throws RemoteException{
		this.nCourses = value;
	}

	/**
	 * Write the portion value in the logging file
	 * 
	 * @param value nPortions value to set
	 */
    @Override
	public synchronized void setnPortions(int value) throws RemoteException {
		this.nPortions = value;
	}

	/**
	 * Write to the logging file the updated seats values at the table
	 * 
	 * @param id   seat at the table
	 * @param seat student id to sit
	 */
    @Override
	public synchronized void updateSeatsAtTable(int id, int seat) throws RemoteException{
		this.seatsAtTable[id] = seat;
		reportStatus();
	}

//	/**
//	 * Update the leaving of a student in the seats of the table
//	 * 
//	 * @param id student id to leave table
//	 */
//	public synchronized void updateSeatsAtLeaving(int id) {
//		int seat = 0;
//
//		for (int i = 0; i < this.seatsAtTable.length; i++) {
//			if (this.seatsAtTable[i] == id)
//				seat = i;
//		}
//
//		this.seatsAtTable[seat] = -1;
//	}

	/**
	 * Operation Table server shutdown
	 */
	@Override
    public synchronized void shutdown() throws RemoteException {
		nEntities += 1;
		if (nEntities >= SimulPar.E) {
			ServerRestaurantGeneralRepos.shutdown();
		}
		notifyAll();
	}
}

