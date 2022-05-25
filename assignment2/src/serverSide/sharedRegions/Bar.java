package serverSide.sharedRegions;

import commInfra.*;
import entities.*;
import main.*;

public class Bar {
			
	private MemFIFO<Request> reqQueue;
	
	private final Student[] students;
	
	private boolean[] studentsGreeted;
	
	private final GeneralRepos repo;
	
	private Table table;
	
	private int nPendingRequests;
	private int nStudentsInRestaurant;
	
	private int studentBeingAnswered;
	
	private boolean courseFinished;
	
	public Bar(GeneralRepos repo, Table table) {
		
		students = new Student[SimulPar.N];
		for (int i = 0; i < SimulPar.N; i++) {
			students[i] = null;
		}
		
		try {
			reqQueue = new MemFIFO<>(new Request[SimulPar.N * SimulPar.M]);
		} catch (MemException e) {
			reqQueue = null;
	        System.exit (1);
		}
		
		this.table = table;
		this.nPendingRequests = 0;
		this.nStudentsInRestaurant = 0;
		this.studentBeingAnswered = -1;
		this.courseFinished = true;
		
		this.studentsGreeted = new boolean[SimulPar.N];
		for (int i = 0; i < SimulPar.N; i++) {
			studentsGreeted[i] = false;
		}
		
		this.repo = repo;
	}

	public synchronized char lookAround() {
		
		Request req = null;
		
		while(nPendingRequests == 0) {
			try 
			{ wait();
			} catch (InterruptedException e) {}
		}
		
		try {
			req = reqQueue.read();	
		} catch (MemException e) {}
		
		nPendingRequests--;
		studentBeingAnswered = req.id;
		
		return req.type;
	}

	public synchronized void prepareTheBill() {
		
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.PROCESSING_THE_BILL);
		repo.updateWaiterState(((Waiter) Thread.currentThread()).getWaiterState());	
	}

	public synchronized boolean sayGoodbye() {
		
		//Student was greeted
		studentsGreeted[studentBeingAnswered] = true;
		//Wake up student that is waiting to be greeted by waiter
		notifyAll();
		
		//Update number of students at the restaurant
		nStudentsInRestaurant--;
		studentBeingAnswered = -1;
		
		repo.updateWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
		
		if(nStudentsInRestaurant == 0) return true;
		
		return false;
		
	}
	
	public synchronized void enter() {
		
		int id = ((Student) Thread.currentThread()).getStudentId();
		students[id] = ((Student) Thread.currentThread());
		
		nStudentsInRestaurant++;
		if(nStudentsInRestaurant == 1) {
			table.setFirstStudent(id);				
		}
		else if (nStudentsInRestaurant == SimulPar.N) {
			table.setLastStudent(id);
		}

		try {
			reqQueue.write(new Request(id, 'c'));
		} catch (MemException e) {}

		nPendingRequests++;
		
		students[id].setStudentState(StudentStates.TAKING_A_SEAT_AT_THE_TABLE);
		repo.updateStudentState(id, ((Student) Thread.currentThread()).getStudentState());
		repo.updateStudentSeat(nStudentsInRestaurant-1, id);
		
		notifyAll();
	}
	
	public synchronized void callWaiter() {
		
		Request newReq;
		int id = ((Student) Thread.currentThread()).getStudentId();
		
		newReq = new Request(id, 'w');
		
		try 
		{ reqQueue.write(newReq);
		} catch (Exception e) {	}
		
		nPendingRequests++;
		
		notifyAll();
	}
	
	public synchronized void signalWaiter() {
		
		int studentId = ((Student) Thread.currentThread()).getStudentId();
		if(((Student) Thread.currentThread()).getStudentState() == StudentStates.PAYING_THE_MEAL)
		{		
			try {
				reqQueue.write(new Request(studentId, 'e'));
			} catch (MemException e) {	}
			//Update pending requests
			nPendingRequests++;	
			
			//Signal waiter of a pending request
			notifyAll();
			
		} else {
			System.out.println("SINGALING");

			courseFinished = true;
			notifyAll();
		}
	}


	public synchronized void alertTheWaiter() {
		
		System.out.println("courseFinished");

		while(!courseFinished)
		{
			try {
				wait();
			} catch (InterruptedException e1) { }
		}
		      
		Request r = new Request(SimulPar.N+1,'a');
		
		try {
			reqQueue.write(r);
		} catch (MemException e) {}
		
		nPendingRequests++;
		courseFinished = false;
		
		((Chef) Thread.currentThread()).setChefState(ChefStates.DELIVERING_THE_PORTIONS);
		repo.updateChefState(((Chef) Thread.currentThread()).getChefState());
		
		notifyAll();
	}

	public synchronized void exit() {
		
		int id = ((Student) Thread.currentThread()).getStudentId();
		Request request = new Request(id, 'g');
		
		try {
			reqQueue.write(request);
		} catch (MemException e) { }
		
		nPendingRequests++;
		
		notifyAll();
		
		students[id].setStudentState(StudentStates.GOING_HOME);
		repo.updateStudentState(id, ((Student) Thread.currentThread()).getStudentState());
		
		while(studentsGreeted[id] == false) {
			try {
				wait();
			} catch (InterruptedException e) { }
			System.out.println("Student "+id+" wants to leave");
		}
		System.out.println("I want out "+id);		
	}
	
	public synchronized int getStudentBeingAnswered() {
		return studentBeingAnswered;
	}
	
}
