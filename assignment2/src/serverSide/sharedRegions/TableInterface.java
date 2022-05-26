/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverSide.sharedRegions;

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
        }

     // processing 

      switch (inMessage.getMsgType ())

      { case MessageType.REQSC:  ((BarberShopClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                   if (table.saluteTheClient ())
                                      outMessage = new Message (MessageType.SCDONE,
                                                                ((BarberShopClientProxy) Thread.currentThread ()).getWaiterState ());
                                   break;

         case MessageType.REQRB:  ((BarberShopClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                   if (table.returnToBar ())
                                      outMessage = new Message (MessageType.RBDONE,
                                                                ((BarberShopClientProxy) Thread.currentThread ()).getWaiterState ());
                                   break;

         case MessageType.REQGB:  ((BarberShopClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                   if (table.getThePad ())
                                      outMessage = new Message (MessageType.GBDONE,
                                                                ((BarberShopClientProxy) Thread.currentThread ()).getWaiterState ());
                                   break;

         case MessageType.REQHACBS:  ((BarberShopClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                   if (table.haveAllClientsBeenServed ())
                                      outMessage = new Message (MessageType.HACBSDONE,
                                                                ((BarberShopClientProxy) Thread.currentThread ()).getWaiterState ());
                                   break;

         case MessageType.REQDP:  ((BarberShopClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                   if (table.returnToBar ())
                                      outMessage = new Message (MessageType.DPDONE,
                                                                ((BarberShopClientProxy) Thread.currentThread ()).getWaiterState ());
                                   break;    

         case MessageType.REQPB:  ((BarberShopClientProxy) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState ());
                                   if (table.presentTheBill ())
                                      outMessage = new Message (MessageType.PBDONE,
                                                                ((BarberShopClientProxy) Thread.currentThread ()).getWaiterState ());
                                   break;

         case MessageType.REQGFTA:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId())
                                    ((BarberShopClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.getFirstStudent ())
                                       outMessage = new Message (MessageType.GTFADONE,
                                                                  ((BarClientProxy)Thread.currentThread())-getStudentId(),
                                                                  ((BarClientProxy)Thread.currentThread()).setStudentState());         

                                   break;
         
         case MessageType.REQGLTE:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId())
                                    ((BarberShopClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.getLastStudent ())
                                      outMessage = new Message (MessageType.GLTEDONE,
                                                                  ((BarClientProxy)Thread.currentThread())-getStudentId(),
                                                                  ((BarClientProxy)Thread.currentThread()).setStudentState());   
                                   break;

         case MessageType.REQSAT:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId())
                                    ((BarberShopClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.seatAtTable ())
                                      outMessage = new Message (MessageType.SATDONE,
                                                                  ((BarClientProxy)Thread.currentThread())-getStudentId(),
                                                                  ((BarClientProxy)Thread.currentThread()).setStudentState());   
                                   break;

         case MessageType.REQRM:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId())
                                    ((BarberShopClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.readTheMenu ())
                                      outMessage = new Message (MessageType.RMDONE,
                                                                  ((BarClientProxy)Thread.currentThread())-getStudentId(),
                                                                  ((BarClientProxy)Thread.currentThread()).setStudentState());   
                                   break;

         case MessageType.REQPO:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId())
                                    ((BarberShopClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.prepareOrder ())
                                      outMessage = new Message (MessageType.PODONE,
                                                                  ((BarClientProxy)Thread.currentThread())-getStudentId(),
                                                                  ((BarClientProxy)Thread.currentThread()).setStudentState());   
                                   break;

         case MessageType.REQEHC:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId())
                                    ((BarberShopClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.hasEverybodyChosen ())
                                      outMessage = new Message (MessageType.EHCDONE,
                                                                  ((BarClientProxy)Thread.currentThread())-getStudentId(),
                                                                  ((BarClientProxy)Thread.currentThread()).setStudentState());   
                                   break;

         case MessageType.REQAUOC:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId())
                                    ((BarberShopClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.addUpOnesChoice ())
                                      outMessage = new Message (MessageType.AUOCDONE,
                                                                  ((BarClientProxy)Thread.currentThread())-getStudentId(),
                                                                  ((BarClientProxy)Thread.currentThread()).setStudentState());   
                                   break;

         case MessageType.REQDO:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId())
                                    ((BarberShopClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.describeOrder ())
                                      outMessage = new Message (MessageType.DODONE,
                                                                  ((BarClientProxy)Thread.currentThread())-getStudentId(),
                                                                  ((BarClientProxy)Thread.currentThread()).setStudentState());   
                                   break;

         case MessageType.REQJT:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId())
                                    ((BarberShopClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.joinTalk ())
                                      outMessage = new Message (MessageType.JTDONE,
                                                                  ((BarClientProxy)Thread.currentThread())-getStudentId(),
                                                                  ((BarClientProxy)Thread.currentThread()).setStudentState());   
                                   break;

         case MessageType.REQIC:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId())
                                    ((BarberShopClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.informingCompanion ())
                                      outMessage = new Message (MessageType.ICDONE,
                                                                  ((BarClientProxy)Thread.currentThread())-getStudentId(),
                                                                  ((BarClientProxy)Thread.currentThread()).setStudentState());   
                                   break;

         case MessageType.REQSE:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId())
                                    ((BarberShopClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.startEating ())
                                      outMessage = new Message (MessageType.SEDONE,
                                                                  ((BarClientProxy)Thread.currentThread())-getStudentId(),
                                                                  ((BarClientProxy)Thread.currentThread()).setStudentState());   
                                   break;

         case MessageType.REQEE:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId())
                                    ((BarberShopClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.endEating ())
                                      outMessage = new Message (MessageType.EEDONE,
                                                                  ((BarClientProxy)Thread.currentThread())-getStudentId(),
                                                                  ((BarClientProxy)Thread.currentThread()).setStudentState());   
                                   break;

         case MessageType.REQHEFE:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId())
                                    ((BarberShopClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.hasEveryoneFinishedPortion ())
                                      outMessage = new Message (MessageType.HEFEDONE,
                                                                  ((BarClientProxy)Thread.currentThread())-getStudentId(),
                                                                  ((BarClientProxy)Thread.currentThread()).setStudentState());   
                                   break;

         case MessageType.REQHB:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId())
                                    ((BarberShopClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.honorTheBill ())
                                      outMessage = new Message (MessageType.HBDONE,
                                                                  ((BarClientProxy)Thread.currentThread())-getStudentId(),
                                                                  ((BarClientProxy)Thread.currentThread()).setStudentState());   
                                   break;

         case MessageType.REQAHACBE: ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId())
                                    ((BarberShopClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.everyoneHasEaten ())
                                      outMessage = new Message (MessageType.HACBEDONE,
                                                                  ((BarClientProxy)Thread.currentThread())-getStudentId(),
                                                                  ((BarClientProxy)Thread.currentThread()).setStudentState());   
                                   break;

         case MessageType.REQSHAE:  ((TableClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId())
                                    ((BarberShopClientProxy) Thread.currentThread ()).setStudentState (inMessage.getStudentState ());
                                   if (table.shouldHaveArrivedEarlier ())
                                      outMessage = new Message (MessageType.SHAEDONE,
                                                                  ((BarClientProxy)Thread.currentThread())-getStudentId(),
                                                                  ((BarClientProxy)Thread.currentThread()).setStudentState());   
                                   break;

        case MessageType.SHUT:     table.shutdown ();
                                   outMessage = new Message (MessageType.SHUTDONE);
                                   break;
      }

     return (outMessage);
   }
   
}
