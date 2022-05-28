package clientSide.main;

import clientSide.entities.Student;
import clientSide.stubs.*;
import serverSide.main.SimulPar;
import genclass.GenericIO;

/**
 * Client side of the Assignment 2 - Student. Static solution Attempt (number of
 * threads controlled by global constants - ExecConst) Implementation of a
 * client-server model of type 2 (server replication). Communication is based on
 * a communication channel under the TCP protocol.
 */
public class ClientStudentMain {

	/**
	 * Main method.
	 *
	 * @param args runtime arguments
	 */

	public static void main(String[] args) {
		String barServerHostName; // name of the platform where is located the barber shop server
		int barServerPortNum = -1; // port number for listening to service requests
		String tableServerHostName;
		int tableServerPortNum = -1;
		String reposServerName;
		int reposServerPortNum = -1;
		BarStub bar; // remote reference to the bar
		TableStub table;
		GeneralReposStub reposStub;
		Student[] students = new Student[SimulPar.N];

		/* getting problem runtime parameters */

		if (args.length != 6) {
			GenericIO.writelnString("Wrong number of parameters!");
			System.exit(1);
		}
		barServerHostName = args[0];
		try {
			barServerPortNum = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString("args[1] is not a number!");
			System.exit(1);
		}
		if ((barServerPortNum < 4000) || (barServerPortNum >= 65536)) {
			GenericIO.writelnString("args[1] is not a valid port number!");
			System.exit(1);
		}

		tableServerHostName = args[2];
		try {
			tableServerPortNum = Integer.parseInt(args[3]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString("args[3] is not a valid port number!");
			System.exit(1);
		}
		if ((tableServerPortNum < 4000) || (tableServerPortNum >= 65536)) {
			GenericIO.writelnString("args[3] is not a valid port number!");
			System.exit(1);
		}
		
		reposServerName = args[4];
	    try {
	    	reposServerPortNum = Integer.parseInt(args[5]);
	    } catch(NumberFormatException e) {
	    	GenericIO.writelnString("args[3] is not a valid port number!");
	    	System.exit(1);
	    }
	    if ((reposServerPortNum < 4000) || (reposServerPortNum >= 65536)) {
		    GenericIO.writelnString("args[3] is not a valid port number!");
		    System.exit (1);
		}

		// Initialization

		bar = new BarStub(barServerHostName, barServerPortNum);
		table = new TableStub(tableServerHostName, tableServerPortNum);
		reposStub = new GeneralReposStub(reposServerName, reposServerPortNum);

		for (int i = 0; i < SimulPar.N; i++) {
			students[i] = new Student("Student_" + i, i, table, bar);
		}

		// Start of simulation
		for (int i = 0; i < SimulPar.N; i++) {
			students[i].start();
			GenericIO.writelnString("Student thread " + i + " Started");
		}

		for (int i = 0; i < SimulPar.N; i++) {
			try {
				students[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		bar.shutdown();
		table.shutdown();
		reposStub.shutdown();
	}
}
