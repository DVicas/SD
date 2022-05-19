package entities;

import sharedRegions.*;

public class Waiter extends Thread {
	
	private int waiterState;
	
	private Table table;
	private Kitchen kitchen;
	private Bar bar;
	
	public Waiter(String name, Table table, Kitchen kitchen, Bar bar) {
		super(name);
		this.table = table;
		this.kitchen = kitchen;
		this.bar = bar;
		this.waiterState = WaiterStates.APPRAISING_SITUATION;
	}
	
	public int getWaiterState() { return waiterState; }
			
	public void setWaiterState(int waiterState) { this.waiterState = waiterState; } 
	
	@Override
	public void run() {
		
		boolean goodbye = false;
		char status;

		do {
			
			status = bar.lookAround();
			
			switch(status) {
				
				//enter the bar
				case 'c':
					table.saluteTheClient(bar.getStudentBeingAnswered());
					table.returnToBar();
					break;
					
				//call the waiter
				case 'w':
					table.getThePad();
					kitchen.handNoteToChef();
					kitchen.returnToBar();
					break;
					
				//alert the waiter
				case 'a':
					do {
						kitchen.collectPortion();
						table.deliverPortion();
					} while(table.haveAllClientsBeenServed());
					
					table.returnToBar();
					break;
					
				//should have arrived earlier
				case 'e':
					bar.prepareTheBill();
					table.presentTheBill();
					table.returnToBar();
					break;
				
				// goodbyes
				case 'g':
					goodbye = bar.sayGoodbye();				//tbd
					break;
			}
			
		} while(!goodbye);
	}
}
