package commInfra;

/**
 * Type of the exchanged messages. the Implementation of a client-server model
 * of type 2 (server replication). Communication is based on a communication
 * channel under the TCP protocol.
 */

public class MessageType {
	/**
	 * Initialization of the logging file name and the number of iterations (service
	 * request).
	 */

	public static final int SETNFIC = 1;

	/**
	 * Logging file was initialized (reply).
	 */

	public static final int NFICDONE = 2;

	////////////// STUDENT TYPES ///////////////

	/**
	 * enter(request).
	 */

	public static final int REQENT = 3;

	/**
	 * enter done (reply).
	 */

	public static final int ENTDONE = 4;

	/**
	 * Call waiter (request).
	 */

	public static final int REQCW = 5;

	/**
	 * Call waiter done (reply).
	 */

	public static final int CWDONE = 6;

	/**
	 * Signal waiter (request).
	 */

	public static final int REQSW = 7;

	/**
	 * Signal waiter done (reply).
	 */

	public static final int SWDONE = 8;

	/**
	 * EXIT REQUEST (REQUEST).
	 */

	public static final int REQEXIT = 9;

	/**
	 * EXIT DONE (REPLY)
	 */

	public static final int EXITDONE = 10;
	
	/**
	 * seatAtTable (REQUEST)
	 */

	public static final int REQSAT = 11;

	/**
	 * seatAtTable (REPLY)
	 */

	public static final int SATDONE = 12;

	/**
	 * readMenu (REQUEST)
	 */

	public static final int REQRM = 13;

	/**
	 * readMenu (REPLY)
	 */

	public static final int RMDONE = 60;
	
	/**
	 * getFirstToArrive (REQUEST)
	 */

	public static final int REQGFTA = 53;

	/**
	 * getFirstToArrive (REPLY)
	 */

	public static final int GFTADONE = 54;
	
	/**
	 * prepareOrder (REQUEST)
	 */

	public static final int REQPO = 61;

	/**
	 * prepareOrder (REPLY)
	 */

	public static final int PODONE = 62;
	
	/**
	 * everybodyHasChosen (REQUEST)
	 */

	public static final int REQEHC = 63;

	/**
	 * everybodyHasChosen (REPLY)
	 */

	public static final int EHCDONE = 64;
	
	/**
	 * addUpOnesChoices (REQUEST)
	 */

	public static final int REQAUOC = 65;

	/**
	 * addUpOnesChoices (REPLY)
	 */

	public static final int AUOCDONE = 66;

	/**
	 * describeOrder (REQUEST)
	 */

	public static final int REQDO = 67;

	/**
	 * describeOrder (REPLY)
	 */

	public static final int DODONE = 68;
	
	/**
	 * joinTalk (REQUEST)
	 */

	public static final int REQJT = 69;

	/**
	 * joinTalk (REPLY)
	 */

	public static final int JTDONE = 70;

	/**
	 * informCompanion (REQUEST)
	 */

	public static final int REQIC = 71;

	/**
	 * informCompanion (REPLY)
	 */

	public static final int ICDONE = 72;
	
	/**
	 * haveAllCoursesBeenEaten (REQUEST)
	 */

	public static final int REQHACBE = 81;

	/**
	 * haveAllCoursesBeenEaten (REPLY)
	 */

	public static final int HACBEDONE = 82;
	
	/**
	 * startEating (REQUEST)
	 */

	public static final int REQSE = 73;

	/**
	 * startEating (REPLY)
	 */

	public static final int SEDONE = 74;

	/**
	 * endEating (REQUEST)
	 */

	public static final int REQEE = 75;

	/**
	 * endEating (REPLY)
	 */

	public static final int EEDONE = 76;
	
	/**
	 * hasEverybodyFinishedEating (REQUEST)
	 */

	public static final int REQHEFE = 77;

	/**
	 * hasEverybodyFinishedEating (REPLY)
	 */

	public static final int HEFEDONE = 78;
	
	/**
	 * honourBill (REQUEST)
	 */

	public static final int REQHB = 79;

	/**
	 * honourBill (REPLY)
	 */

	public static final int HBDONE = 80;

	/**
	 * shouldHaveArrivedEarlier (REQUEST)
	 */

	public static final int REQSHAE = 83;

	/**
	 * shouldHaveArrivedEarlier (REPLY)
	 */

	public static final int SHAEDONE = 84;
	
	/**
	 * getLastToEat (REQUEST)
	 */

	public static final int REQGLTE = 55;

	/**
	 * getLastToEat (REPLY)
	 */

	public static final int GLTEDONE = 56;

	
	//////////// WAITER TYPES /////////////
	
	/**
	 * LOOK AROUND (REQUEST)
	 */

	public static final int REQLA = 13;

	/**
	 * LOOK AROUND (REPLY)
	 */

	public static final int LADONE = 14;

	/**
	 * SAY GOODBYE (REQUEST)
	 */

	public static final int REQSG = 15;

	/**
	 * SAY GOODBYE (REPLY)
	 */

