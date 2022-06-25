package interfaces;

import java.rmi.*;

public interface TableInterface extends Remote {
    public int getFirstStudent() throws RemoteException;
    public int getLastStudent() throws RemoteException;
    public void setFirstStudent(int studentId) throws RemoteException;
    public void setLastStudent(int studentId) throws RemoteException;
    public void seatAtTable() throws RemoteException;
    public void readTheMenu() throws RemoteException;
    public void prepareOrder() throws RemoteException;
    public void joinTalk() throws RemoteException;
    public boolean hasEverybodyChosen() throws RemoteException;
    public void addUpOnesChoice() throws RemoteException;
    public void informCompanion() throws RemoteException;
    public void describeOrder() throws RemoteException;
    public boolean everyoneHasEaten() throws RemoteException;
    public void startEating() throws RemoteException;
    public void endEating() throws RemoteException;
    public boolean isLastCourse() throws RemoteException;
    public boolean hasEveryoneFinishedPortion() throws RemoteException;
    public boolean shouldHaveArrivedEarlier() throws RemoteException;
    public void honorTheBill() throws RemoteException;
    public void saluteTheClient(int studentIdBeingAnswered) throws RemoteException;
    public void getThePad() throws RemoteException;
    public void deliverPortion() throws RemoteException;
    public void returnToBar() throws RemoteException;
    public void presentTheBill() throws RemoteException;
    public boolean haveAllClientsBeenServed() throws RemoteException;
    public void shutdown() throws RemoteException;
}
