package MainPackage.controller;

import MainPackage.EmailManager;
import MainPackage.controller.services.MessageRenderService;
import MainPackage.model.EmailMessage;
import MainPackage.model.EmailTreeItem;
import MainPackage.model.SizeInteger;
import MainPackage.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    private MenuItem markUnread = new MenuItem("mark as unread");
    private MenuItem deleteMessage = new MenuItem("delete message");
    private MenuItem showMessageDetailMenuItem = new MenuItem("show details");

    @FXML
    private TreeView<String> emailsTreeView;

    @FXML
    private TableView<EmailMessage> emailTableView;
    @FXML
    private TableColumn<EmailMessage, String> senderCol;

    @FXML
    private TableColumn<EmailMessage, String> subjectCol;

    @FXML
    private TableColumn<EmailMessage, String> recipientCol;

    @FXML
    private TableColumn<EmailMessage, SizeInteger> sizeCol;

    @FXML
    private TableColumn<EmailMessage, Date> dateCol;


    @FXML
    private WebView emailWebView;
    private MessageRenderService messageRenderService;

    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void optionsAction() {
        viewFactory.showOptionsWindow();
    }

    @FXML
    void addNewAccount() {
        viewFactory.showLoginWindow();
    }

    @FXML
    void newMessageButton() {
        viewFactory.showComposeMessageWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpEmialsTreeView();
        setUpEmailsTableView();
        setUpFolderSelection();
        setUpBoldRows();
        setUpMessageRenderService();
        setUpMessageSelection();
        setUpContextMenus();
    }

    private void setUpContextMenus() {
        markUnread.setOnAction(e->{
            emailManager.setUnread();
        });
        deleteMessage.setOnAction(e->{
            emailManager.deleteSelectedMessage();
            emailWebView.getEngine().loadContent("");
        });
        showMessageDetailMenuItem.setOnAction(e->{
            viewFactory.showEmailDetailsWindow();
        });
    }

    private void setUpMessageSelection() {
        emailTableView.setOnMouseClicked(event-> {
            EmailMessage emailMessage = emailTableView.getSelectionModel().getSelectedItem();
            if (emailMessage != null) {
                emailManager.setSelectedMessage(emailMessage);
                emailManager.setSelectedMessage(emailMessage);
                if (!emailMessage.isRead()) {
                    emailManager.setRead();
                }
                messageRenderService.setEmailMessage(emailMessage);
                messageRenderService.restart();
            }
        });
    }

    private void setUpMessageRenderService() {
        messageRenderService = new MessageRenderService(emailWebView.getEngine());
    }

    private void setUpBoldRows() {
        emailTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>() {
            @Override
            public TableRow<EmailMessage> call(TableView<EmailMessage> emailMessageTableView) {
                return new TableRow<EmailMessage>(){
                    protected  void updateItem(EmailMessage item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item != null) {
                            if (item.isRead()) {
                                setStyle("");
                            } else {
                                setStyle("-fx-font-weight:bold");
                            }
                        }
                    }
                };
            }
        });
    }

    private void setUpFolderSelection() {
        emailsTreeView.setOnMouseClicked(e->{
            EmailTreeItem<String> item = (EmailTreeItem<String>) emailsTreeView.getSelectionModel().getSelectedItem();
            if(item != null) {
                emailManager.setSelectedTreeItem(item);
                emailTableView.setItems(item.getEmailMessages());
            }
        });

    }


    private void setUpEmailsTableView() {
        senderCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("sender")));
        subjectCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("subject")));
        recipientCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("recipient")));
        sizeCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, SizeInteger>("size")));
        dateCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, Date>("date")));

        emailTableView.setContextMenu(new ContextMenu(markUnread, deleteMessage,showMessageDetailMenuItem));
    }

    private void setUpEmialsTreeView() {
        emailsTreeView.setRoot(emailManager.getFoldersRoot());
        emailsTreeView.setShowRoot(false);
    }
}

