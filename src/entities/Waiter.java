package entities;

public class Waiter extends Thread {
	
	
	private Table table;
	private Kitchen kitchen;
	private Bar bar;
	
//	private int 
			
	public void work() {
		int status;

		do {
			
			status = Bar.lookAround();
			
			switch(status) {
				
				//enter the bar
				case 'c':
					table.saluteTheClient();
					table.returnToBar();
					
				//call the waiter
				case 'w':
					table.getThePad();
					kitchen.handNoteToChef();
					kitchen.returnToBar();
					
				//alert the waiter
				case 'a':
					do {
						kitchen.collectPortion();
						table.deliverPortion();
					} while(table.haveAllClientsBeenServed());
					
					table.returnToBar();
					
				//should have arrived earlier
				case 'e':
					bar.prepareTheBill();
					table.presentTheBill();
					table.returnToBar();
			}
			
		} while();
	}
}
