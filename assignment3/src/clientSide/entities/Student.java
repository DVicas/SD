package clientSide.entities;

import java.rmi.*;
import interfaces.*;
import genclass.GenericIO;

public class Student extends Thread {

	private int studentId;
	private int studentState;

	private final TableInterface tableStub;
	private final BarInterface barStub;

	public Student(String name, int studentId, TableInterface table, BarInterface bar) {
		super(name);
		this.studentId = studentId;
		this.tableStub = table;
		this.barStub = bar;
		this.studentState = StudentStates.GOING_TO_THE_RESTAURANT;
	}

	public int getStudentId() {
		return studentId;
	}

	public int getStudentState() {
		return studentState;
	}

	public void setStudentState(int studentState) {
		this.studentState = studentState;
	}

	public void walkABit() {
		try {
			sleep((long) (1 + 40 * Math.random()));
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void run() {

		walkABit();
		enter();
		seatAtTable();
		readTheMenu();

		if (getFirstStudent() == studentId) {
			prepareOrder();

			while (!hasEverybodyChosen()) {
				addUpOnesChoices();
			}

			callWaiter();
			describeOrder();
			joinTalk();
		} else {
			informCompanion();
		}

		while (!everyoneHasEaten()) {

			startEating();
			
			try {
	            Thread.sleep((long) 250 + (long) Math.random() * 500);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	            throw new RuntimeException(e);
	        }
			
			endEating();

			while (!hasEveryoneFinishedPortion());

			if (getLastToEat() == studentId) {
				signalWaiter();
			}
		}

		if (shouldHaveArrivedEarlier()) {
			signalWaiter();
			honourTheBill();
		}

		exit();
	}
	
	
	
	private void enter() {
		try {
			studentState = barStub.enter(studentId);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	//nao sei qual é que precisa de studentId a ser passado
	
	private void seatAtTable() {
		try {
			studentState = tableStub.seatAtTable(studentId);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void readTheMenu() {
		try {
			studentState = tableStub.readTheMenu(studentId);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private int getFirstStudent() {
		int first = -1;
		
		try {
			first = tableStub.getFirstStudent();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		if (first == -1) {
			GenericIO.writelnString("invalid student id");
			System.exit(-1);
		}
		return first;
	}
	
	private void prepareOrder() {
		try {
			studentState = tableStub.prepareOrder();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private boolean hasEverybodyChosen() {
		boolean b = false;
		
		try {
			b = tableStub.hasEverybodyChosen();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		return b;
	}

	private void addUpOnesChoices() {
		try {
			tableStub.addUpOnesChoice();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void describeOrder() {
		try {
			tableStub.describeOrder();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void joinTalk() {
		try {
			studentState = tableStub.joinTalk();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void informCompanion() {
		try {
			studentState = tableStub.informCompanion(studentId);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private boolean everyoneHasEaten() {
		boolean b = false;
		
		try {
			b = tableStub.everyoneHasEaten();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		return b;
	}
	
	private void startEating() {
		try {
			studentState = tableStub.startEating(studentId);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void endEating() {
		try {
			studentState = tableStub.endEating(studentId);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private boolean hasEveryoneFinishedPortion() {
		boolean b = false;
		
		try {
			b = tableStub.hasEveryoneFinishedPortion();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		return b;
	}

	private int getLastToEat() {
		int last = -1;
		
		try {
			last = tableStub.getLastToEat();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return last;
	}
	
	private boolean shouldHaveArrivedEarlier() {
		ReturnBoolean ret = null;
		
		try {
			ret = tableStub.shouldHaveArrivedEarlier(studentId);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		studentState = ret.getIntStateVal();
		return ret.getBooleanVal();
	}
		
	private void honourTheBill() {
		try {
			tableStub.honourTheBill();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void callWaiter() {
		try {
			barStub.callWaiter(studentId);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void signalWaiter() {
		try {
			barStub.signalWaiter(studentId, studentState);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void exit() {
		try {
			studentState = barStub.exit(studentId);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}	
