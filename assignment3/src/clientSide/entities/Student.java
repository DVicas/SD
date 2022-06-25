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
			barStub.enter();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void seatAtTable() {
		try {
			tableStub.seatAtTable();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void readTheMenu() {
		try {
			tableStub.readTheMenu();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private int getFirstStudent() {
		ReturnInt ret = null;
		
		try {
			ret = tableStub.getFirstStudent();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return ret.getIntVal();
	}
	
	private void prepareOrder() {
		try {
			tableStub.prepareOrder();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private boolean hasEverybodyChosen() {
		ReturnBoolean ret = null;
		
		try {
			ret = tableStub.hasEverybodyChosen();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return ret.getBooleanVal();
	}

	private void addUpOnesChoices() {
		try {
			tableStub.readTheMenu();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void describeOrder() {
		try {
			tableStub.describeOrder();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void joinTalk() {
		try {
			tableStub.joinTalk();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void informCompanion() {
		try {
			tableStub.informCompanion();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private boolean everyoneHasEaten() {
		ReturnBoolean ret = null;
		
		try {
			tableStub.everyoneHasEaten();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return ret.getBooleanVal();
	}
	
	private void startEating() {
		try {
			tableStub.startEating();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void endEating() {
		try {
			tableStub.endEating();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private boolean hasEveryoneFinishedPortion() {
		ReturnBoolean ret = null;
		
		try {
			ret = tableStub.hasEveryoneFinishedPortion();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return ret.getBooleanVal();
	}

	private int getLastToEat() {
		ReturnInt ret = null;
		
		try {
			ret = tableStub.getLastToEat();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return ret.getIntVal();
	}
	
	private boolean shouldHaveArrivedEarlier() {
		ReturnBoolean ret = null;
		
		try {
			tableStub.shouldHaveArrivedEarlier();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return ret.getBooleanVal();
	}
		
	private void honourTheBill() {
		try {
			tableStub.honourTheBill();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void callWaiter() {
		try {
			barStub.callWaiter();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void signalWaiter() {
		try {
			barStub.signalWaiter();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void exit() {
		try {
			barStub.exit();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}	
