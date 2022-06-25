package serverSide.objects;

import java.rmi.RemoteException;
import clientSide.entities.*;
import interfaces.GeneralReposInterface;
import interfaces.KitchenInterface;
import serverSide.main.ExecuteConst;
import serverSide.main.ServerRestaurantKitchen;

public class Kitchen implements KitchenInterface{

	/**
	 * Number of entities requesting shutdown
	 */
	private int nEntities;

	private int nPortionsReady;
	private int nPortionsServed;
	private int nCoursesServed;
	private int nPortionPrepared;

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

    @Override
	public synchronized int watchTheNews() throws RemoteException {

		repo.setChefState(ChefStates.WAITING_FOR_AN_ORDER);

		while (!handedNoteToChef) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		receivedNote = true;
		notifyAll();
        return ChefStates.WAITING_FOR_AN_ORDER;
	}

    @Override
	public synchronized int startPreparation() throws RemoteException{
		
		repo.setnCourses(nCoursesServed+1);
		repo.setChefState(ChefStates.PREPARING_THE_COURSE);

		handedNoteToChef = false;

		notifyAll();

        return ChefStates.PREPARING_THE_COURSE;
	}

    @Override
	public synchronized int proceedToPresentation() throws RemoteException {
		
		nPortionPrepared++;
		repo.setnPortions(nCoursesServed);
		repo.setChefState(ChefStates.DISHING_THE_PORTIONS);

		nPortionsReady++;

        return ChefStates.DISHING_THE_PORTIONS;
	}

    @Override
	public synchronized boolean haveAllPortionsBeenDelivered() throws RemoteException {

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

    @Override
	public synchronized boolean hasTheOrderBeenCompleted() throws RemoteException {

		if (nCoursesServed == SimulPar.M) {
			return true;
		}
		return false;
	}

    @Override
	public synchronized int continuePreparation() throws RemoteException {

		repo.setnCourses(nCoursesServed+1);
		nPortionPrepared = 0;
		repo.setnPortions(nPortionPrepared);
		
		repo.setChefState(ChefStates.PREPARING_THE_COURSE);
        return ChefStates.PREPARING_THE_COURSE;
	}

    @Override
	public synchronized int haveNextPortionReady() throws RemoteException {
		
		nPortionPrepared++;
		repo.setnPortions(nPortionPrepared);
		repo.setChefState(ChefStates.DISHING_THE_PORTIONS);

		nPortionsReady++;
		notifyAll();

        return ChefStates.DELIVERING_THE_PORTIONS
	}

    @Override
	public synchronized int cleanUp() throws RemoteException {

		repo.setChefState(ChefStates.CLOSING_SERVICE);
        return ChefStates.CLOSING_SERVICE;
	}

    @Override
	public synchronized int handNoteToChef() throws RemoteException {
		repo.setWaiterState(WaiterStates.PLACING_THE_ORDER);

		handedNoteToChef = true;

		notifyAll();

		while (!receivedNote) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		receivedNote = false;
    return WaiterStates.PLACING_THE_ORDER;
	}

    @Override
	public synchronized int returnToBar() throws RemoteException {
		repo.setWaiterState(WaiterStates.APRAISING_SITUATION);
		return WaiterStates.APRAISING_SITUATION;
	}

    @Override
	public synchronized int collectPortion() throws RemoteException{
		repo.setWaiterState(WaiterStates.WAITING_FOR_PORTION);

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

		repo.setnCourses(nCoursesServed);
		repo.setnPortions(nPortionsServed);

		notifyAll();
        return WaiterStates.WAITING_FOR_PORTION;
	}

    @Override
	public synchronized void shutdown() {
		nEntities += 1;
		if (nEntities >= 2)
			ServerKitchenMain.waitConnection = false;
		notifyAll();
	}
}