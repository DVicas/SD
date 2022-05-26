package clientSide.entities;

import clientSide.stubs.*;

public class Student extends Thread {
	
	private int studentId;
	private int studentState;
	
	private TableStub tableStub;
	private BarStub barStub;
	
	public Student(String name, int studentId, TableStub table, BarStub bar) {
		super(name);
		this.studentId = studentId;
		this.tableStub = table;
		this.barStub = bar;
		studentState = StudentStates.GOING_TO_THE_RESTAURANT;
	}
	
	public int getStudentId() { return studentId; }
	
	public int getStudentState() { return studentState; }
	
	public void setStudentState(int studentState) { this.studentState = studentState; } 
	
	public void walkABit() {
		try
	      { sleep ((long) (1 + 40 * Math.random ()));
	      }
	      catch (InterruptedException e) {}
	}
	
	@Override
	public void run() {
		
		walkABit();
		barStub.enter();
		tableStub.seatAtTable();
		tableStub.readTheMenu();
		
		if (tableStub.getFirstStudent() == studentId) {
			tableStub.prepareOrder();							
			
			while(!tableStub.hasEverybodyChosen()) {
				tableStub.addUpOnesChoices();
			}
			
			barStub.callWaiter();
			tableStub.describeOrder();
			tableStub.joinTalk();
		} else {
			tableStub.informCompanion();
		}
		
		while(!tableStub.everyoneHasEaten()) {
			
			tableStub.startEating();
			tableStub.endEating();
			
			while(!tableStub.hasEveryoneFinishedPortion());
			
			if(tableStub.getLastToEat() == studentId) {
				barStub.signalWaiter();
			}
		}
		
		if(tableStub.shouldHaveArrivedEarlier()) {
			barStub.signalWaiter();
			tableStub.honourTheBill();
		}
		
		barStub.exit();
	}
	
}
