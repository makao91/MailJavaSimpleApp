package MainPackage.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.mail.Message;
import javax.mail.internet.MimeBodyPart;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmailMessage {

    private SimpleStringProperty subject;
    private SimpleStringProperty sender;
    private SimpleStringProperty recipient;
    private SimpleObjectProperty size;
    private SimpleObjectProperty<Date> date;
    private boolean isRead;
    private Message message;
    private List<MimeBodyPart> attachmentsList = new ArrayList<MimeBodyPart>();
    private boolean hasAttachments = false;

    public EmailMessage(String subject, String sender, String recipeint, int size, Date date, boolean isRead,
                        Message message) {
        this.subject = new SimpleStringProperty(subject);
        this.sender = new SimpleStringProperty(sender);
        this.recipient = new SimpleStringProperty(recipeint);
        this.size = new SimpleObjectProperty(new SizeInteger(size));
        this.date = new SimpleObjectProperty<Date>(date);
        this.isRead = isRead;
        this.message = message;
    }
    public String getSubject() {
        return subject.get();
    }

    public String getSender() {
        return sender.get();
    }


    public String getRecipient() {
        return recipient.get();
    }


    public SizeInteger getSize() {
        return (SizeInteger) size.get();
    }


    public Date getDate() {
        return date.get();
    }


    public boolean isRead() {
        return isRead;
    }

    public Message getMessage() {
        return message;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void addAttachment(MimeBodyPart mbd) {
        hasAttachments = true;
        attachmentsList.add(mbd);
    }

    public boolean isHasAttachments() {
        return hasAttachments;
    }

    public List<MimeBodyPart> getAttachmentsList() {
        return attachmentsList;
    }
}
