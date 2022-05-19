package sharedRegions;

import entities.*;
import genclass.GenericIO;
import genclass.TextFile;
import main.SimulPar;

public class GeneralRepos {

	private final String file;

	private int [] studentStates;
	
	private int [] studentSeats;
	
	private int waiterState;
	
	private int chefState;	
	
	private int nPortions;
	
	private int nCourses;

	public GeneralRepos(String file){
		this.file = file;
		
		
		studentStates = new int[SimulPar.N];
		studentSeats = new int[SimulPar.N];
		for(int i=0 ; i<SimulPar.N ; i++) {
			studentStates[i] = StudentStates.GOING_TO_THE_RESTAURANT;
			studentSeats[i] = -1;
		}
		waiterState = WaiterStates.APPRAISING_SITUATION;
		chefState = ChefStates.WAITING_FOR_AN_ORDER;
		nPortions = 0;
		nCourses = 0;
		
		reportInitialStatus();
	}
	
	
	public synchronized void updateStudentState(int studentID, int studentState) {
		studentStates[studentID] = studentState;
		reportStatus();
	}
	
	
	public synchronized void updateStudentSeat(int studentID, int studentSeat) {
		studentSeats[studentID] = studentSeat;
	}
	
	
	public synchronized void updateWaiterState(int newWaiterState) {
		waiterState = newWaiterState;
		reportStatus();
	}
	
	
	public synchronized void updateChefState(int newChefState) {
		chefState = newChefState;
		reportStatus();
	}
	
	public synchronized void setnPortions(int nPortions) {
		this.nPortions = nPortions;
	}
	
	public synchronized void setnCourses(int nCourses) {
		this.nCourses = nCourses;
	}
	
	
   private void reportInitialStatus()
   {
      TextFile log = new TextFile();                      

      if (!log.openForWriting(".", file))
         { GenericIO.writelnString("The operation of creating the file " + file + " failed!");
           System.exit(1);
         }
      log.writelnString("\t\t\t\t\t\t  The Restaurant - Description of the internal state");
      log.writelnString("\n\tChef\tWaiter\tStu0\tStu1\tStu2\tStu3\tStu4\tStu5\tStu6\tNCourse\tNPortion\t\t\t\t\t\t\tTable\n");
      log.writelnString("\tState\tState\tState\tState\tState\tState\tState\tState\tState\t\t\t\t\t  Seat0\t  Seat1\t  Seat2\t  Seat3\t  Seat4\t  Seat5\t  Seat6\n");
      if (!log.close())
         { GenericIO.writelnString("The operation of closing the file " + file + " failed!");
           System.exit(1);
         }
      reportStatus();
   }
  
   private void reportStatus()
   {
		TextFile log = new TextFile();                      
		
		String lineStatus = "";                              
		
		if (!log.openForAppending(".", file))
			{ GenericIO.writelnString("The operation of opening for appending the file " + file + " failed!");
		     System.exit(1);
			}
		
		switch(chefState){ 
    		case ChefStates.WAITING_FOR_AN_ORDER:  lineStatus += "\tWAFOR";
    			break;
    		case ChefStates.PREPARING_THE_COURSE: lineStatus += "\tPRPCS";
                break;
    		case ChefStates.DISHING_THE_PORTIONS:      lineStatus += "\tDSHPT";
    			break;
    		case ChefStates.DELIVERING_THE_PORTIONS:    lineStatus += "\tDLVPT";
		        break;
    		case ChefStates.CLOSING_SERVICE:    lineStatus += "\tCLSSV";
    			break;
    	}
		
		switch(waiterState){ 
		    case WaiterStates.APPRAISING_SITUATION:  lineStatus += "\tAPPST";
               break;
		    case WaiterStates.PRESENTING_THE_MENU: lineStatus += "\tPRSMN";  
		    	break;
		    case WaiterStates.TAKING_THE_ORDER:      lineStatus += "\tTKODR";
		    	break;
		    case WaiterStates.PLACING_THE_ORDER:    lineStatus += "\tPCODR";
		    	break;
		    case WaiterStates.WAITING_FOR_PORTION:    lineStatus += "\tWTFPT";
		    	break;
		    case WaiterStates.PROCESSING_THE_BILL:    lineStatus += "\tPRCBL";
		    	break;                                              
		    case WaiterStates.RECEIVING_PAYMENT:    lineStatus += "\tRECPM";
		    	break;                                           
		    }
	        
		for(int i = 0; i < SimulPar.N; i++){
            switch (studentStates[i]){ 
                case StudentStates.GOING_TO_THE_RESTAURANT:   lineStatus += "\tGGTRT";
                	break;
                case StudentStates.TAKING_A_SEAT_AT_THE_TABLE: lineStatus += "\tTKSTT";
                	break;    
                case StudentStates.SELECTING_THE_COURSES: lineStatus += "\tSELCS";
                	break; 
                case StudentStates.ORGANIZING_THE_ORDER: lineStatus += "\tOGODR";
                	break; 
                case StudentStates.CHATTING_WITH_COMPANIONS: lineStatus += "\tCHTWC";
                	break; 
                case StudentStates.PAYING_THE_MEAL: lineStatus += "\tPYTML";
                	break; 
            	case StudentStates.ENJOYING_THE_MEAL: lineStatus += "\tEJTML";
        			break; 
                case StudentStates.GOING_HOME: lineStatus += "\tGGHOM";
                	break; 
            }
        }
		lineStatus += "\t\t"+nCourses+"\t\t"+nPortions;
		
		for(int i=0 ; i<SimulPar.N ; i++)
		{
		    lineStatus += "\t\t";
		    if(studentSeats[i] == -1){
		        lineStatus += "-";
		    }
		    else
		    {
		        lineStatus += studentSeats[i];
		    }
		}
	     
		log.writelnString (lineStatus);
		if (!log.close ())
		{ GenericIO.writelnString("The operation of closing the file " + file + " failed!");
		System.exit (1);
		}
	}
}