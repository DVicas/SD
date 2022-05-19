package entities;

import sharedRegions.*;

public class Chef extends Thread {
	
	private int chefState;
	
	private Kitchen kitchen;
	private Bar bar;
	
	
	public Chef(String name, Kitchen kitchen, Bar bar) {
		super(name);
		this.kitchen = kitchen;
		this.bar = bar;
		chefState = ChefStates.WAITING_FOR_AN_ORDER;
	}
	
	public int getChefState() { return chefState; }
	
 	public void setChefState(int chefState) { this.chefState = chefState; } 
	
 	@Override
	public void run() {
		
		kitchen.watchTheNews();
		kitchen.startPreparation();
		
		boolean firstCourse = true;
		do {
			if(!firstCourse) {
				kitchen.continuePreparation();
			} else {
				firstCourse = false;
			}
			
			kitchen.proceedToPresentation();
			bar.alertTheWaiter();
			
			while(!kitchen.haveAllPortionsBeenDelivered()) {
				kitchen.haveNextPortionReady();
				bar.alertTheWaiter();
			}
			
		} while(!kitchen.hasTheOrderBeenCompleted());
		
		kitchen.cleanUp();
	}
}
