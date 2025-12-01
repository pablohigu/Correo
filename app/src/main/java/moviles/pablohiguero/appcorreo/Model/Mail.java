package moviles.pablohiguero.appcorreo.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import moviles.pablohiguero.appcorreo.App.MyApplication;
import moviles.pablohiguero.appcorreo.Utils.Util;

public class Mail extends RealmObject {

    @PrimaryKey
    private int IdMail;
    private String Subject;
    private String Message;
    private String SenderName;
    private String color;

    public Mail() {
    }

    public Mail(String subject, String message, String senderName) {
        this.Subject = subject;
        this.Message = message;
        this.SenderName = senderName;
        this.color = Util.getRandomColor();
        this.IdMail = MyApplication.MailID.incrementAndGet();
    }

    public int getIdMail() {
        return IdMail;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getColor() {
        return color;
    }
}
