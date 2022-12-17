import java.rmi.*;

public interface MessagingServer extends Remote {
    public void sendText(String text) throws RemoteException;
    public String getText() throws RemoteException;
    public void chooseMethod() throws RemoteException;
    public void createAccount(String username) throws RemoteException;
    public void showAccounts(int authToken) throws RemoteException;
    public void sendMessage(int authToken, String recipient, String message_body) throws RemoteException;
    public void showInbox(int authToken) throws RemoteException;
    public void readMessage(int authToken, int message_id) throws RemoteException;
    public void deleteMessage(int authToken, int message_id) throws RemoteException;
}
