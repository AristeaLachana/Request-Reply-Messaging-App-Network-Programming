public class Message implements java.io.Serializable {
    public static int messages = 1;
    private int message_id;
    private boolean isRead;
    private String sender;
    private String receiver;
    private String body;

    public Message(boolean isRead, String sender, String receiver, String body){
        this.message_id = messages;
        messages++;
        this.isRead=isRead;
        this.sender=sender;
        this.receiver=receiver;
        this.body=body;
    }

    public int getMessage_id() {
        return message_id;
    }

    public String getBody() {
        return body;
    }

    public String getSender() {
        return sender;
    }

    public boolean getIsRead(){
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}