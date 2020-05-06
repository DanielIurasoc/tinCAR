package Controllers;

import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {

    private User loggedInAccount;
    @FXML
    public Button mainPageButton;
    @FXML
    public Button profileButton;
    @FXML
    public Button addButton;
    @FXML
    public Button logoutButton;
    @FXML
    public Label accountUsernameLabel;

    public void setAccount(User account){
        this.loggedInAccount = account;
        this.accountUsernameLabel.setText(loggedInAccount.getUsername());
    }

    public void handleLogoutButton(javafx.event.ActionEvent actionEvent) throws IOException {
        //Get window
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        //Create scene
        Parent loginParent = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene page = new Scene(loginParent, 800, 500);

        //Adding logo
        window.setTitle("tinCAR - The place to find your new car");
        window.getIcons().add(new Image("icon.png"));

        //Display window
        window.close();
        window.setScene(page);
        window.show();
    }
}
