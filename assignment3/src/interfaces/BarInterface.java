package interfaces;

import java.rmi.*;

public interface BarInterface extends Remote {
    public char lookAround() throws RemoteException;
    public int prepareTheBill() throws RemoteException;
    public boolean sayGoodbye() throws RemoteException;
    public int enter() throws RemoteException;
    public void callWaiter() throws RemoteException;
    public void signalWaiter() throws RemoteException;
    public int alertTheWaiter() throws RemoteException;
    public int exit() throws RemoteException;
    public int getStudentBeingAnswered() throws RemoteException;
    public synchronized void shutdown() throws RemoteException;
}
