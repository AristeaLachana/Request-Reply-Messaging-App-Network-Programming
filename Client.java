import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.rmi.RemoteException;

public class Client {
    /*
   args[0] = ip
   args[1] = port_number
    */
    public static void main(String[] args) {
        MessagingServer ms = null;

        try {
            Registry rmiRegistry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
            ms = (MessagingServer) rmiRegistry.lookup("MessagingServer");

        } catch (Exception e) {
            System.out.println(e);
        }

        if (ms != null) {
            try {
                //make text with all the arguments from client request
                StringBuilder text = new StringBuilder();
                int i = 0;
                while (i < args.length) {
                    if (i != args.length - 1)
                        text.append(args[i]).append(" ");
                    else
                        text.append(args[i]);
                    i++;
                }

                //send client request
                ms.sendText(text.toString());

                //choose by FN_ID - args[2]
                ms.chooseMethod();

                //print reply
                System.out.println(ms.getText());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
