package clientSide.entities;

import clientSide.stubs.*;

public class Waiter extends Thread {
	
	private int waiterState;
	
	private TableStub tableStub;
	private KitchenStub kitchenStub;
	private BarStub barStub;
	
	public Waiter(String name, TableStub table, KitchenStub kitchen, BarStub bar) {
		super(name);
		this.tableStub = table;
		this.kitchenStub = kitchen;
		this.barStub = bar;
		this.waiterState = WaiterStates.APPRAISING_SITUATION;
	}
	
	public int getWaiterState() { return waiterState; }
			
	public void setWaiterState(int waiterState) { this.waiterState = waiterState; } 
	
	@Override
	public void run() {
		
		boolean goodbye = false;
		char status;

		do {
			status = barStub.lookAround();
			
			switch(status) {
				
				//enter the bar
				case 'c':
					tableStub.saluteTheClient(barStub.getStudentBeingAnswered());
					tableStub.returnToBar();
					break;
					
				//call the waiter
				case 'w':
					tableStub.getThePad();
					kitchenStub.handNoteToChef();
					kitchenStub.returnToBar();
					break;
					
				//alert the waiter
				case 'a':
					while(!tableStub.haveAllClientsBeenServed()) {	
						
						kitchenStub.collectPortion();
						tableStub.deliverPortion();
					}
					tableStub.returnToBar();
					break;
					
				//should have arrived earlier
				case 'e':
					barStub.preprareTheBill();
					tableStub.presentTheBill();
					tableStub.returnToBar();
					break;
				
				// goodbyes
				case 'g':
					goodbye = barStub.sayGoodbye();	
					break;
			}
			
		} while(!goodbye);
	}
}
