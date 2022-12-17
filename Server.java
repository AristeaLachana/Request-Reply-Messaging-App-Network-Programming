import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try{
            RemoteMessagingServer rms = new RemoteMessagingServer();
            Registry rmiRegistry = LocateRegistry.createRegistry(Integer.parseInt(args[0]));
            rmiRegistry.rebind("MessagingServer", rms);
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
