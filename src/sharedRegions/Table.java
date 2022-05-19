package sharedRegions;

import entities.*;
import main.SimulPar;

public class Table {
	
	private int firstStudent;
	
	private int lastStudent;
	
	private int nOrders;
	
	private boolean informingCompanion;
	
	//
	private int studentBeingAnswered;
	
	//
	private boolean presentingTheMenu;
	
	private final Student[] students;
	
	private final boolean[] seated;
	
	private final boolean[] readMenu;

	private boolean takingOrder;

	private int nCourses;

	private int nStudentsServed;

	private int lastToEat;
	
	private int nStudentsEaten;
	
	private int nStudentsAwake;

	private boolean billPresented;
	
	private GeneralRepos repo;
	
	public Table(GeneralRepos repo) {
		
		firstStudent = 0;
		lastStudent = 0;
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
		
		this.repo = repo;
		
		seated = new boolean[SimulPar.N];
		readMenu = new boolean[SimulPar.N];
		
		
		students = new Student[SimulPar.N];
		for(int i = 0; i < SimulPar.N; i++ ) {
			students[i] = null;
		}
	}
	
	
	// Getters and Setters
	public int getFirstStudent() { return firstStudent; }
	
	public int getLastStudent() { return lastStudent; }
	
	public void setFirstStudent(int studentId) { this.firstStudent = studentId; }
	
	public void setLastStudent(int studentId) { this.lastStudent = studentId; }
	
	//
	public synchronized void seatAtTable() {
	
		int id = ((Student) Thread.currentThread()).getStudentId();
		students[id] = (Student) Thread.currentThread();
		students[id].setStudentState(StudentStates.TAKING_A_SEAT_AT_THE_TABLE);
		repo.updateStudentState(id, ((Student) Thread.currentThread()).getStudentState());
		
		seated[id] = true;
		
		notifyAll();
		
		while(true) {
			try {
				wait();
			} catch (InterruptedException e) { }
			
			if (id == studentBeingAnswered && presentingTheMenu == true) break;
		} 
	}

	//
	public synchronized void readTheMenu() {
		
		int id = ((Student) Thread.currentThread()).getStudentId();
		
		students[id].setStudentState(StudentStates.SELECTING_THE_COURSES);
		repo.updateStudentState(id, ((Student) Thread.currentThread()).getStudentState());
		
		//student has read the menu
		readMenu[id] = true;
		
		//wake waiter waiting for student to read the menu
		notifyAll();
	}
	

	public synchronized void prepareOrder() {
		
		int id = ((Student) Thread.currentThread()).getStudentId();
		
		students[id].setStudentState(StudentStates.ORGANIZING_THE_ORDER);
		repo.updateStudentState(id, ((Student) Thread.currentThread()).getStudentState());
		
		//log his own order
		nOrders++;
	}
	

	public synchronized void joinTalk() {
		
		int id = ((Student) Thread.currentThread()).getStudentId();
		students[id].setStudentState(StudentStates.CHATTING_WITH_COMPANIONS);
		repo.updateStudentState(id, ((Student) Thread.currentThread()).getStudentState());
	}

	
	public synchronized boolean hasEverybodyChosen() {
		
		if (nOrders == SimulPar.N) return true;
		
		while(informingCompanion == false) {
			try 
			{ wait();
			} catch (Exception e) { }
		}
		
		return false;
	}
	
	public synchronized void addUpOnesChoice() {
		nOrders++;
		
		informingCompanion = false;
		
		notifyAll();
	}
	
	public synchronized void informCompanion() {
		
		int id;
		while (informingCompanion) {
			try 
			{ wait();
			} catch (Exception e) { }
		}
		
		informingCompanion = true;
		
		notifyAll();
		
		id = ((Student) Thread.currentThread()).getStudentId();
			
		students[id].setStudentState(StudentStates.CHATTING_WITH_COMPANIONS);
		repo.updateStudentState(id, ((Student) Thread.currentThread()).getStudentState());
		
	}
	
	public synchronized void describeOrder() {
		
		// wait for waiter to get the pad
		while (takingOrder == false) {
			try 
			{ wait();
			}catch (Exception e) { }
			
		}
		
		System.out.println("First student described the order");
		takingOrder = false;
		
		notifyAll();
	}


	public synchronized boolean everyoneHasEaten() {
		
		if (nCourses == SimulPar.M) return true;
		
		
		// wait for everyone to be served
		while (nStudentsServed != SimulPar.N) {
			
			try
			{ wait();
			} catch (Exception e) { }
		}
		System.out.println("Course " + nCourses + "Served");
		
		return false;
	}


