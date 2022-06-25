package interfaces;


import java.rmi.*;

public interface KitchenInterface extends Remote {
    public void watchTheNews() throws RemoteException;
    public void startPreparation() throws RemoteException;
    public void proceedToPresentation() throws RemoteException;
    public void continuePreparation() throws RemoteException;
    public boolean haveAllPortionsBeenDelivered() throws RemoteException;
    public void haveNextPortionReady() throws RemoteException;
    public boolean hasTheOrderBeenCompleted() throws RemoteException;
    public void handNoteToChef() throws RemoteException;
    public void returnToBar() throws RemoteException;
    public void collectPortion() throws RemoteException;
    public void cleanUp() throws RemoteException;
    public void shutdown() throws RemoteException;
}
