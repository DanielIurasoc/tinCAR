package Controllers;

import Exceptions.UsernameOrPasswordDoesNotExistException;
import Model.User;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Base64;


public class LoginController {

    public Button LoginButton;
    @FXML
    private Text loginMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;

    public void handleLoginButton(ActionEvent actionEvent) throws IOException {

        try {
            // Check credentials and get the role of the account
            User user = UserService.checkCredentials(usernameField.getText(), Base64.getEncoder().encodeToString((passwordField.getText()).getBytes()));

            // Get the stage window where other scene is showed
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader();

            //Different page loaded depends on account role
            if(user.getRole().equals("user")){
                loader.setLocation(getClass().getResource("/MainPage.fxml"));
            } else if(user.getRole().equals("admin")){
                loader.setLocation(getClass().getResource("/AdminValidatePage.fxml"));
            }

            Parent pageParent = loader.load();
            Scene page = new Scene(pageParent, 1200, 800);

            //Pass the account
            MainPageController controller = loader.getController();
            controller.initMainPage(user);

            //Adding logo
            window.setTitle("tinCAR - The place to find your new car");
            window.getIcons().add(new Image("icon.png"));

            //Display window
            window.close();
            window.setScene(page);
            window.show();

        } catch (UsernameOrPasswordDoesNotExistException e) {
            loginMessage.setText(e.getMessage());
        }
    }

}