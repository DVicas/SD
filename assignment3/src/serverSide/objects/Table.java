package serverSide.objects;

import java.rmi.*;
import interfaces.*;
import serverSide.main.*;
import clientSide.entities.*;

public class Table implements TableInterface {
	
	/**
	 * Number of entities requesting shutdown
	 */
	private int nEntities;

	private int firstStudent;

	private int lastStudent;

	private int nOrders;

	// count number of students finished the course
	private int nStudentsEaten;

	private boolean informingCompanion;

	private int lastToEat;
	
	//n courses eaten
	private int nCourses;

	private int studentBeingAnswered;

	private int nStudentsServed;

	private boolean presentingTheMenu;

	private final int[] studentStates;

	private final boolean[] seated;

	private final boolean[] readMenu;

	private boolean takingOrder;
	

	private int nStudentsAwake;

	private boolean billPresented;

	private GeneralReposInterface repoStub;

	public Table(GeneralReposInterface reposStub) {

		firstStudent = -1;
		lastStudent = -1;
		nOrders = 0;
		nStudentsServed = 0;
		nCourses = 0;
		nStudentsEaten = 0;
		nStudentsAwake = 0;
		lastToEat = -1;
		billPresented = false;
		presentingTheMenu = false;
		takingOrder = false;
		informingCompanion = false;

		this.repoStub = reposStub;

		seated = new boolean[SimulPar.N];
		readMenu = new boolean[SimulPar.N];

		studentStates = new int[SimulPar.N];
		for (int i = 0; i < SimulPar.N; i++) {
			studentStates[i] = -1;
			seated[i] = false;
			readMenu[i] = false;
		}
	}

	// Getters and Setters
	@Override
	public int getFirstStudent() {
		return firstStudent;
	}

	@Override
	public int getLastStudent() {
		return lastStudent;
	}
	
	@Override
	public void setFirstStudent(int studentId) {
		this.firstStudent = studentId;
	}
	
	@Override
	public void setLastStudent(int studentId) {
		this.lastStudent = studentId;
	}

	//
	@Override
	public synchronized void seatAtTable(int studentId) throws RemoteException {
		
		
		studentStates[studentId] = StudentStates.TAKING_A_SEAT_AT_THE_TABLE;
		
		seated[studentId] = true;

		notifyAll();

		while (true) {
			try {
				wait();
			} catch (InterruptedException e) {
			}

			if (studentId == studentBeingAnswered && presentingTheMenu == true)
				break;
		}
	}

	//
	@Override
	public synchronized int readTheMenu(int studentId) throws RemoteException {

		students[studentId].setStudentState(StudentStates.SELECTING_THE_COURSES);
		repoStub.setStudentState(studentId, studentStates[studentId]);

		// student has read the menu
		readMenu[studentId] = true;

		// wake waiter waiting for student to read the menu
		notifyAll();
		
		return studentStates[studentId];
	}
	
	@Override
	public synchronized int prepareOrder() throws RemoteException {

		studentStates[firstStudent] = StudentStates.ORGANIZING_THE_ORDER;
		repoStub.setStudentState(firstStudent, studentStates[firstStudent]);

		// log his own order
		nOrders++;
		
		return studentStates[firstStudent];
	}

	@Override
	public synchronized int joinTalk(int studentId) throws RemoteException {

		studentStates[studentId] = StudentStates.CHATTING_WITH_COMPANIONS;
		repoStub.setStudentState(studentId, studentStates[studentId]);
	
		return studentStates[firstStudent];
	}

