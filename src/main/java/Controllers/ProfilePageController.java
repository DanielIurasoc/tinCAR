package Controllers;

import Model.User;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@SuppressWarnings("unchecked")

public class ProfilePageController {
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
    @FXML
    public TextField phoneLabel;
    @FXML
    public TextField nameLabel;
    @FXML
    public Button editButton;
    @FXML
    public TextField cityLabel;

    private User user;

    public void initProfilePage(User account){
        accountUsernameLabel.setText(account.getUsername());
        profileButton.setStyle("-fx-background-color: #005934");
        this.user = account;
        // Set information of the account
        this.nameLabel.setText(user.getFull_name());
        this.phoneLabel.setText(user.getPhone());
        this.cityLabel.setText(user.getCity());
    }

    public void handleEditButton() throws IOException, ParseException {
        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        FileReader reader = new FileReader("../tinCAR/src/main/resources/users.json");
        // Read JSON file
        Object obj = jsonParser.parse(reader);

        JSONArray userList = (JSONArray) obj;

        for (Object value : userList) {
            JSONObject o = (JSONObject) value;
            if(o.get("username").equals(user.getUsername())){

                // change the value in json document
                o.put("phone", phoneLabel.getText());
                o.put("full name", nameLabel.getText());
                o.put("city", cityLabel.getText());

                // change these information in user object too, because we need to pass actualized information
                user.setPhone(phoneLabel.getText());
                user.setFull_name(nameLabel.getText());
                user.setCity(cityLabel.getText());
            }
        }

        FileWriter file = new FileWriter("../tinCAR/src/main/resources/users.json");
        file.write(userList.toJSONString());
        file.flush();
        file.close();

        // actualize data with all users in case we switch account
        UserService.loadUsersFromFile();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit profile");
        alert.setHeaderText("Profile information edited successfully !");
        alert.setContentText("Press ok to continue.");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("icon.png"));
        alert.show();
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

    public void handleProfileButton(){
        initProfilePage(user);
    }

    public void handleAddButton(ActionEvent actionEvent) throws IOException {
        //Get window
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/addAnnouncement.fxml"));
        Parent profileParent = loader.load();
        Scene page = new Scene(profileParent, 1200, 800);
        AddAnnouncementController controller = loader.getController();
        controller.initAddPage(user);

        //Adding logo
        window.setTitle("tinCAR - The place to find your new car");
        window.getIcons().add(new Image("icon.png"));

        //Display window
        window.close();
        window.setScene(page);
        window.show();
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
