package MainPackage;

import MainPackage.controller.persistence.PersistenceAccess;
import MainPackage.controller.persistence.ValidAccount;
import MainPackage.controller.services.LoginService;
import MainPackage.model.EmailAccount;
import MainPackage.model.EmailMessage;
import MainPackage.view.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Launcher extends Application {

    private PersistenceAccess persistenceAccess = new PersistenceAccess();
    private EmailManager emailManager = new EmailManager();

    @Override
    public void start(Stage primaryStage) throws Exception{

        ViewFactory viewFactory = new ViewFactory(emailManager);
        List<ValidAccount> validAccountList = persistenceAccess.loadFromPersistence();
        if (validAccountList.size() > 0) {
            viewFactory.showMainWindow();
            for (ValidAccount validAccount: validAccountList) {
                EmailAccount emailAccount = new EmailAccount(validAccount.getAdress(), validAccount.getPassword());
                LoginService loginService = new LoginService(emailAccount, emailManager);
                loginService.start();
            }
        }else {
            viewFactory.showLoginWindow();
        }
    }


    @Override
    public void stop() throws Exception {
        List<ValidAccount> validAccountList = new ArrayList<ValidAccount>();
        for (EmailAccount emailAccount: emailManager.getEmailAccounts()) {
            validAccountList.add(new ValidAccount(emailAccount.getAddress(), emailAccount.getPassword()));
        }
        persistenceAccess.saveToPersistance(validAccountList);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
