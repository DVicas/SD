package serverSide.main;

import java.net.*;

import commInfra.*;
import serverSide.sharedRegions.*;


public class KitchenMain {
	
	/**
	 *  Flag signaling the service is active.
	 */
	
	public static boolean waitConnection;
	
	
	/**
	 *  Main method.
	 *
	 *    @param args runtime arguments 
	 *        args[0] - port nunber for listening to service requests
	 *        args[1] - name of the platform where is located the server for the general repository
	 *        args[2] - port nunber where the server for the general repository is listening to service requests
	 */
	
	public static void main(String[] args) {
		Kitchen kitchen;                                              // kitchen (service to be rendered)
		KitchenInterface kitchenInter;                                // interface to the kitchen
		GeneralReposStub reposStub;                                    // stub to the general repository
		ServerCom scon, sconi;                                         // communication channels
		int portNumb = -1;                                             // port number for listening to service requests
		String reposServerName;                                        // name of the platform where is located the server for the general repository
		int reposPortNumb = -1;                                        // port nunber where the server for the general repository is listening to service requests
	
		if (args.length != 3)
		{ GenericIO.writelnString ("Wrong number of parameters!");
	    	System.exit (1);
	    }
		try
		{ portNumb = Integer.parseInt (args[0]);
		}
		catch (NumberFormatException e)
		{ GenericIO.writelnString ("args[0] is not a number!");
	    	System.exit (1);
		}
		if ((portNumb < 4000) || (portNumb >= 65536))
	    { GenericIO.writelnString ("args[0] is not a valid port number!");
	     	System.exit (1);
	    }
		reposServerName = args[1];
		try
		{ reposPortNumb = Integer.parseInt (args[2]);
		}
		catch (NumberFormatException e)
		{ GenericIO.writelnString ("args[2] is not a number!");
	    	System.exit (1);
		}
		if ((reposPortNumb < 4000) || (reposPortNumb >= 65536))
	    { GenericIO.writelnString ("args[2] is not a valid port number!");
	    	System.exit (1);
	    }
	
		/* service is established */
	
		reposStub = new GeneralReposStub (reposServerName, reposPortNumb);       // communication to the general repository is instantiated
		kitchen = new Kitchen (reposStub);                                      // service is instantiated
		kitchenInter = new KitchenInterface (kitchen);                            // interface to the service is instantiated
		scon = new ServerCom (portNumb);                                         // listening channel at the public port is established
		scon.start ();
		GenericIO.writelnString ("Service is established!");
		GenericIO.writelnString ("Server is listening for service requests.");
	
		/* service request processing */
	
		KitchenClientProxy cliProxy;                                // service provider agent
	
		waitConnection = true;
		while (waitConnection)
		{ try
	    { sconi = scon.accept ();                                    // enter listening procedure
	    cliProxy = new KitchenClientProxy (sconi, kitchenInter);    // start a service provider agent to address
	    cliProxy.start ();                                         //   the request of service
	    }
	    catch (SocketTimeoutException e) {}
		}
		scon.end ();                                                   // operations termination
		GenericIO.writelnString ("Server was shutdown.");
	}
}