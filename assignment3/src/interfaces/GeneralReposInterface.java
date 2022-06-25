package interfaces;

import java.rmi.*;

public interface GeneralReposInterface extends Remote{
    public void setChefState(int value) throws RemoteException;
    public void setWaiterState(int value) throws RemoteException;
    public void setStudentState(int id, int value) throws RemoteException;
    public void setStudentState(int id, int value, boolean hold) throws RemoteException;
    public void setnCourses(int value) throws RemoteException;
    public void setnPortions(int value) throws RemoteException;
    public void updateSeatsAtTable(int id, int seat) throws RemoteException;
    public void shutdown() throws RemoteException;
}
