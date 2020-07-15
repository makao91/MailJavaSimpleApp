package MainPackage.view;

import MainPackage.EmailManager;
import MainPackage.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewFactory {
    EmailManager emailManager;
    private ArrayList<Stage> activeStages;
    private boolean mainViewInitialized = false;

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
        activeStages = new ArrayList<Stage>();
    }

    public boolean isMainViewInitialized (){
        return mainViewInitialized;
    }

    //View options
    private ColorTheme colorTheme = ColorTheme.DEFAULT;
    private FontSize fontSize = FontSize.MEDIUM;


    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }


    public void showLoginWindow() {

        BaseController controller = new LoginWindowController(emailManager, this, "LoginWindow.fxml");
        sceneLoader(controller);
    }

    public void showMainWindow() {
        BaseController controller = new MainWindowController(emailManager, this, "MainWindow.fxml");
        sceneLoader(controller);
        mainViewInitialized = true;
    }

    public void showOptionsWindow() {
        BaseController controller = new OptionWindowController(emailManager, this, "OptionsWindow.fxml");
        sceneLoader(controller);
    }

    public void showEmailDetailsWindow() {
        BaseController controller = new EmailDetailsController(emailManager, this, "EmailsDetailWindow.fxml");
        sceneLoader(controller);
    }

    public void showComposeMessageWindow() {
        BaseController controller = new ComposeMessageController(emailManager, this, "ComposeMessageWindow.fxml");
        sceneLoader(controller);
    }

    private void sceneLoader(BaseController baseController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        activeStages.add(stage);
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
        activeStages.remove(stageToClose);
    }

    public void updateStyles() {
        for (Stage stage: activeStages) {
            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(colorTheme)).toExternalForm());
            scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm());
        }

    }
}
