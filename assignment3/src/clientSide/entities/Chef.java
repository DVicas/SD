package clientSide.entities;

import java.rmi.*;
import interfaces.*;
import genclass.GenericIO;

/**
 * Chef thread.
 *
 * It simulates the chef life cycle. Implementation of a client-server model of
 * type 2 (server replication). Communication is based on a communication
 * channel under the TCP protocol.
 */

public class Chef extends Thread {

	/**
	 * Chef state
	 */

	private int chefState;

	/**
	 * Reference to the stub of the kitchen.
	 */

	private final BarInterface barStub;

	/**
	 * Reference to the stub of the kitchen.
	 */

	private final KitchenInterface kitchenStub;

	/**
	 * Instantiation of the chef thread
	 * 
	 * @param name    thread name
	 * @param kitchen reference to the kitchen
	 * @param bar     reference to the bar stub
	 */

	public Chef(String name, KitchenInterface kitchen, BarInterface bar) {
		super(name);
		this.kitchenStub = kitchen;
		this.barStub = bar;
		chefState = ChefStates.WAITING_FOR_AN_ORDER;
	}

	/**
	 * Get the chef current state
	 * 
	 * @return chef state
	 */

	public int getChefState() {
		return chefState;
	}

	/**
	 * Set the chef state
	 */

	public void setChefState(int chefState) {
		this.chefState = chefState;
	}

	/**
	 * Life cycle of the chef
	 */

	@Override
	public void run() {

		watchTheNews();
		startPreparation();

		boolean firstCourse = true;
		do {
			if (!firstCourse) {
				continuePreparation();
			} else {
				firstCourse = false;
			}

			proceedToPresentation();

			alertTheWaiter();
//			System.out.println("portion ready");

			while (!haveAllPortionsBeenDelivered()) {
				haveNextPortionReady();
			}

		} while (!hasTheOrderBeenCompleted());

		cleanUp();
	}
	
	private void watchTheNews() {
		try {
			kitchenStub.watchTheNews();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void startPreparation() {
		try {
			kitchenStub.startPreparation();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void continuePreparation() {
		try {
			kitchenStub.continuePreparation();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void proceedToPresentation() {
		try {
			kitchenStub.proceedToPreparation();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private boolean haveAllPortionsBeenDelivered() {
		ReturnBoolean ret = null;
		try {
			ret = kitchenStub.haveAllPortionsBeenDelivered();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ret.getBooleanVal();
	}
	
	private void haveNextPortionReady() {
		try {
			kitchenStub.haveNextPortionReady();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private boolean hasTheOrderBeenCompleted() {
		ReturnBoolean ret = null;
		try {
			ret = kitchenStub.hasTheOrderBeenCompleted();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return ret.getBooleanVal();
	}
	
	private void cleanUp() {
		try {
			kitchenStub.cleanUp();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void alertTheWaiter() {
		try {
			barStub.alertTheWaiter();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
