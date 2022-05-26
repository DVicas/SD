/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverSide.sharedRegions;

import serverSide.main.*;
import serverSide.entities.*;
import clientSide.entities.*;
import commInfra.*;


/**
 *  Interface to the Bar.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    Bar and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class BarInterface {
  /**
   *  Reference to the bar.
   */

   private final Bar bar;

  /**
   *  Instantiation of an interface to the bar.
   *
   *    @param bar reference to the bar
   */

   public BarInterface (Bar bar)
   {
      this.bar = bar;
   }
   
   /**
   *  Processing of the incoming messages.
   *
   *  Validation, execution of the corresponding method and generation of the outgoing message.
   *
   *    @param inMessage service request
   *    @return service reply
   *    @throws MessageException if the incoming message is not valid
   */
   
   
   public Message processAndReply (Message inMessage) throws MessageException
   {
        Message outMessage = null;                                     // outgoing message

        // validation of the incoming message 

        switch (inMessage.getMsgType ())
        {   case MessageType.REQCUTH -> {
            if ((inMessage.getCustId () < 0) || (inMessage.getCustId () >= ExecConst.Nstudents))
                throw new MessageException ("Invalid customer id!", inMessage);
            else if ((inMessage.getCustState () < CustomerStates.DAYBYDAYLIFE) || (inMessage.getCustState () > CustomerStates.CUTTHEHAIR))
                throw new MessageException ("Invalid customer state!", inMessage);
           }
            case MessageType.SLEEP -> {
                if ((inMessage.getBarbId () < 0) || (inMessage.getBarbId () >= SimulPar.M))
                    throw new MessageException ("Invalid barber id!", inMessage);
           }
            case MessageType.CALLCUST -> {
                if ((inMessage.getBarbId () < 0) || (inMessage.getBarbId () >= SimulPar.M))
                    throw new MessageException ("Invalid barber id!", inMessage);
                else if ((inMessage.getBarbState () < BarberStates.SLEEPING) || (inMessage.getBarbState () > BarberStates.INACTIVITY))
                    throw new MessageException ("Invalid barber state!", inMessage);
           }
            case MessageType.RECPAY -> {
                if ((inMessage.getBarbId () < 0) || (inMessage.getBarbId () >= SimulPar.M))
                    throw new MessageException ("Invalid barber id!", inMessage);
                else if ((inMessage.getBarbState () < BarberStates.SLEEPING) || (inMessage.getBarbState () > BarberStates.INACTIVITY))
                    throw new MessageException ("Invalid barber state!", inMessage);
                else if ((inMessage.getCustId () < 0) || (inMessage.getCustId () >= SimulPar.N))
                    throw new MessageException ("Invalid customer id!", inMessage);
           }
            case MessageType.ENDOP -> {
                if ((inMessage.getBarbId () < 0) || (inMessage.getBarbId () >= SimulPar.M))
                    throw new MessageException ("Invalid barber id!", inMessage);
           }
            case MessageType.SHUT -> {
           }
            default -> throw new MessageException ("Invalid message type!", inMessage);
        }
       // check nothing

        // processing 

        switch (inMessage.getMsgType ())

        {   case MessageType.REQCUTH:  ((BarClientProxy) Thread.currentThread ()).setCustomerId (inMessage.getCustId ());
                                   ((BarClientProxy) Thread.currentThread ()).setCustomerState (inMessage.getCustState ());
                                   if (bShop.goCutHair ())
                                      outMessage = new Message (MessageType.CUTHDONE,
                                                                ((BarClientProxy) Thread.currentThread ()).getCustomerId (),
                                                                ((BarClientProxy) Thread.currentThread ()).getCustomerState ());
                                      else outMessage = new Message (MessageType.BSHOPF,
                                                                     ((BarClientProxy) Thread.currentThread ()).getCustomerId (),
                                                                     ((BarClientProxy) Thread.currentThread ()).getCustomerState ());
                                   break;
            case MessageType.SLEEP:    ((BarClientProxy) Thread.currentThread ()).setBarberId (inMessage.getBarbId ());
                                   if (bShop.goToSleep ())
                                      outMessage = new Message (MessageType.SLEEPDONE,
                                                                ((BarClientProxy) Thread.currentThread ()).getBarberId (), true);
                                      else outMessage = new Message (MessageType.SLEEPDONE,
                                                                     ((BarClientProxy) Thread.currentThread ()).getBarberId (), false);
                                   break;
            case MessageType.CALLCUST: ((BarClientProxy) Thread.currentThread ()).setBarberId (inMessage.getBarbId ());
                                   ((BarClientProxy) Thread.currentThread ()).setBarberState (inMessage.getBarbState ());
                                   int custId = bShop.callACustomer ();
                                   outMessage = new Message (MessageType.CCUSTDONE,
                                                             ((BarClientProxy) Thread.currentThread ()).getBarberId (),
                                                             ((BarClientProxy) Thread.currentThread ()).getBarberState (), custId);
                                   break;
            case MessageType.RECPAY:   ((BarClientProxy) Thread.currentThread ()).setBarberId (inMessage.getBarbId ());
                                   ((BarClientProxy) Thread.currentThread ()).setBarberState (inMessage.getBarbState ());
                                   bShop.receivePayment (inMessage.getCustId ());
                                   outMessage = new Message (MessageType.RPAYDONE,
                                                             ((BarClientProxy) Thread.currentThread ()).getBarberId (),
                                                             ((BarClientProxy) Thread.currentThread ()).getBarberState ());
                                   break;
            case MessageType.ENDOP:    bShop.endOperation (inMessage.getBarbId ());
                                   outMessage = new Message (MessageType.EOPDONE, inMessage.getBarbId ());
                                   break;
            case MessageType.SHUT:     bShop.shutdown ();
                                   outMessage = new Message (MessageType.SHUTDONE);
                                   break;
        }

        return (outMessage);
    }
   
}
