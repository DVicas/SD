package clientSide.entities;

import clientSide.stubs.*;

/**
 *    Chef thread.
 *
 *      It simulates the chef life cycle.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on a communication channel under the TCP protocol.
 */

public class Chef extends Thread {
	
	private int chefState;
	
	private final BarStub barStub;
	private final KitchenStub kitchenStub;

	
	public Chef(String name, KitchenStub kitchen, BarStub bar) {
		super(name);
		this.kitchenStub = kitchen;
		this.barStub = bar;
		chefState = ChefStates.WAITING_FOR_AN_ORDER;
	}
	
	public int getChefState() { return chefState; }
	
 	public void setChefState(int chefState) { this.chefState = chefState; } 
	
 	@Override
	public void run() {
		
		kitchenStub.watchTheNews();
		
		kitchenStub.startPreparation();
		
		boolean firstCourse = true;
		do {
			if(!firstCourse) {
				kitchenStub.continuePreparation();
			} else {
				firstCourse = false;
			}
			
			kitchenStub.proceedToPresentation();

			barStub.alertTheWaiter();
			System.out.println("portion ready");
			
			while(!kitchenStub.haveAllPortionsBeenDelivered()) {
				kitchenStub.haveNextPortionReady();
			}

			
		} while(!kitchenStub.hasTheOrderBeenCompleted());
		
		kitchenStub.cleanUp();
	}
}
