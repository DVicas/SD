package serverSide.sharedRegions;

import serverSide.main.*;
import serverSide.entities.*;
import clientSide.entities.*;
import clientSide.stubs.*;
import commInfra.*;
import genclass.GenericIO;

public class Kitchen {

	/**
	 * Number of entities requesting shutdown
	 */
	private int nEntities;

	private int nPortionsReady;
	private int nPortionsServed;
	private int nCoursesServed;

	private boolean handedNoteToChef;
	private boolean receivedNote;

	/**
	 * Reference to General Repositories
	 */
	private GeneralReposStub repo;

	/**
	 * Kitchen Instantiation
	 * 
	 * @param reposStub
	 */

	public Kitchen(GeneralReposStub reposStub) {
		this.nPortionsReady = 0;
		this.nPortionsServed = 0;
		this.nCoursesServed = 0;
		this.handedNoteToChef = false;
		this.receivedNote = false;

		this.repo = reposStub;
	}

	public synchronized void watchTheNews() {

		((Chef) Thread.currentThread()).setChefState(ChefStates.WAITING_FOR_AN_ORDER);
		repo.setChefState(((Chef) Thread.currentThread()).getChefState());

		while (!handedNoteToChef) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		receivedNote = true;
		notifyAll();
	}

	public synchronized void startPreparation() {

		((Chef) Thread.currentThread()).setChefState(ChefStates.PREPARING_THE_COURSE);
		repo.setChefState(((Chef) Thread.currentThread()).getChefState());

		handedNoteToChef = false;

		notifyAll();
	}

	public synchronized void proceedToPresentation() {

		((Chef) Thread.currentThread()).setChefState(ChefStates.DISHING_THE_PORTIONS);
		repo.setChefState(((Chef) Thread.currentThread()).getChefState());

		nPortionsReady++;
	}

	public synchronized void continuePreparation() {

		((Chef) Thread.currentThread()).setChefState(ChefStates.PREPARING_THE_COURSE);
		repo.setChefState(((Chef) Thread.currentThread()).getChefState());
	}

	public synchronized boolean haveAllPortionsBeenDelivered() {

		while (nPortionsReady != 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		System.out.println("delivered");
		if (nPortionsServed == SimulPar.N) {

			nCoursesServed++;
			return true;
		}

		return false;
	}

	public synchronized void haveNextPortionReady() {

		((Chef) Thread.currentThread()).setChefState(ChefStates.DISHING_THE_PORTIONS);
		repo.setChefState(((Chef) Thread.currentThread()).getChefState());

		nPortionsReady++;

		((Chef) Thread.currentThread()).setChefState(ChefStates.DELIVERING_THE_PORTIONS);
		repo.setChefState(((Chef) Thread.currentThread()).getChefState());

		notifyAll();
	}

	public synchronized boolean hasTheOrderBeenCompleted() {

		if (nCoursesServed == SimulPar.M) {
			return true;
		}
		return false;
	}

	public synchronized void handNoteToChef() {
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.PLACING_THE_ORDER);
		repo.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());

		handedNoteToChef = true;

		notifyAll();

		while (!receivedNote) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		receivedNote = false;
	}

	public synchronized void returnToBar() {
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.APPRAISING_SITUATION);
		repo.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
	}

	public synchronized void collectPortion() {
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.WAITING_FOR_PORTION);
		repo.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());

		while (nPortionsReady == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		nPortionsReady--;
		nPortionsServed++;

		if (nPortionsServed > SimulPar.N) {
			nPortionsServed = 1;
		}

//		repo.setnCourses(nCoursesServed);
//		repo.setnPortions(nPortionsServed);

		notifyAll();
	}

	public synchronized void cleanUp() {

		((Chef) Thread.currentThread()).setChefState(ChefStates.CLOSING_SERVICE);
		repo.setChefState(((Chef) Thread.currentThread()).getChefState());
	}
	
	/**
	 *   Operation server shutdown.
	 *
	 *   New operation.
	 */
	 public synchronized void shutdown() {
		 nEntities += 1;
	     if (nEntities >= 2)
	    	 ServerKitchenMain.waitConnection = false;
	     notifyAll();
	 }
}
