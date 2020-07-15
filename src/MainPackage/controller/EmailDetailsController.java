package MainPackage.controller;

import MainPackage.EmailManager;
import MainPackage.controller.services.MessageRenderService;
import MainPackage.model.EmailMessage;
import MainPackage.view.ViewFactory;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmailDetailsController extends BaseController implements Initializable {

    private String LOCATIONS_OF_DOWNLOADS = System.getProperty("user.home") + "/Downloads/";

    @FXML
    private WebView webView;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label senderLabel;

    @FXML
    private HBox hBoxDownloads;

    @FXML
    private Label attachmentLabel;

    public EmailDetailsController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EmailMessage emailMessage = emailManager.getSelectedMessage();
        subjectLabel.setText(emailMessage.getSubject());
        senderLabel.setText(emailMessage.getSender());
        try {
            loadAttachments(emailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        MessageRenderService messageRenderService = new MessageRenderService(webView.getEngine());
        messageRenderService.setEmailMessage(emailMessage);
        messageRenderService.restart();
    }

    private void loadAttachments(EmailMessage emailMessage) throws MessagingException {
        if(emailMessage.isHasAttachments()) {
            for (MimeBodyPart mimeBodyPart: emailMessage.getAttachmentsList()){
                try {
                    AttachmentButton button = new AttachmentButton(mimeBodyPart);
                    hBoxDownloads.getChildren().add(button);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

            }

        }else {
            attachmentLabel.setText("");
        }
    }

    private class AttachmentButton extends Button {

        private MimeBodyPart mimeBodyPart;
        private String downloadedFilePath;

        public AttachmentButton (MimeBodyPart mimeBodyPart) throws MessagingException {
            this.mimeBodyPart = mimeBodyPart;
            this.setText(mimeBodyPart.getFileName());
            this.downloadedFilePath = LOCATIONS_OF_DOWNLOADS + mimeBodyPart.getFileName();
            
            this.setOnAction(e->{
                downloadAttachment();
            });
        }

        private void downloadAttachment() {
            colorBlue();
            Service service = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            mimeBodyPart.saveFile(downloadedFilePath);
                            return null;
                        }
                    };
                }
            };
            service.restart();
            service.setOnSucceeded(e-> {
                colorGreen();
                this.setOnAction(e2->{
                    File file = new File(downloadedFilePath);
                    Desktop desktop = Desktop.getDesktop();
                    if(file.exists()) {
                        try {
                            desktop.open(file);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });
            });
        }

        private void colorBlue() {
           this.setStyle("-fx-background-color: Blue");
        }
        private void colorGreen() {
            this.setStyle("-fx-background-color: Green");
        }

    }
}

