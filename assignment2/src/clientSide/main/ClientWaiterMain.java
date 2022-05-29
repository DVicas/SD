package clientSide.main;

import clientSide.entities.Waiter;
import clientSide.stubs.*;
import genclass.GenericIO;

/**
 *
 * 
 */
public class ClientWaiterMain {
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
		String kitchenServerHostName; // name of the platform where is located the kitchen server
		int kitchenServerPortNum = -1; // port number for listening to service requests
		String genReposServerHostName;
		int genReposServerPortNum = -1;

		BarStub barStub; // remote reference to the bar
		TableStub tableStub;
		KitchenStub kitchenStub;
		GeneralReposStub repoStub; // remote reference to the general repository

		String fileName;

		Waiter waiter; // waiter thread

		/* getting problem runtime parameters */

		if (args.length != 9) {
			GenericIO.writelnString("Wrong number of parameters!");
			System.exit(1);
		}

		tableServerHostName = args[0];
		try {
			tableServerPortNum = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString("args[1] is not a valid port number!");
			System.exit(1);
		}
		if ((tableServerPortNum < 4000) || (tableServerPortNum >= 65536)) {
			GenericIO.writelnString("args[1] is not a valid port number!");
			System.exit(1);
		}

		barServerHostName = args[2];
		try {
			barServerPortNum = Integer.parseInt(args[3]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString("args[1] is not a number!");
			System.exit(1);
		}
		if ((barServerPortNum < 4000) || (barServerPortNum >= 65536)) {
			GenericIO.writelnString("args[1] is not a valid port number!");
			System.exit(1);
		}

		kitchenServerHostName = args[4];
		try {
			kitchenServerPortNum = Integer.parseInt(args[5]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString("args[5] is not a valid port number!");
			System.exit(1);
		}
		if ((kitchenServerPortNum < 4000) || (kitchenServerPortNum >= 65536)) {
			GenericIO.writelnString("args[5] is not a valid port number!");
			System.exit(1);
		}

		genReposServerHostName = args[6];
		try {
			genReposServerPortNum = Integer.parseInt(args[7]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString("args[3] is not a number!");
			System.exit(1);
		}
		if ((genReposServerPortNum < 4000) || (genReposServerPortNum >= 65536)) {
			GenericIO.writelnString("args[3] is not a valid port number!");
			System.exit(1);
		}

		fileName = args[8];

		// Initialization
		
		barStub = new BarStub(barServerHostName, barServerPortNum);
		tableStub = new TableStub(tableServerHostName, tableServerPortNum);
		kitchenStub = new KitchenStub(kitchenServerHostName, kitchenServerPortNum);
		repoStub = new GeneralReposStub(genReposServerHostName, genReposServerPortNum);
//		repoStub.initSimulation(fileName, 10);

		waiter = new Waiter("Waiter", tableStub, kitchenStub, barStub);

		// Start of simulation
		waiter.start();
		GenericIO.writelnString("Waiter thread" + Thread.currentThread().getName() + " Started");

		try {
			waiter.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		barStub.shutdown();
		tableStub.shutdown();
		repoStub.shutdown();

	}
}
