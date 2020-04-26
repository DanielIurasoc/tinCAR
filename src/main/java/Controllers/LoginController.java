package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import Exceptions.UsernameOrPasswordDoesNotExistException;
import Services.*;

public class LoginController {

    @FXML
    private Text loginMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;

    @FXML
    public void handleLoginAction() {
        try {
            String role = UserService.checkCredentials(usernameField.getText(), passwordField.getText());
            loginMessage.setText(String.format("Logged in as %s", role));
        } catch (UsernameOrPasswordDoesNotExistException e) {
            loginMessage.setText(e.getMessage());
        }
    }
}