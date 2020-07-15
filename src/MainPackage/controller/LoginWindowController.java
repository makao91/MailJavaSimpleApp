package MainPackage.controller;

import MainPackage.EmailManager;
import MainPackage.controller.services.LoginService;
import MainPackage.model.EmailAccount;
import MainPackage.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController extends BaseController implements Initializable {

    @FXML
    private TextField emailAdressTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label errorLabel;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonAction() {
        if (fieldsAreValid()){
            EmailAccount emailAccount = new EmailAccount(emailAdressTextField.getText(), passwordTextField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            loginService.start();
            loginService.setOnSucceeded(workerStateEvent -> {

                EmailLoginResult emailLoginResult = loginService.getValue();
                switch (emailLoginResult) {
                    case SUCCESS:
                        if (!viewFactory.isMainViewInitialized()){
                            viewFactory.showMainWindow();
                        }
                        Stage stage = (Stage) errorLabel.getScene().getWindow();
                        viewFactory.closeStage(stage);
                        return;
                    case FAILED_BY_CREDENTIALS:
                        errorLabel.setText("Invalid credentials");
                        return;
                    case UNEXPECTED_ERROR:
                        errorLabel.setText("unexpected error");
                        return;
                    case FAILED_BY_NETWORK:
                        errorLabel.setText("Failsed by network");
                        return;
                    default:
                        return;
                }
            });
        }

    }

    private boolean fieldsAreValid() {
        if (emailAdressTextField.getText().isEmpty()){
            errorLabel.setText("Please fill email");
            return false;
        }
        if (passwordTextField.getText().isEmpty()) {
            errorLabel.setText("Please fill password");
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailAdressTextField.setText("javaMailTes12345@gmail.com");
        passwordTextField.setText("qwerty1234_");
    }
}