	public static final int SGDONE = 16;

	/**
	 * PREPARE BILL (REQUEST)
	 */

	public static final int REQPB = 17;

	/**
	 * PREPARE BILL (REPLY)
	 */

	public static final int PBDONE = 18;
	
	/**
	 * saluteClient (REQUEST)
	 */

	public static final int REQSC = 19;

	/**
	 * saluteClient (REPLY)
	 */

	public static final int SCDONE = 20;

	/**
	 * getStudentBeingAnswered (REQUEST)
	 */

	public static final int REQGSBA = 85;

	/**
	 * getStudentBeingAnswered (REPLY)
	 */

	public static final int GSBADONE = 86;
	
	/**
	 * returnToBar (REQUEST)
	 */

	public static final int REQRTB = 35;

	/**
	 * returnToBar (REPLY)
	 */

	public static final int RTBDONE = 36;
	/**
	 * getThePad (REQUEST)
	 */

	public static final int REQGB = 45;

	/**
	 * getThePad (REPLY)
	 */

	public static final int GBDONE = 46;

	/**
	 * handNoteToChef (REQUEST)
	 */

	public static final int REQHNTC = 37;

	/**
	 * handNoteToChef (REPLY)
	 */

	public static final int HNTCDONE = 38;

	/**
	 * haveAllClientsBeenServed (REQUEST)
	 */

	public static final int REQHACBS = 47;

	/**
	 * haveAllClientsBeenServed (REPLY)
	 */

	public static final int HACBSDONE = 48;
	
	/**
	 * collectPortion (REQUEST)
	 */

	public static final int REQCPOR = 39;

	/**
	 * collectPortion (REPLY)
	 */

	public static final int CPORDONE = 40;

	/**
	 * deliverPortion (REQUEST)
	 */

	public static final int REQDP = 49;

	/**
	 * deliverPortion (REPLY)
	 */

	public static final int DPDONE = 50;
	
	/**
	 * presentBill (REQUEST)
	 */

	public static final int REQPREB = 51;

	/**
	 * presentBill (REPLY)
	 */

	public static final int PREBDONE = 52;
	
	//////////// CHEF TYPES /////////////
	
	/**
	 * watch the news (request)
	 */

	public static final int REQWTN = 19;

	/**
	 * watch the news (reply)
	 */

	public static final int WTNDONE = 20;

	/**
	 * START PREPARATION (REQUEST)
	 */

	public static final int REQSTP = 21;

	/**
	 * START PREPARATION (REPLY)
	 */

	public static final int STPDONE = 22;

	/**
	 * PROCEED PRESENTATION (REQUEST)
	 */

	public static final int REQPTP = 23;

	/**
	 * PROCEED PRESENTATION (REPLY)
	 */

	public static final int PTPDONE = 24;

	/**
	 * haveNextPortionReady (REQUEST)
	 */

	public static final int REQHNPR = 25;

	/**
	 * haveNextPortionReady (REPLY)
	 */

	public static final int HNPRDONE = 26;

	/**
	 * continuePreparation (REQUEST)
	 */

	public static final int REQCP = 27;

	/**
	 * continuePreparation (REPLY)
	 */

	public static final int CPDONE = 28;

	/**
	 * haveAllPortionsBeenDelivered (REQUEST)
	 */

	public static final int REQHAPBD = 29;

	/**
	 * haveAllPortionsBeenDelivered (REPLY)
	 */

	public static final int HAPBDDONE = 30;

	/**
	 * hasOrderBeenCompleted (REQUEST)
	 */

	public static final int REQHOBC = 31;

	/**
	 * hasOrderBeenCompleted (REPLY)
	 */

	public static final int HOBCDONE = 32;

	/**
	 * cleanUp (REQUEST)
	 */

	public static final int REQCU = 33;

	/**
	 * cleanUp (REPLY)
	 */

	public static final int CUDONE = 34;

	/**
	 * Alert waiter (service request).
	 */

	public static final int REQAL = 3;

	/**
	 * Waiter was alerted (reply).
	 */

	public static final int ALDONE = 4;

	

	
	/**
	 * returnBar (REQUEST)
	 */

	public static final int REQRB = 43;

	/**
	 * returnBar (REPLY)
	 */

	public static final int RBDONE = 44;

	
	

	

	

	
	
	

	

	

	
	

	
	

	

	

	


	/**
	 * Server shutdown (service request).
	 */

	public static final int SHUT = 87;

	/**
	 * Server was shutdown (reply).
	 */

	public static final int SHUTDONE = 88;

	
	/////////// GENERAL REPOS ////////////
	
	/**
	 * setStudentState (REQUEST).
	 */

	public static final int STSST = 89;

	/**
	 * setWaiterState (REQUEST).
	 */

	public static final int STWST = 90;

	/**
	 * setChefState (REQUEST).
	 */

	public static final int STCST = 91;

	/**
	 * Setting acknowledged (reply).
	 */

	public static final int SACK = 92;
}
