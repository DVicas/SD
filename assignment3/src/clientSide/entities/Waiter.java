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
				tabReturnToBar();
				break;

			// call the waiter
			case 'w':
				getThePad();
				handNoteToChef();
				kitReturnToBar();
				break;

			// alert the waiter
			case 'a':
				while (!haveAllClientsBeenServed()) {

					collectPortion();
					deliverPortion();
				}
				tabReturnToBar();
				break;

			// should have arrived earlier
			case 'e':
				prepareTheBill();
				presentTheBill();
				tabReturnToBar();
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
		char c = '\0';
		
		try {
			c = barStub.lookAround();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(c != 'c' && c != 'w' && c != 'a' && c != 'e' && c != 'g') {
			GenericIO.writelnString("Invalid waiter status");
			System.exit(1);
		}
		return c;
	}
	
	private int getStudentBeingAnswered() {
		int studentId = -1;
		try {
			studentId = barStub.getStudentBeingAnswered();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (studentId == -1) {
			GenericIO.writelnString("Invalid id");
			System.exit(-1);
		}
		
		return studentId;
	}
	
	private void prepareTheBill() {
		try {
			waiterState = barStub.prepareTheBill();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private boolean sayGoodbye() {
		boolean b = false;
		
		try {
			b = barStub.sayGoodbye();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return b;
	}

	// table functions
	
	private void saluteTheClient(int studentId) {
		try {
			waiterState = tableStub.saluteTheClient(studentId);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void getThePad() {
		try {
			waiterState = tableStub.getThePad();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private boolean haveAllClientsBeenServed() {
		boolean b = false;
		
		try {
			b = tableStub.haveAllClientsBeenServed();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return b;
	}
	
	private void deliverPortion() {
		try {
			tableStub.deliverPortion();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private void tabReturnToBar() {
		try {
			waiterState = tableStub.returnToBar();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void presentTheBill() {
		try {
			waiterState = tableStub.presentTheBill();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	// kitchen functions
	
	private void handNoteToChef() {
		try {
			waiterState = kitchenStub.handNoteToChef();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void kitReturnToBar() {
		try {
			waiterState = kitchenStub.returnToBar();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void collectPortion() {
		try {
			waiterState = kitchenStub.collectPortion();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
