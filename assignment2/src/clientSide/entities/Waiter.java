package clientSide.entities;

import clientSide.stubs.*;

public class Waiter extends Thread {
	
	/**
	 * Waiter state
	 */
	private int waiterState;

	/**
	 * Reference to table stub
	 */
	private TableStub tableStub;
	
	/**
	 * Reference to kitchen stub
	 */
	private KitchenStub kitchenStub;
	
	/**
	 * Reference to bar stub
	 */
	private BarStub barStub;

	public Waiter(String name, TableStub tableStub, KitchenStub kitchenStub, BarStub barStub) {
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
			status = barStub.lookAround();

			switch (status) {

			// enter the bar
			case 'c':
				tableStub.saluteTheClient(barStub.getStudentBeingAnswered());
				tableStub.returnToBar();
				break;

			// call the waiter
			case 'w':
				tableStub.getThePad();
				kitchenStub.handNoteToChef();
				kitchenStub.returnToBar();
				break;

			// alert the waiter
			case 'a':
				while (!tableStub.haveAllClientsBeenServed()) {

					kitchenStub.collectPortion();
					tableStub.deliverPortion();
				}
				tableStub.returnToBar();
				break;

			// should have arrived earlier
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

		} while (!goodbye);
	}
}
