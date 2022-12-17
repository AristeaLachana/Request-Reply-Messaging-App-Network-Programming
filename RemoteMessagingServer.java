import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RemoteMessagingServer extends UnicastRemoteObject implements MessagingServer {
    private Map<Integer, Account> accounts = new HashMap<>();
    private int key = 0; //count key of the Map accounts
    private String text; //text with client args - while the methods are processing, the text changes

    public RemoteMessagingServer() throws RemoteException {
        super();
    }

    public void chooseMethod() {
        //split client request
        String[] clientArgs = text.split(" ");

        switch (clientArgs[2]) { //FN_ID
            case "1":
                //username
                createAccount(clientArgs[3]);
                break;
            case "2":
                //authToken
                showAccounts(Integer.parseInt(clientArgs[3]));
                break;
            case "3":
                if (clientArgs.length > 6)
                    //if message_body is more than one word, get together the rest args to clientArgs[5]
                    for (int i = 6; i < clientArgs.length; i++) {
                        clientArgs[5] += " " + clientArgs[i];
                    }
                //authToken, recipient, message_body
                sendMessage(Integer.parseInt(clientArgs[3]), clientArgs[4], clientArgs[5]);
                break;
            case "4":
                //authToken
                showInbox(Integer.parseInt(clientArgs[3]));
                break;
            case "5":
                //authToken, message_id
                readMessage(Integer.parseInt(clientArgs[3]), Integer.parseInt(clientArgs[4]));
                break;
            case "6":
                //authToken, message_id
                deleteMessage(Integer.parseInt(clientArgs[3]), Integer.parseInt(clientArgs[4]));
                break;
        }
    }

    //the value text gets the request from the client as a String
    public void sendText(String text) {
        this.text = text;
    }

    //method that returns the value text from the class
    public String getText() {
        return text;
    }

    public void createAccount(String username) {
        //check if the user already exists
        if (accounts.size() > 0) {
            ArrayList<Account> users = new ArrayList<Account>(accounts.values());
            for (Account user : users) {
                if (user.getUsername().equals(username)) {
                    text = "Sorry, the user already exists";
                    return;
                }
            }
        }

        //if the user doesn't already exist, check if the given username is valid
        boolean isValid = username.matches("[_a-zA-Z0-9]+");
        if (isValid) {
            String authToken = "";
            boolean alreadyExists = false;
            //make authToken
            while (!alreadyExists) {
                Random rnd = new Random();
                int number = rnd.nextInt(9999);
                authToken = String.format("%04d", number);

                //unique authToken
                for (int i = 0; i < key; i++) {
                    if (accounts.get(i).getAuthToken() == Integer.parseInt(authToken)) {
                        alreadyExists = true;
                        break;
                    }
                }
                if (!alreadyExists) {
                    text = authToken;
                    break;
                }
            }

            //make new account + add it to the Map accounts
            Account a = new Account(username, Integer.parseInt(authToken));
            accounts.put(key, a);
            key++;
        } else {
            text = "Invalid Username";
        }
    }

    public void showAccounts(int authToken) {
        //check if authToken exists
        if (!authTokenExists(authToken)) {
            return;
        }

        text = "";
        for (int i = 0; i < accounts.size(); i++) {
            Integer obj = i + 1;
            //number of account, account Username
            text += obj.toString() + ". " + accounts.get(i).getUsername() + "\n";
        }
    }

    public void sendMessage(int authToken, String recipient, String message_body) {
        //check if authToken exists
        if (!authTokenExists(authToken)) {
            return;
        }

        //check if recipient exists
        int indexRecipient = -1;
        ArrayList<Account> users = new ArrayList<>(accounts.values());
        boolean recipientExists = false;
        for (Account user : users) {
            if (user.getUsername().equals(recipient)) {
                recipientExists = true;
                indexRecipient = users.indexOf(user);
                break;
            }
        }

        if (recipientExists) {
            //send message to recipient
            Account recipientAccount = accounts.get(indexRecipient);
            //find sender by authToken
            String sender = whichAccountByAuthToken(authToken).getUsername();
            Message message = new Message(false, sender, recipient, message_body);
            recipientAccount.getMessageBox().add(message);

            text = "OK";
        } else {
            text = "User does not exist";
        }
    }

    public void showInbox(int authToken) {
        text = "";

        //check if authToken exists
        if (!authTokenExists(authToken)) {
            return;
        }

        List<Message> messageBox = new ArrayList<>();

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAuthToken() == authToken) {
                messageBox = accounts.get(i).getMessageBox();
            }
        }


        for (int i = 0; i < messageBox.size(); i++) {
            String star = "";
            if (messageBox.get(i).getIsRead() == false)
                star = "*";
            text += messageBox.get(i).getMessage_id() + ". from: " + messageBox.get(i).getSender() + star + "\n";
        }
    }

    public void readMessage(int authToken, int message_id) {
        //check if authToken exists
        if (!authTokenExists(authToken)) {
            return;
        }

        //find account by authToken
        Account a = whichAccountByAuthToken(authToken);
        List<Message> messageBox = a.getMessageBox();

        Message message = testIfMessageExists(message_id, messageBox);

        if (message != null) {
            text = "(" + message.getSender() + ")" + message.getBody();
        } else {
            text = "Message ID does not exist";
        }
    }

    public void deleteMessage(int authToken, int message_id) {
        //check if authToken exists
        if (!authTokenExists(authToken)) {
            return;
        }

        //find account by given authToken
        Account a = whichAccountByAuthToken(authToken);
        int index = -1;

        for (Integer key : accounts.keySet()) {
            if (a.equals(accounts.get(key))) {
                index = key;
            }
        }

        List<Message> messageBox = a.getMessageBox();

        Message message = testIfMessageExists(message_id, messageBox);

        if (message != null) {
            accounts.get(index).setMessageBox(removeFromMessageBox(accounts.get(index).getMessageBox(), message));
            key--;
            text = "OK";
        } else {
            text = "Message does not exist";
        }

    }

    private List<Message> removeFromMessageBox(List<Message> messageBox, Message message) {
        Iterator<Message> itr = messageBox.iterator();
        while (itr.hasNext()) {
            Message m = itr.next();
            if (m == message) {
                itr.remove();
            }
        }

        return messageBox;
    }

    private Message testIfMessageExists(int message_id, List<Message> messageBox) {
        Message message = null;

        for (Message m : messageBox) {
            if (m.getMessage_id() == message_id) {
                m.setRead(true);
                message = m;
                break;
            }
        }

        return message;
    }

    private boolean authTokenExists(int authToken) {
        boolean exists = false;

        //check if it exists
        for (Account user : accounts.values()) {
            if (user.getAuthToken() == authToken) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            text = "Invalid Auth Token";
        }

        return exists;
    }

    private Account whichAccountByAuthToken(int authToken) {
        Account thisUser = null;
        for (Account user : accounts.values()) {
            if (user.getAuthToken() == authToken)
                thisUser = user;
        }
        return thisUser;
    }
}