	@Override
	public synchronized boolean hasEverybodyChosen() throws RemoteException {

		if (nOrders == SimulPar.N) {
			return true;
		} else {
			while (informingCompanion == false) {
				try {
					wait();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return false;
		}
	}

	@Override
	public synchronized void addUpOnesChoice() throws RemoteException {
		nOrders++;
		informingCompanion = false;
		notifyAll();
	}
	
	@Override
	public synchronized int informCompanion(int studentId) throws RemoteException {


		while (informingCompanion) {
			try {
				wait();
			} catch (Exception e) {
			}
		}

		informingCompanion = true;
		notifyAll();

		studentStates[studentId] = StudentStates.CHATTING_WITH_COMPANIONS;
		repoStub.setStudentState(studentId, studentStates[studentId]);
		
		return studentStates[studentId];
	}
	
	@Override
	public synchronized void describeOrder() throws RemoteException {

		// wait for waiter to get the pad
		while (takingOrder == false) {
			try {
				wait();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		System.out.println("First student described the order");
		takingOrder = false;

		notifyAll();
	}

	@Override
	public synchronized boolean everyoneHasEaten() {

		if (nCourses == SimulPar.M) {
			return true;
		} else {
			System.out.printf("served %d \n", nStudentsServed);
			// wait for everyone to be served
			while (nStudentsServed != SimulPar.N) {
				try {
					wait();
				} catch (Exception e) {
				}
			}
			System.out.println("Course " + nCourses + "Served");

//			System.out.printf("sai %d \n", ((Student) Thread.currentThread()).getStudentId());

			return false;
		}
	}

	public synchronized void startEating() {

		int id = ((TableClientProxy) Thread.currentThread()).getStudentId();

		System.out.printf("started %d \n", ((TableClientProxy) Thread.currentThread()).getStudentId());

		students[id].setStudentState(StudentStates.ENJOYING_THE_MEAL);
		((TableClientProxy) Thread.currentThread()).setStudentState(StudentStates.ENJOYING_THE_MEAL);
		repoStub.setStudentState(id, ((TableClientProxy) Thread.currentThread()).getStudentState());

		try {
			Thread.sleep((long) (1 + 40 * Math.random()));
		} catch (InterruptedException e) {
		}

	}

	public synchronized void endEating() {

		int id = ((TableClientProxy) Thread.currentThread()).getStudentId();

		nStudentsEaten++;

		if (nStudentsEaten == SimulPar.N) {
			nCourses++;

			lastToEat = id;
		}

		students[id].setStudentState(StudentStates.CHATTING_WITH_COMPANIONS);
		((TableClientProxy) Thread.currentThread()).setStudentState(StudentStates.CHATTING_WITH_COMPANIONS);
		repoStub.setStudentState(id, ((TableClientProxy) Thread.currentThread()).getStudentState());
	}

	public boolean isLastCourse() {
		return (nCourses == SimulPar.M);
	}

	public int lastToEat() {
		return lastToEat;
	}

	public synchronized boolean hasEveryoneFinishedPortion() {

		int studentId = ((TableClientProxy) Thread.currentThread()).getStudentId();

		// Notify all students that the last one to eat has already finished
		if (studentId == lastToEat) {
			nStudentsEaten = 0;
			nStudentsServed = 0;
			nStudentsAwake++;
			notifyAll();
			while (nStudentsAwake != SimulPar.N) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
		}

		// Wait while not all students have finished
		while (nStudentsEaten != 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
			System.out.println("Student " + studentId + " woke ");
		}
		nStudentsAwake++;
		if (nStudentsAwake == SimulPar.N)
			notifyAll();

		return true;
	}

	public synchronized boolean shouldHaveArrivedEarlier() {

		int studentId = ((TableClientProxy) Thread.currentThread()).getStudentId();

		if (studentId == lastStudent) {
			students[studentId].setStudentState(StudentStates.PAYING_THE_MEAL);
			((TableClientProxy) Thread.currentThread()).setStudentState(StudentStates.PAYING_THE_MEAL);
			repoStub.setStudentState(studentId, ((TableClientProxy) Thread.currentThread()).getStudentState());
			return true;
		} else {
			return false;
		}
	}

	public synchronized void honorTheBill() {

		while (!billPresented) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		notifyAll();
	}

	public synchronized void saluteTheClient(int studentIdBeingAnswered) {
		studentBeingAnswered = studentIdBeingAnswered;

		((TableClientProxy) Thread.currentThread()).setWaiterState(WaiterStates.PRESENTING_THE_MENU);
		repoStub.setWaiterState(((TableClientProxy) Thread.currentThread()).getWaiterState());

		presentingTheMenu = true;

		while (!seated[studentBeingAnswered]) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		// wakes student waiting for salute
		notifyAll();
		System.out.println("Waiter Saluting student " + studentBeingAnswered + " " + presentingTheMenu);

		// block while student reads the menu
		while (!readMenu[studentBeingAnswered]) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		studentBeingAnswered = -1;
		presentingTheMenu = false;
	}

	public synchronized void getThePad() {

		((TableClientProxy) Thread.currentThread()).setWaiterState(WaiterStates.TAKING_THE_ORDER);
		repoStub.setWaiterState(((TableClientProxy) Thread.currentThread()).getWaiterState());

		takingOrder = true;
		notifyAll();

		System.out.println("Waiter is now wainting for order description");
		// block while waiting for order description
		while (takingOrder) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		System.out.println("Waiter Got the order");

	}

	public synchronized void deliverPortion() {
		nStudentsServed++;
	}

	public synchronized void returnToBar() {
		((TableClientProxy) Thread.currentThread()).setWaiterState(WaiterStates.APPRAISING_SITUATION);
		repoStub.setWaiterState(((TableClientProxy) Thread.currentThread()).getWaiterState());
	}

	public synchronized void presentTheBill() {
		billPresented = true;

		notifyAll();

		((TableClientProxy) Thread.currentThread()).setWaiterState(WaiterStates.RECEIVING_PAYMENT);
		repoStub.setWaiterState(((TableClientProxy) Thread.currentThread()).getWaiterState());

		try {
			wait();
		} catch (InterruptedException e) {
		}
	}

	public synchronized boolean haveAllClientsBeenServed() {

		if (nStudentsServed == SimulPar.N) {
			lastToEat = -1;
			nStudentsAwake = 0;
			notifyAll();
			return true;
		}
		return false;
	}

	/**
	 * Operation server shutdown.
	 *
	 * New operation.
	 */
	public synchronized void shutdown() {
		nEntities += 1;
		if (nEntities >= 2)
			ServerTableMain.waitConnection = false;
		notifyAll();
	}
	
}
