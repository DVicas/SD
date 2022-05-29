package serverSide.sharedRegions;

import clientSide.entities.*;
import clientSide.stubs.GeneralReposStub;
import clientSide.stubs.TableStub;
import commInfra.*;
import serverSide.entities.*;
import serverSide.main.ServerBarMain;
import serverSide.main.SimulPar;

public class Bar {

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
	 * Reference to the student threads
	 */
	private final BarClientProxy[] students;

	/**
	 * Array of booleans to keep track of the students which the waiter has already
	 * said goodbye
	 */
	private boolean[] studentsGreeted;

	/**
	 * Reference to the general repository
	 */
	private final GeneralReposStub repoStub;

	/**
	 * Reference to the table
	 */
	private final TableStub tableStub;

	/**
	 * Auxiliary variable to keep track of the id of the student whose request is
	 * being answered
	 */
	private int studentBeingAnswered;

	/**
	 * Boolean variable used to store if a course was finished or not
	 */
	private boolean courseFinished;

	public Bar(GeneralReposStub repoStub, TableStub tableStub) {

		students = new BarClientProxy[SimulPar.N];
		for (int i = 0; i < SimulPar.N; i++) {
			students[i] = null;
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

	public synchronized char lookAround() {

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

	//TODO ver if se houver erros
	public synchronized void prepareTheBill() {

		BarClientProxy waiter = ((BarClientProxy) Thread.currentThread());
		if (waiter.getWaiterState() != WaiterStates.PROCESSING_THE_BILL) {
			waiter.setWaiterState(WaiterStates.PROCESSING_THE_BILL);
			repoStub.setWaiterState(WaiterStates.PROCESSING_THE_BILL);
		}
	}

	public synchronized boolean sayGoodbye() {

		// Student was greeted
		studentsGreeted[studentBeingAnswered] = true;
		// Wake up student that is waiting to be greeted by waiter
		notifyAll();

		// Update number of students at the restaurant
		nStudentsInRestaurant--;
		studentBeingAnswered = -1;

<<<<<<< HEAD
		repoStub.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
=======
		repo.setWaiterState(((BarClientProxy) Thread.currentThread()).getWaiterState());
>>>>>>> 13c01173657d992e95601872daf216c1c0a2056a

		if (nStudentsInRestaurant == 0)
			return true;

		return false;

	}

	public synchronized void enter() {

		BarClientProxy student = ((BarClientProxy) Thread.currentThread());
		
		int id = student.getStudentId();
		students[id] = student;

		if(student.getStudentState() != StudentStates.GOING_TO_THE_RESTAURANT) {
			students[id].setStudentState(StudentStates.GOING_TO_THE_RESTAURANT);
			repoStub.setStudentState(id, StudentStates.GOING_TO_THE_RESTAURANT);
		}
		
		nStudentsInRestaurant++;
		if (nStudentsInRestaurant == 1) {
			tableStub.setFirstStudent(id);
		} else if (nStudentsInRestaurant == SimulPar.N) {
			tableStub.setLastStudent(id);
		}

		try {
			reqQueue.write(new Request(id, 'c'));
		} catch (MemException e) {
		}

		nPendingRequests++;

		students[id].setStudentState(StudentStates.TAKING_A_SEAT_AT_THE_TABLE);
		repo.setStudentState(id, ((BarClientProxy) Thread.currentThread()).getStudentState());
		repo.updateSeatsAtTable(nStudentsInRestaurant - 1, id);

		notifyAll();
	}

	public synchronized void callWaiter() {

		Request newReq;
		int id = ((BarClientProxy) Thread.currentThread()).getStudentId();

		newReq = new Request(id, 'w');

		try {
			reqQueue.write(newReq);
		} catch (Exception e) {
		}

		nPendingRequests++;

		notifyAll();
	}

	public synchronized void signalWaiter() {

		int studentId = ((BarClientProxy) Thread.currentThread()).getStudentId();
		if (((BarClientProxy) Thread.currentThread()).getStudentState() == StudentStates.PAYING_THE_MEAL) {
			try {
				reqQueue.write(new Request(studentId, 'e'));
			} catch (MemException e) {
			}
			// Update pending requests
			nPendingRequests++;

			// Signal waiter of a pending request
			notifyAll();

		} else {
			System.out.println("SINGALING");

			courseFinished = true;
			notifyAll();
		}
	}

	public synchronized void alertTheWaiter() {

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

<<<<<<< HEAD
		((Chef) Thread.currentThread()).setChefState(ChefStates.DELIVERING_THE_PORTIONS);
		repoStub.setChefState(((Chef) Thread.currentThread()).getChefState());
=======
		((BarClientProxy) Thread.currentThread()).setChefState(ChefStates.DELIVERING_THE_PORTIONS);
		repo.setChefState(((BarClientProxy) Thread.currentThread()).getChefState());
>>>>>>> 13c01173657d992e95601872daf216c1c0a2056a

		notifyAll();
	}

	public synchronized void exit() {

		int id = ((BarClientProxy) Thread.currentThread()).getStudentId();
		Request request = new Request(id, 'g');

		try {
			reqQueue.write(request);
		} catch (MemException e) {
		}

		nPendingRequests++;

		notifyAll();

		students[id].setStudentState(StudentStates.GOING_HOME);
<<<<<<< HEAD
		repoStub.setStudentState(id, ((Student) Thread.currentThread()).getStudentState());
=======
		repo.setStudentState(id, ((BarClientProxy) Thread.currentThread()).getStudentState());
>>>>>>> 13c01173657d992e95601872daf216c1c0a2056a

		while (studentsGreeted[id] == false) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
			System.out.println("Student " + id + " wants to leave");
		}
		System.out.println("I want out " + id);
	}

	public synchronized int getStudentBeingAnswered() {
		return studentBeingAnswered;
	}

	/**
	 * Operation server shutdown.
	 *
	 * New operation.
	 */
	public synchronized void shutdown() {
		nEntities += 1;
		if (nEntities >= 3)
			ServerBarMain.waitConnection = false;
		notifyAll();
	}

}
