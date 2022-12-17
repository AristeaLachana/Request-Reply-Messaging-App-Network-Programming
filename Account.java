import java.util.*;

public class Account implements java.io.Serializable {
    private String username;
    private int authToken;
    private List<Message> messageBox;

    public Account(String username, int authToken){
        this.username=username;
        this.authToken=authToken;
        this.messageBox=new ArrayList<>();
    }

    public int getAuthToken() {
        return authToken;
    }

    public List<Message> getMessageBox() {
        return messageBox;
    }

    public String getUsername() {
        return username;
    }

    public void setMessageBox(List<Message> messageBox) {
        this.messageBox = messageBox;
    }
}