package clientSide.entities;

import sharedRegions.Bar;

import sharedRegions.Table;

public class Student extends Thread {
	
	private int studentId;
	private int studentState;
	
	private Table table;
	private Bar bar;
	
	public Student(String name, int studentId, Table table, Bar bar) {
		super(name);
		this.studentId = studentId;
		this.table = table;
		this.bar = bar;
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
		bar.enter();
		table.seatAtTable();
		table.readTheMenu();
		
		if (table.getFirstStudent() == studentId) {
			table.prepareOrder();							
			
			while(!table.hasEverybodyChosen()) {
				table.addUpOnesChoice();
			}
			
			bar.callWaiter();
			table.describeOrder();
			table.joinTalk();
		} else {
			table.informCompanion();
		}
		
		while(!table.everyoneHasEaten()) {
			
			table.startEating();
			table.endEating();
			
			while(!table.hasEveryoneFinishedPortion());
			
			if(table.lastToEat() == studentId) {
				bar.signalWaiter();
			}
		}
		
		if(table.shouldHaveArrivedEarlier()) {
			bar.signalWaiter();
			table.honorTheBill();
		}
		
		bar.exit();
	}
	
}
