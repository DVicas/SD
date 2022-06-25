package interfaces;

import java.rmi.*;

public interface BarInterface extends Remote {
    public char lookAround() throws RemoteException;
    public int prepareTheBill() throws RemoteException;
    public boolean sayGoodbye() throws RemoteException;
    public int enter(int studentId) throws RemoteException;
    public void callWaiter(int studentId) throws RemoteException;
    public void signalWaiter(int studentId, int studentState) throws RemoteException;
    public int alertTheWaiter() throws RemoteException;
    public int exit(int studentId) throws RemoteException;
    public int getStudentBeingAnswered() throws RemoteException;
    public void shutdown() throws RemoteException;
}
