package clientSide.main;

import clientSide.entities.Student;
import clientSide.stubs.BarStub;
import clientSide.stubs.KitchenStub;
import clientSide.stubs.TableStub;
import serverSide.main.SimulPar;

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
		Student[] student = new Student[SimulPar.N]; // array of references to the Students Threads

		BarStub barStub; // Reference to the Bar
		KitchenStub kitchenStub; // Reference to the Kitchen
		TableStub tableStub; // Reference to the Table

		barStub = new BarStub(" ", 22150);
		kitchenStub = new KitchenStub(" ", 22151);
		tableStub = new TableStub(" ", 22152);

		for (int i = 0; i < SimulPar.N; i++)
			student[i] = new Student("Student_" + (i + 1), i, tableStub, barStub);

		/* start threads */
		for (int i = 0; i < SimulPar.N; i++)
			student[i].start();

		/* wait for the end */
		for (int i = 0; i < SimulPar.N; i++) {
			try {
				student[i].join();
			} catch (InterruptedException e) {
			}
			System.out.println("The Student " + (i + 1) + " just terminated");
		}

		System.out.println("End of the Simulation");

	}

}
