package interfaces;

import java.rmi.*;

public interface TableInterface extends Remote {
    public int getFirstStudent() throws RemoteException;
    public int getLastStudent() throws RemoteException;
    public void setFirstStudent(int studentId) throws RemoteException;
    public void setLastStudent(int studentId) throws RemoteException;
    public void seatAtTable(int studentId) throws RemoteException;
    public int readTheMenu(int studentId) throws RemoteException;
    public int prepareOrder() throws RemoteException;
    public int joinTalk() throws RemoteException;
    public boolean hasEverybodyChosen() throws RemoteException;
    public void addUpOnesChoice() throws RemoteException;
    public int informCompanion(int studentId) throws RemoteException;
    public void describeOrder() throws RemoteException;
    public boolean everyoneHasEaten() throws RemoteException;
    public int startEating(int studentId) throws RemoteException;
    public int endEating(int studentId) throws RemoteException;
    public boolean isLastCourse() throws RemoteException;
    public boolean hasEveryoneFinishedPortion(int studentId) throws RemoteException;
    public ReturnBoolean shouldHaveArrivedEarlier(int studentId) throws RemoteException;
    public void honorTheBill() throws RemoteException;
    public int saluteTheClient(int studentIdBeingAnswered) throws RemoteException;
    public int getThePad() throws RemoteException;
    public void deliverPortion() throws RemoteException;
    public int returnToBar() throws RemoteException;
    public int presentTheBill() throws RemoteException;
    public boolean haveAllClientsBeenServed() throws RemoteException;
    public void shutdown() throws RemoteException;
}
