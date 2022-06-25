package interfaces;


import java.rmi.*;

public interface KitchenInterface extends Remote {
    public int watchTheNews() throws RemoteException;
    public int startPreparation() throws RemoteException;
    public int proceedToPresentation() throws RemoteException;
    public int continuePreparation() throws RemoteException;
    public boolean haveAllPortionsBeenDelivered() throws RemoteException;
    public int haveNextPortionReady() throws RemoteException;
    public boolean hasTheOrderBeenCompleted() throws RemoteException;
    public int handNoteToChef() throws RemoteException;
    public int returnToBar() throws RemoteException;
    public int collectPortion() throws RemoteException;
    public int cleanUp() throws RemoteException;
    public void shutdown() throws RemoteException;
}
