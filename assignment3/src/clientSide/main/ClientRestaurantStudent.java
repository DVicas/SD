package clientSide.main;

import java.rmi.*;
import java.rmi.registry.*;
import clientSide.entities.*;
import interfaces.*;
import serverSide.main.*;
import genclass.GenericIO;

public class ClientRestaurantStudent {

	/**
	 *	Main method. 
	 * 		@param args runtime arguments
	 * 			args[0] - name of the platform where is located the RMI registering service
	 * 			args[1] - port number where the registering service is listening to service requests 
	 */	
	public static void main(String[] args) 
	{
		//Name of the platform where is located the RMI registering service
		String rmiRegHostName;
		//Port number where the registering service is listening to service request
		int rmiRegPortNumb = -1;
		
		/* getting problem runtime parameters */
		
		if(args.length != 2) 
		{
			GenericIO.writelnString ("Wrong number of parameters!");
			System.exit (1);			
		}
		rmiRegHostName = args[0];
		
		try { 
			rmiRegPortNumb = Integer.parseInt(args[1]); 
		} catch(NumberFormatException e) {
			GenericIO.writelnString ("args[1] is not a valid port number!");
			System.exit (1);			
		}
		
		
		/* problem initialisation */

		String nameEntryGeneralRepos = "GeneralRepository";	//Public name of the General Repository object
		String nameEntryBar = "Bar";						//Public name of the Bar object
		String nameEntryTable = "Table";					//Public name of the Table object
		GeneralReposInterface reposStub = null;				//Remote reference to the General Repository object
		BarInterface barStub = null;						//Remote reference for the Bar object
		TableInterface tabStub = null;						//Remote reference for the Table object
		Registry registry = null;							//Remote reference for registration in the RMI Registry service
		Student[] student = new Student[SimulPar.N];	//Array of student Threads
	
		
		//Locate RMI Registry server
		try {
			registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
		} catch (RemoteException e) {
			GenericIO.writelnString ("RMI registry creation exception: " + e.getMessage ());
	        e.printStackTrace ();
	        System.exit (1);			
		}

		//Locate GeneralRepos Server
		try {
			reposStub = (GeneralReposInterface) registry.lookup(nameEntryGeneralRepos);
		} catch( RemoteException e ) {
			GenericIO.writelnString ("General Repository lookup exception: " + e.getMessage ());
	        e.printStackTrace ();
	        System.exit (1);			
		} catch( NotBoundException e ) {
			GenericIO.writelnString ("General Repository not bound exception: " + e.getMessage ());
	        e.printStackTrace ();
	        System.exit (1);			
		}
		
		
		//Locate Bar Server
		try {
			barStub = (BarInterface) registry.lookup(nameEntryBar);
		} catch( RemoteException e ) {
			GenericIO.writelnString ("Bar lookup exception: " + e.getMessage ());
	        e.printStackTrace ();
	        System.exit (1);			
		} catch( NotBoundException e ) {
			GenericIO.writelnString ("Bar not bound exception: " + e.getMessage ());
	        e.printStackTrace ();
	        System.exit (1);			
		}

		//Locate Table Server
		try {
			tabStub = (TableInterface) registry.lookup(nameEntryTable);
		} catch( RemoteException e ) {
			GenericIO.writelnString ("Table lookup exception: " + e.getMessage ());
	        e.printStackTrace ();
	        System.exit (1);			
		} catch( NotBoundException e ) {
			GenericIO.writelnString ("Table not bound exception: " + e.getMessage ());
	        e.printStackTrace ();
	        System.exit (1);			
		}
		
		for(int i=0; i < SimulPar.N; i++)
			student[i] = new Student("student_"+(i+1), i, tabStub, barStub);
		
		/* start of the simulation */
		for(int i=0; i < SimulPar.N; i++)
			student[i].start();
		
		/* waiting for the end of the simulation */
		for(int i=0; i < SimulPar.N; i++)
		{
			try 
			{ student[i].join();
			}
			catch(InterruptedException e) {}
			GenericIO.writelnString ("The student "+(i+1) + " has terminated.");
		}
		
		
		try {
			tabStub.shutdown();
		} catch(RemoteException e) {
			GenericIO.writelnString ("Chef generator remote exception on Table shutdown: " + e.getMessage ());
	        System.exit (1);			
		}
		
		try {
			barStub.shutdown();
		} catch(RemoteException e) {
			GenericIO.writelnString ("Chef generator remote exception on Kitchen shutdown: " + e.getMessage ());
	        System.exit (1);			
		}
		
		try {
			reposStub.shutdown();
		} catch(RemoteException e) {
			GenericIO.writelnString ("Chef generator remote exception on GeneralRepos shutdown: " + e.getMessage ());
	        System.exit (1);			
		}
	}
}
