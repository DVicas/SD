package clientSide.entities;

import clientSide.stubs.*;

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

	private final BarStub barStub;

	/**
	 * Reference to the stub of the kitchen.
	 */

	private final KitchenStub kitchenStub;

	/**
	 * Instantiation of the chef thread
	 * 
	 * @param name    thread name
	 * @param kitchen reference to the kitchen
	 * @param bar     reference to the bar stub
	 */

	public Chef(String name, KitchenStub kitchen, BarStub bar) {
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
	 * Lifecycle of the chef
	 */

	@Override
	public void run() {

		kitchenStub.watchTheNews();

		kitchenStub.startPreparation();

		boolean firstCourse = true;
		do {
			if (!firstCourse) {
				kitchenStub.continuePreparation();
			} else {
				firstCourse = false;
			}

			kitchenStub.proceedToPresentation();

			barStub.alertTheWaiter();
			System.out.println("portion ready");

			while (!kitchenStub.haveAllPortionsBeenDelivered()) {
				kitchenStub.haveNextPortionReady();
			}

		} while (!kitchenStub.hasTheOrderBeenCompleted());

		kitchenStub.cleanUp();
	}
}
