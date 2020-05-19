package Controllers;

import Model.User;
import javafx.event.ActionEvent;
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

public class ValidatePageController {
    @FXML
    public Button mainPageButton;
    @FXML
    public Button profileButton;
    @FXML
    public Button addButton;
    @FXML
    public Button logoutButton;
    @FXML
    public Button ValidatePageButton;
    @FXML
    public Label accountUsernameLabel;

    private User user;

    public void initValidatePage(User account){
        accountUsernameLabel.setText(account.getUsername());
        this.user = account;
        ValidatePageButton.setStyle("-fx-background-color: #005934");
    }

    public void handleMainPageButton(ActionEvent actionEvent) throws IOException {
        //Get window
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/MainPage.fxml"));
        Parent profileParent = loader.load();
        Scene page = new Scene(profileParent, 1200, 800);
        page.getStylesheets().add("/pageStyle.css");
        MainPageController controller = loader.getController();
        controller.initMainPage(user);

        //Adding logo
        window.setTitle("tinCAR - The place to find your new car");
        window.getIcons().add(new Image("icon.png"));

        //Display window
        window.close();
        window.setScene(page);
        window.show();
    }

    public void handleValidatePageButton(){
        initValidatePage(user);
    }

    public void handleLogoutButton(ActionEvent actionEvent) throws IOException {
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