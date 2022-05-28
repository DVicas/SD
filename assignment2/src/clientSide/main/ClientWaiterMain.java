package clientSide.main;

import clientSide.entities.Waiter;
import clientSide.stubs.BarStub;
import clientSide.stubs.KitchenStub;
import clientSide.stubs.TableStub;

/**
 *
 * 
 */
public class ClientWaiterMain {
	/**
	 * Main method.
	 *
	 * @param args runtime arguments
	 */
	public static void main(String[] args) {
		Waiter waiter; // Reference to the Waiter Thread

		BarStub barStub; // Reference to the Bar
		KitchenStub kitchenStub; // Reference to the Kitchen
		TableStub tableStub; // Reference to the Table

		barStub = new BarStub(" ", 22150);
		kitchenStub = new KitchenStub(" ", 22151);
		tableStub = new TableStub(" ", 22152);

		waiter = new Waiter("Waiter_1", tableStub, kitchenStub, barStub);

		/* start thread */
		waiter.start();

		/* wait for the end */
		try {
			waiter.join();
		} catch (InterruptedException e) {
		}
		System.out.println("The Waiter 1 just terminated");

		System.out.println("End of the Simulation");

	}
}
