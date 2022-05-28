/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clientSide.main;

import clientSide.entities.Chef;
import clientSide.stubs.*;
import genclass.GenericIO;

/**
 * Client side of the Assignment 2 - Chef. Static solution Attempt (number of
 * threads controlled by global constants - ExecConst) Implementation of a
 * client-server model of type 2 (server replication). Communication is based on
 * a communication channel under the TCP protocol.
 */

public class ClientChefMain {

	/**
	 * Main method.
	 *
	 * @param args runtime arguments
	 */
	public static void main(String[] args) {
		String barServerHostName;                               // name of the platform where is located the barber shop server
	    int barServerPortNum = -1;                             // port number for listening to service requests
	    String kitchenServerHostName;                                 // name of the platform where is located the kitchen server
	    int kitchenServerPortNum = -1;                               // port number for listening to service requests
	    BarStub bar;													// remote reference to the bar
		KitchenStub kitchen;
		String reposServerName;                                 // name of the platform where is located the general repository server
	    int reposServerPortNum = -1;
	    GeneralReposStub reposStub;
		
		Chef chef;
	
	    /* getting problem runtime parameters */
	
	    if (args.length != 6) {
	    	GenericIO.writelnString("Wrong number of parameters!");
	        System.exit (1);
	    }
	    
	    barServerHostName = args[0];
	    try {
	    	barServerPortNum = Integer.parseInt (args[1]);
	    }
	    catch (NumberFormatException e) {
	    	GenericIO.writelnString ("args[1] is not a number!");
	        System.exit (1);
	    }
	    if ((barServerPortNum < 4000) || (barServerPortNum >= 65536)) {
	    	GenericIO.writelnString ("args[1] is not a valid port number!");
	        System.exit(1);
	    }
	      
	    kitchenServerHostName = args[2];
	    try {
	    	kitchenServerPortNum = Integer.parseInt(args[3]);
	    } catch(NumberFormatException e) {
	    	GenericIO.writelnString("args[3] is not a valid port number!");
	    	System.exit(1);
	    }
	    if ((kitchenServerPortNum < 4000) || (kitchenServerPortNum >= 65536)) {
		    GenericIO.writelnString("args[3] is not a valid port number!");
		    System.exit (1);
		}
	    
	    reposServerName = args[4];
	    try {
	    	reposServerPortNum = Integer.parseInt(args[5]);
	    } catch(NumberFormatException e) {
	    	GenericIO.writelnString("args[3] is not a valid port number!");
	    	System.exit(1);
	    }
	    if ((reposServerPortNum < 4000) || (reposServerPortNum >= 65536)) {
		    GenericIO.writelnString("args[3] is not a valid port number!");
		    System.exit (1);
		}
		
	    
		
		//Initialization
		
		
		bar = new BarStub(barServerHostName, barServerPortNum);
		kitchen = new KitchenStub(kitchenServerHostName, kitchenServerPortNum);
		reposStub = new GeneralReposStub(reposServerName, reposServerPortNum);
		
		chef = new Chef("Chef", kitchen, bar);
		
		
		// Start of simulation
		chef.start();
        GenericIO.writelnString("Chef thread"+Thread.currentThread().getName()+" Started");


        try {
            chef.join();
        } catch(InterruptedException e) {
        	e.printStackTrace();
        }
        
        bar.shutdown();
        kitchen.shutdown();
        reposStub.shutdown();
	}
}
