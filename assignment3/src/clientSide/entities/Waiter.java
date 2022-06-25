package clientSide.entities;

import java.rmi.*;
import interfaces.*;
import genclass.GenericIO;

public class Waiter extends Thread {
	
	/**
	 * Waiter state
	 */
	private int waiterState;

	/**
	 * Reference to table stub
	 */
	private TableInterface tableStub;
	
	/**
	 * Reference to kitchen stub
	 */
	private KitchenInterface kitchenStub;
	
	/**
	 * Reference to bar stub
	 */
	private BarInterface barStub;

	public Waiter(String name, TableInterface tableStub, KitchenInterface kitchenStub, BarInterface barStub) {
		super(name);
		this.tableStub = tableStub;
		this.kitchenStub = kitchenStub;
		this.barStub = barStub;
		this.waiterState = WaiterStates.APPRAISING_SITUATION;
	}
	
	
	/**
	 * 	Get waiter state
	 * 	@return waiter state
	 */
	public int getWaiterState() {
		return waiterState;
	}

	/**
	 * Set a new waiter state
	 * @param waiterState new state to be set
	 */
	public void setWaiterState(int waiterState) {
		this.waiterState = waiterState;
	}

	@Override
	public void run() {

		boolean goodbye = false;
		char status;

		do {
			status = lookAround();

			switch (status) {

			// enter the bar
			case 'c':
				saluteTheClient(getStudentBeingAnswered());
				returnToBar();
				break;

			// call the waiter
			case 'w':
				getThePad();
				handNoteToChef();
				returnToBar();
				break;

			// alert the waiter
			case 'a':
				while (!haveAllClientsBeenServed()) {

					collectPortion();
					deliverPortion();
				}
				returnToBar();
				break;

			// should have arrived earlier
			case 'e':
				prepareTheBill();
				presentTheBill();
				returnToBar();
				break;

			// goodbyes
			case 'g':
				goodbye = sayGoodbye();
				break;
			}

		} while (!goodbye);
	}
	
	// bar functions
	
	private char lookAround() {
		
	}
	
	private int getStudentBeingAnswered() {
		
	}
	
	private void prepareTheBill() {
		
	}
	
	private boolean sayGoodbye() {
		
	}

	// table functions
	
	private void saluteTheClient() {
		
	}
	
	private void returnToBar() {
		
	}
	
	private void getThePad() {
		
	}
	
	private boolean haveAllClientsBeenServed() {
		return true;
	}
	
	private void deliverPortion() {
		
	}
	
	private void returnToBar() {
		
	}
	
	// kitchen table
	
	private void handNoteToChef() {
		
	}
	
	private void returnToBar() {
		
	}
	
	private void collectPortion() {
		
	}
}
