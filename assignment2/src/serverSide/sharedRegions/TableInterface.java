/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverSide.sharedRegions;

import commInfra.*;
import serverSide.entities.*;

/**
 *  Interface to the Table.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    Table and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class TableInterface {
    /**
   *  Reference to the table.
   */

   private final Table table;

  /**
   *  Instantiation of an interface to the table.
   *
   *    @param table reference to the kitchen
   */

   public TableInterface (Table table)
   {
      this.table = table;
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
        Message outMessage = null; // mensagem de resposta

        /* validation of the incoming message */

        /* processing */

      //   switch (inMessage.getMsgType()) {
      //       case MessageType.LOOK_AROUND:
      //           if ((inMessage.getWaiterState() < 0) || (inMessage.getWaiterState() > 6))
      //               throw new MessageException("Invalid waiter state!", inMessage);
      //           break;
      //       case MessageType.ENTER:
      //           if ((inMessage.getStudentState() < 0) || (inMessage.getStudentState() > 7))
      //               throw new MessageException("Invalid student state!", inMessage);
      //           if ((inMessage.getStudentID() < 0) || (inMessage.getStudentID() > 6))
      //               throw new MessageException("Invalid student ID!", inMessage);
      //           break;
      //       case MessageType.CALL_THE_WAITER:
      //           if ((inMessage.getStudentState() < 0) || (inMessage.getStudentState() > 7))
      //               throw new MessageException("Invalid student state!", inMessage);
      //           if ((inMessage.getStudentID() < 0) || (inMessage.getStudentID() > 6))
      //               throw new MessageException("Invalid student ID!", inMessage);
      //           break;
      //       case MessageType.ALERT_THE_WAITER:
      //           if ((inMessage.getChefState() < 0) || (inMessage.getChefState() > 4))
      //               throw new MessageException("Invalid chef state!", inMessage);
      //           break;
      //       case MessageType.SIGNAL_THE_WAITER:
      //           if ((inMessage.getStudentState() < 0) || (inMessage.getStudentState() > 7))
      //               throw new MessageException("Invalid student state!", inMessage);
      //           if ((inMessage.getStudentID() < 0) || (inMessage.getStudentID() > 6))
      //               throw new MessageException("Invalid student ID!", inMessage);
      //           break;
      //       case MessageType.SHOULD_HAVE_ARRIVED_EARLIER:
      //           if ((inMessage.getStudentState() < 0) || (inMessage.getStudentState() > 7))
      //               throw new MessageException("Invalid student state!", inMessage);
      //           if ((inMessage.getStudentID() < 0) || (inMessage.getStudentID() > 6))
      //               throw new MessageException("Invalid student ID!", inMessage);
      //           break;
      //       case MessageType.PREPARE_THE_BILL:
      //           if ((inMessage.getWaiterState() < 0) || (inMessage.getWaiterState() > 6))
      //               throw new MessageException("Invalid waiter state!", inMessage);
      //           break;
      //       case MessageType.SAY_GOODBYE:
      //           if ((inMessage.getWaiterState() < 0) || (inMessage.getWaiterState() > 6))
      //               throw new MessageException("Invalid waiter state!", inMessage);
      //           break;
      //       case MessageType.EXIT:
      //           if ((inMessage.getStudentState() < 0) || (inMessage.getStudentState() > 7))
      //               throw new MessageException("Invalid student state!", inMessage);
      //           if ((inMessage.getStudentID() < 0) || (inMessage.getStudentID() > 6))
      //               throw new MessageException("Invalid student ID!", inMessage);
      //           break;
      //       case MessageType.SHUTDOWN:
      //           break;
      //       default:
      //           throw new MessageException("Invalid message type!", inMessage);
//        }

     // processing 

      switch (inMessage.getMsgType ()) { 
      
      	case MessageType.REQSC:  ((TableClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                   table.saluteTheClient()
                                      outMessage = new Message (MessageType.SCDONE,
                                                                ((TableClientProxy) Thread.currentThread ()).getWaiterState ());
                                   break;

         case MessageType.REQRB:  ((TableClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                   table.returnToBar ()
                                      outMessage = new Message (MessageType.RBDONE,
                                                                ((TableClientProxy) Thread.currentThread ()).getWaiterState ());
                                   break;

         case MessageType.REQGB:  ((TableClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                   table.getThePad ()
                                      outMessage = new Message (MessageType.GBDONE,
                                                                ((TableClientProxy) Thread.currentThread ()).getWaiterState ());
                                   break;

         case MessageType.REQHACBS:  ((TableClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                   if (table.haveAllClientsBeenServed())
                                      outMessage = new Message (MessageType.HACBSDONE,
                                                                ((TableClientProxy) Thread.currentThread ()).getWaiterState ());
                                   break;

         case MessageType.REQDP:  ((TableClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                   table.deliverPortion ()
                                      outMessage = new Message (MessageType.DPDONE,
                                                                ((TableClientProxy) Thread.currentThread ()).getWaiterState ());
                                   break;    

         case MessageType.REQPB:  ((TableClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                   table.presentTheBill ()
                                      outMessage = new Message (MessageType.PBDONE,
                                                                ((TableClientProxy) Thread.currentThread ()).getWaiterState ());
                                   break;

         case MessageType.REQGFTA:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
                                    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   table.getFirstStudent ()
                                       outMessage = new Message (MessageType.GTFADONE,
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentId(),
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentState());         

                                   break;
         
         case MessageType.REQGLTE:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
                                    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   table.getLastStudent ()
                                      outMessage = new Message (MessageType.GLTEDONE,
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentId(),
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentState());   
                                   break;

         case MessageType.REQSAT:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
                                    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   table.seatAtTable ()
                                      outMessage = new Message (MessageType.SATDONE,
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentId(),
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentState());   
                                   break;

         case MessageType.REQRM:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
                                    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   table.readTheMenu ()
                                      outMessage = new Message (MessageType.RMDONE,
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentId(),
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentState());   
                                   break;

         case MessageType.REQPO:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
                                    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   table.prepareOrder ()
                                      outMessage = new Message (MessageType.PODONE,
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentId(),
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentState());   
                                   break;

         case MessageType.REQEHC:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
                                    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.hasEverybodyChosen ())
                                      outMessage = new Message (MessageType.EHCDONE,
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentId(),
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentState());   
                                   break;

         case MessageType.REQAUOC:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
                                    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   table.addUpOnesChoice ()
                                      outMessage = new Message (MessageType.AUOCDONE,
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentId(),
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentState());   
                                   break;

         case MessageType.REQDO:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
                                    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   table.describeOrder ()
                                      outMessage = new Message (MessageType.DODONE,
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentId(),
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentState());   
                                   break;

         case MessageType.REQJT:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
                                    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   table.joinTalk ()
                                      outMessage = new Message (MessageType.JTDONE,
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentId(),
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentState());   
                                   break;

         case MessageType.REQIC:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
                                    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   table.informingCompanion ()
                                      outMessage = new Message (MessageType.ICDONE,
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentId(),
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentState());   
                                   break;

         case MessageType.REQSE:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
                                    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   table.startEating ()
                                      outMessage = new Message (MessageType.SEDONE,
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentId(),
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentState());   
                                   break;

         case MessageType.REQEE:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
                                    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   table.endEating ()
                                      outMessage = new Message (MessageType.EEDONE,
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentId(),
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentState());   
                                   break;

         case MessageType.REQHEFE:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
                                    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.hasEveryoneFinishedPortion ())
                                      outMessage = new Message (MessageType.HEFEDONE,
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentId(),
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentState());   
                                   break;

         case MessageType.REQHB:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
                                    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   table.honorTheBill ()
                                      outMessage = new Message (MessageType.HBDONE,
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentId(),
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentState());   
                                   break;

         case MessageType.REQHACBE: ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
                                    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.everyoneHasEaten ())
                                      outMessage = new Message (MessageType.HACBEDONE,
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentId(),
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentState());   
                                   break;

         case MessageType.REQSHAE:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
                                    ((TableClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.shouldHaveArrivedEarlier ())
                                      outMessage = new Message (MessageType.SHAEDONE,
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentId(),
                                                                  ((TableClientProxy)Thread.currentThread()).getStudentState());   
                                   break;

        case MessageType.SHUT:     table.shutdown ();
                                   outMessage = new Message (MessageType.SHUTDONE);
                                   break;
      }

     return (outMessage);
   }
   
}
