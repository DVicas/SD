package interfaces;

import java.rmi.*;

public interface BarInterface extends Remote {
    public char lookAround() throws RemoteException;
    public void prepareTheBill() throws RemoteException;
    public void sayGoodbye() throws RemoteException;
    public void enter() throws RemoteException;
    public void callWaiter() throws RemoteException;
    public void signalWaiter() throws RemoteException;
    public void alertTheWaiter() throws RemoteException;
    public void exit() throws RemoteException;
    public int getStudentBeingAnswered() throws RemoteException;
    public synchronized void shutdown() throws RemoteException;
}
