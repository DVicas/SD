package serverSide.objects;

import java.rmi.*;

import clientSide.entities.*;
import commInfra.MemException;
import commInfra.MemFIFO;
import commInfra.Request;
import interfaces.BarInterface;
import interfaces.GeneralReposInterface;
import interfaces.TableInterface;
import serverSide.main.ServerRestaurantBar;
import serverSide.main.SimulPar;

public class Bar implements BarInterface{
    
	/**
	 * Number of entity groups requesting the shutdown.
	 */
	private int nEntities;

	/**
	 * Number of students present in the restaurant
	 */
	private int nStudentsInRestaurant;

	/**
	 * Number of pending requests to be answered by the waiter
	 */
	private int nPendingRequests;

	/**
	 * Queue of pending Requests
	 */
	private MemFIFO<Request> reqQueue;

	/**
	 * Reference to the student states
	 */
	private final int [] students;

	/**
	 * Array of booleans to keep track of the students which the waiter has already
	 * said goodbye
	 */
	private boolean[] studentsGreeted;

	/**
	 * Reference to the general repository
	 */
	private final GeneralReposInterface repoStub;

	/**
	 * Reference to the table
	 */
	private final TableInterface tableStub;

	/**
	 * Auxiliary variable to keep track of the id of the student whose request is
	 * being answered
	 */
	private int studentBeingAnswered;

	/**
	 * Boolean variable used to store if a course was finished or not
	 */
	private boolean courseFinished;
    public Bar(GeneralReposInterface repoStub, TableInterface tableStub) {

    students = new int[SimulPar.N];
    for (int i = 0; i < SimulPar.N; i++) {
        students[i] = -1;
    }

    try {
        reqQueue = new MemFIFO<>(new Request[SimulPar.N * SimulPar.M]);
    } catch (MemException e) {
        reqQueue = null;
        System.exit(1);
    }

    this.tableStub = tableStub;
    this.nPendingRequests = 0;
    this.nStudentsInRestaurant = 0;
    this.studentBeingAnswered = -1;
    this.courseFinished = true;

    this.studentsGreeted = new boolean[SimulPar.N];
    for (int i = 0; i < SimulPar.N; i++) {
        studentsGreeted[i] = false;
    }

    this.repoStub = repoStub;
	}

    @Override
    public synchronized int getStudentBeingAnswered() throws RemoteException {
    return studentBeingAnswered;
	}

    @Override
	public synchronized int alertTheWaiter() throws RemoteException {

		System.out.println("courseFinished");

		while (!courseFinished) {
			try {
				wait();
			} catch (InterruptedException e1) {
			}
		}

		Request r = new Request(SimulPar.N + 1, 'a');

		try {
			reqQueue.write(r);
		} catch (MemException e) {
		}

		nPendingRequests++;
		courseFinished = false;

		repoStub.setChefState(ChefStates.DELIVERING_THE_PORTIONS);


		notifyAll();

        return ChefStates.DELIVERING_THE_PORTIONS;
	}

	@Override
	public synchronized int prepareTheBill() throws RemoteException{
		//Update Waiter state
		repoStub.setWaiterState(WaiterStates.PROCESSING_THE_BILL);

		return WaiterStates.PROCESSING_THE_BILL;
	}

    @Override
	public synchronized int enter(int studentId) throws RemoteException {

		students[studentId] = StudentStates.GOING_TO_THE_RESTAURANT;		
		repoStub.setStudentState(studentId, students[studentId]);
		
		nStudentsInRestaurant++;
		if (nStudentsInRestaurant == 1) {
			tableStub.setFirstStudent(studentId);
		} else if (nStudentsInRestaurant == SimulPar.N) {
			tableStub.setLastStudent(studentId);
		}

		try {
			reqQueue.write(new Request(studentId, 'c'));
		} catch (MemException e) {
		}

		nPendingRequests++;

		repoStub.updateSeatsAtTable(nStudentsInRestaurant - 1, studentId);

		notifyAll();

        return students[studentId];
	}

    @Override
	public synchronized void callWaiter(int studentId) throws RemoteException {

		Request newReq;


		newReq = new Request(studentId, 'w');

		try {
			reqQueue.write(newReq);
		} catch (Exception e) {
		}

		nPendingRequests++;

		notifyAll();
	}

    @Override
	public synchronized void signalWaiter(int studentId, int stuState) throws RemoteException{

        students[studentId] = stuState;

		if (students[studentId] == StudentStates.PAYING_THE_MEAL) {
			try {
				reqQueue.write(new Request(studentId, 'e'));
			} catch (MemException e) {
			}
			// Update pending requests
			nPendingRequests++;

			// Signal waiter of a pending request
			notifyAll();

		} else {

			courseFinished = true;
			notifyAll();
		}
	} 

    @Override
	public synchronized int exit(int studentId) throws RemoteException{

		Request request = new Request(studentId, 'g');

		try {
			reqQueue.write(request);
		} catch (MemException e) {
		}

		nPendingRequests++;


        students[studentId] = StudentStates.GOING_HOME;
		repoStub.setStudentState(studentId, students[studentId]);

		notifyAll();
		while (studentsGreeted[studentId] == false) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
			System.out.println("Student " + studentId + " wants to leave");
		}
		System.out.println("I want out " + studentId);
    
        return students[studentId];
	}

    @Override
	public synchronized char lookAround() throws RemoteException {

		Request req = null;

		while (nPendingRequests == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		try {
			req = reqQueue.read();
		} catch (MemException e) {
		}

		nPendingRequests--;
		studentBeingAnswered = req.id;

		return req.type;
	}

    @Override
	public synchronized boolean sayGoodbye() throws RemoteException {

		// Student was greeted
		studentsGreeted[studentBeingAnswered] = true;
		// Wake up student that is waiting to be greeted by waiter
		notifyAll();

		// Update number of students at the restaurant
		nStudentsInRestaurant--;
//		repoStub.updateSeatsAtLeaving(studentBeingAnswered);
		studentBeingAnswered = -1;

        repoStub.setWaiterState(WaiterStates.APPRAISING_SITUATION);

		if (nStudentsInRestaurant == 0)
			return true;

		return false;

	}

    @Override
	public synchronized void shutdown() throws RemoteException {
		nEntities += 1;
		if (nEntities >= 3)
			ServerRestaurantBar.shutdown(); //NAOSEIOQUEEAQUIIIIIIIIIIIII
		notifyAll();
	}
}
