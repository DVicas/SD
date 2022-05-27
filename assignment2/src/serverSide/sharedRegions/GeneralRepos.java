/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverSide.sharedRegions;

import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;
import genclass.TextFile;
import java.util.Objects;


/**
 * 
 * 
 */



/**
 *  General Repository.
 *
 *    It is responsible to keep the visible internal state of the problem and to provide means for it
 *    to be printed in the logging file.
 *    It is implemented using semaphores for synchronization.
 *    All public methods are executed in mutual exclusion.
 *    There are no internal synchronization points.
 */
public class GeneralRepos {
   /**
   *  Name of the logging file.
   */
   private final String logFileName;
  /**
   *  Number of portions served.
   */
   private int nPortions;
  /**
   *  Number of Courses served.
   */
   private int nCourses;

  /**
   *  State of the Students.
   */
   private final int [] studentState;

  /**
   *  State of the Chef.
   */
   private int chefState;
   
  /**
   *  State of the Waiter.
   */
   private int waiterState;
   
  /**
   *  seat where each student is.
   */
   private final int [] seatAtTable = new int[SimulPar.N];
   
  /**
   *   Instantiation of a general repository object.
   *
   *     @param logFileName name of the logging file
   */
    public GeneralRepos ()
    {
        logFileName = "logger";


        // inicializar students
        studentState = new int[SimulPar.N];
        for (int i = 0; i < SimulPar.N; i++)
          studentState[i] = StudentStates.GOING_TO_THE_RESTAURANT;

        // iniciar chef
        chefState = ChefStates.WAITING_FOR_AN_ORDER;
        // iniciar waiter
        waiterState = WaiterStates.APPRAISING_SITUATION;
        // iniciar todos os seats a -1 para indicar que nao tem ninguem sentado
        for(int i = 0; i < SimulPar.N;i++)
        {
            seatAtTable[i] = -1;
        }

        reportInitialStatus ();
    }
      
  /**
   *  Print header.
   */
   private void reportInitialStatus ()
   {
      TextFile log = new TextFile ();                      // instantiation of a text file handler

      if (!log.openForWriting (".", logFileName))
         { GenericIO.writelnString ("The operation of creating the file " + logFileName + " failed!");
           System.exit (1);
         }
      log.writelnString ("\t\t\t\t\t\t  The Restaurant - Description of the internal state");
      //log.writelnString ("\nNumber of iterations = " + nIter + "\n");
      log.writelnString ("\n\tChef\tWaiter\tStu0\tStu1\tStu2\tStu3\tStu4\tStu5\tStu6\tNCourse\tNPortion\t\t\tTable\n");
      log.writelnString ("\n\tState\tState\tState\tState\tState\tState\tState\tState\tState\t\t\tSeat0\tSeat1\tSeat2\tSeat3\tSeat4\tSeat5\tSeat6\n");
      if (!log.close ())
         { GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
           System.exit (1);
         }
      reportStatus ();
   }
  /**
   *  Write the body of the logging file.
   *
   */
   private void reportStatus ()
   {
        TextFile log = new TextFile ();                      // instantiation of a text file handler

        String lineStatus = "";                              // state line to be printed

        if (!log.openForAppending (".", logFileName))
           { GenericIO.writelnString ("The operation of opening for appending the file " + logFileName + " failed!");
             System.exit (1);
           }
        switch (chefState){ 
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
        switch (waiterState){ 
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
        for (int i = 0; i < SimulPar.N; i++)
            switch (studentState[i]){ 
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
        lineStatus += "\t"+nCourses+"\t"+nPortions;
        
        for(int i = 0;i< SimulPar.N; i++)
        {
            lineStatus += "\t";
            if(seatAtTable[i] == -1){
                lineStatus += "-";
            }
            else
            {
                lineStatus += seatAtTable[i];
            }
        }
     
      log.writelnString (lineStatus);
      if (!log.close ())
         { GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
           System.exit (1);
         }
   }
  /**
   *   Set Chef state.
   *
   *     @param state chef state
   */
    void setChefState(int state) {
        this.chefState = state;
        reportStatus ();
    }
  /**
   *   Set Waiter state.
   *
   *     @param state waiter state
   */
    void setWaiterState(int state) {
        waiterState = state;
        reportStatus ();
    }
  /**
   *   Set student state.
   *
   *     @param studentId student id
   *     @param state state
   */
    void setStudentState(int studentId, int state) {
        this.studentState[studentId] = state;
        reportStatus ();
    }
  /**
   *   Set number of portions.
   *
   *     @param nPortionsDelivered number of portions delivered
   */
    void setnPortions(int nPortionsDelivered) {
        nPortions = nPortionsDelivered;
    }
  /**
   *   Set number of Courses.
   *
   *     @param setnCourses number of courses
   */
    void setnCourses(int i) {
        nCourses = i;
    }
  /**
   *   update who is seated at the table.
   *
   *     @param nStudentAtRes number of students at the restaurant
   *     @param studentId student id
   */
    void updateSeatsAtTable(int nStudentsAtRes, int studentId) {
        seatAtTable[nStudentsAtRes] = studentId;
    }
}