	public synchronized void startEating() {
		
		int id = ((Student) Thread.currentThread()).getStudentId();
		
		students[id].setStudentState(StudentStates.ENJOYING_THE_MEAL);
		repo.updateStudentState(id, ((Student) Thread.currentThread()).getStudentState());
		
		try
	      { Thread.sleep((long) (1 + 40 * Math.random()));
	      }
	      catch (InterruptedException e) {}
		
	}
	
	public synchronized void endEating() {
		
		int id = ((Student) Thread.currentThread()).getStudentId();
		
		nStudentsServed++;
		
		if (nStudentsServed == SimulPar.N) {
			nCourses++;
			
			lastToEat = id;
		}
		
		students[id].setStudentState(StudentStates.CHATTING_WITH_COMPANIONS);
		repo.updateStudentState(id, ((Student) Thread.currentThread()).getStudentState());
	}
	
	public boolean isLastCourse() {
		return (nCourses == SimulPar.M);
	}
	
	public int lastToEat() { 
		return lastToEat; 
	}


	public synchronized boolean hasEveryoneFinishedPortion() {
		
		int studentId = ((Student) Thread.currentThread()).getStudentId();
    	
    	//Notify all students that the last one to eat has already finished
    	if(studentId == lastToEat)
    	{
    		nStudentsEaten = 0;
    		nStudentsServed = 0;
    		nStudentsAwake++;
    		notifyAll();
    		while(nStudentsAwake != SimulPar.N)
    		{
    			try {
					wait();
				} catch (InterruptedException e) { }
    		}
    	}
    	
    	//Wait while not all students have finished
    	while(nStudentsEaten != 0) {
    		try {
				wait();
			} catch (InterruptedException e) { }
    		System.out.println("Student "+studentId+" woke ");
    	}
    	nStudentsAwake++;
    	if(nStudentsAwake == SimulPar.N)
    		notifyAll();
    	
    	return true;
	}

	public synchronized boolean shouldHaveArrivedEarlier() {
		
		int studentId = ((Student) Thread.currentThread()).getStudentId();

    	if(studentId == lastStudent) {
	    	students[studentId].setStudentState(StudentStates.PAYING_THE_MEAL);
	    	repo.updateStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
	    	return true;
    	}
    	else {
    		return false;
		}
	} 
	
	public synchronized void honorTheBill() {
		
		while (!billPresented) {
			try {
				wait();
			} catch (InterruptedException e) { }
		}
		
		notifyAll();
	}


	public synchronized void saluteTheClient(int studentIdBeingAnswered) {
		studentBeingAnswered = studentIdBeingAnswered;
    	
    	((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.PRESENTING_THE_MENU);
    	repo.updateWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
    	
    	presentingTheMenu = true;
    	
    	while(!seated[studentBeingAnswered])
    	{
			try {
				wait();
			} catch (InterruptedException e) {	}
    	}
    	
    	//wakes student waiting for salute
    	notifyAll();
    	System.out.println("Waiter Saluting student "+studentBeingAnswered+ " "+presentingTheMenu);

    	//block while student reads the menu
    	while(!readMenu[studentBeingAnswered])
    	{
	    	try {
				wait();
			} catch (InterruptedException e) { }    
    	}
    	
    	presentingTheMenu  = false;
    	studentBeingAnswered = -1;
	}


	public synchronized void getThePad() {
		
    	((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.TAKING_THE_ORDER);
    	repo.updateWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
    	
    	takingOrder = true; 
    	notifyAll();
    	
    	System.out.println("Waiter is now wainting for order description");
    	//block while waiting for order description
    	while(takingOrder)
    	{
	    	try {
				wait();
			} catch (InterruptedException e) {}
    	}
    	
    	System.out.println("Waiter Got the order");
		
	}


	public synchronized void deliverPortion() {
		nStudentsServed++;
	}

	public synchronized void returnToBar() {
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.APPRAISING_SITUATION);
		repo.updateWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
	}

	public synchronized void presentTheBill() {
		billPresented = true;
    	
    	notifyAll();
    	
    	((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.RECEIVING_PAYMENT);
    	repo.updateWaiterState(((Waiter) Thread.currentThread()).getWaiterState());

    	try {
			wait();
		} catch (InterruptedException e) { }
	}

	public synchronized boolean haveAllClientsBeenServed() {
		
		if(nStudentsServed == SimulPar.N) {
			lastToEat = -1;
			nStudentsAwake = 0;
			notifyAll();
			return true;
		}
		return false;
	}
	
}
