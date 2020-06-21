package Controllers;

import Model.Announcement;
import Model.User;
import Services.AnnouncementService;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

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
    @FXML
    public VBox listOfItems;
    @FXML
    public AnchorPane informationArea;
    @FXML
    public Label successLabel;

    private User user;
    private JSONArray announcements = new JSONArray();

    public void initProfilePage(User account){
        accountUsernameLabel.setText(account.getUsername());
        profileButton.setStyle("-fx-background-color: #005934");
        this.user = account;

        // Clear old items ( helps when we delete an announcement for e.g. )
        announcements.clear();
        listOfItems.getChildren().clear();
        listOfItems.getChildren().add(informationArea);

        // Set information of the account
        this.nameLabel.setText(user.getFull_name());
        this.phoneLabel.setText(user.getPhone());
        this.cityLabel.setText(user.getCity());

        try { // load announcements from file
            announcements = AnnouncementService.getAnnouncements();
        }catch(IOException e) {
            System.out.println("IO Exception !");
        }catch(ParseException e){
            System.out.println("Parse Exception !");
        }

        for(Object obj : announcements){
            JSONObject o = (JSONObject) obj;
            if(o.get("owner").equals(user.getUsername())) {
                Announcement announcement = new Announcement((String) o.get("owner"), (String) o.get("status"), (String) o.get("price"), (String) o.get("title"), (String) o.get("description"), (String) o.get("Fuel type"), (String) o.get("Transmission"), (String) o.get("Kilometres"), (String) o.get("First Registration"), (String) o.get("picture"), (String) o.get("phone"));
                // Announcement window
                AnchorPane announcementPane = new AnchorPane();
                announcementPane.setPrefHeight(200);
                announcementPane.setMinHeight(200);
                announcementPane.setPrefWidth(1000);
                announcementPane.setStyle("-fx-background-color: #ebeded");

                // Picture
                AnchorPane picture = new AnchorPane();
                picture.setLayoutX(10);
                picture.setLayoutY(10);
                picture.setPrefWidth(250);
                picture.setPrefHeight(180);
                picture.setStyle("-fx-background-image: url(" + "carPictures" + "/" + announcement.getPicture() + ")");

                // Title
                Label title = new Label(announcement.getTitle());
                title.setLayoutX(300);
                title.setLayoutY(20);
                title.setPrefHeight(60);
                title.setPrefWidth(500);
                title.setAlignment(Pos.CENTER);
                title.getStyleClass().add("titleLabel");

                // Price
                Label price = new Label(announcement.getPrice());
                price.setLayoutX(400);
                price.setLayoutY(130);
                price.setPrefHeight(50);
                price.setPrefWidth(300);
                price.setAlignment(Pos.CENTER);
                price.getStyleClass().add("priceLabel");

                // View Details Button
                Button deleteButton = new Button("Delete");
                deleteButton.setLayoutX(820);
                deleteButton.setLayoutY(135);
                deleteButton.setPrefHeight(50);
                deleteButton.setPrefWidth(150);
                deleteButton.getStyleClass().add("deleteButton");
                deleteButton.setOnAction(e -> {
                    try {
                        handleDeleteButton(announcement);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });

                //Stack pane for status of the announcement
                StackPane status = new StackPane();

                // Semicircle shape for status to be fancy
                Arc statusShape = new Arc(0, 0, 75, 75, 180, 180);
                statusShape.setType(ArcType.OPEN);
                statusShape.setStrokeType(StrokeType.CENTERED);
                statusShape.setFill(Color.rgb(224, 224, 224));

                //Text in the semicircle
                Text statusText = new Text();
                statusText.getStyleClass().add("statusText");

                switch (announcement.getStatus()){
                    case "accepted" :{
                        statusText.setFill(Color.rgb(10, 204, 36));
                        statusText.setText("Accepted");
                        break;
                    }
                    case "pending" :{
                        statusText.setFill(Color.rgb(255, 249, 64));
                        statusText.setText("Pending");
                        break;
                    }
                    default:
                        statusText.setText("Denied");
                        statusText.setFill(Color.rgb(240, 23, 12));
                        Button seeReasonButton = new Button("Reason");
                        seeReasonButton.setLayoutX(820);
                        seeReasonButton.setLayoutY(83);
                        seeReasonButton.setPrefHeight(50);
                        seeReasonButton.setPrefWidth(150);
                        seeReasonButton.getStyleClass().add("deleteButton");
                        seeReasonButton.setOnAction(e -> handleDenialReasonButton(announcement.getStatus()));
                        announcementPane.getChildren().add(seeReasonButton);
                        break;
                }

                // Add the two elements to the stack pane witch will be added in the announcement pane
                status.getChildren().addAll(statusShape, statusText);
                status.setLayoutX(820);
                
                // Add elements to announcement pane ( for both user and administrator )
                announcementPane.getChildren().addAll(picture, title, price, deleteButton, status);

                //Add announcement pane to the VBox
                listOfItems.getChildren().add(announcementPane);
            }
        }
        listOfItems.setSpacing(2);
        listOfItems.setStyle("-fx-background-color: #d4d9d9");
    }

    private void handleDenialReasonButton(String status) {
        Stage secondStage = new Stage();

        Label root = new Label();
        root.setPrefHeight(500);
        root.setPrefWidth(300);
        root.setText(status);
        root.setWrapText(true);
        root.getStyleClass().add("denialReasonNewSceneLabel");
        Scene scene = new Scene(root, 500, 300);
        scene.getStylesheets().add("pageStyle.css");
        secondStage.setScene(scene);

        secondStage.setTitle("tinCAR - The place to find your new car");
        secondStage.getIcons().add(new Image("icon.png"));

        secondStage.show();
    }

    private void handleDeleteButton(Announcement announcement) throws IOException {
        JSONArray newListOfAnnouncements = new JSONArray();

        // Add to the new list all elements except the one with same title, owner and price(the one we want to delete)
        for(Object o : announcements){
            JSONObject jo = (JSONObject) o;
            if(!jo.get("title").equals(announcement.getTitle()) && !jo.get("owner").equals(announcement.getOwner()) && !jo.get("price").equals(announcement.getPrice())){
                newListOfAnnouncements.add(jo);
            }
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete announcement");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you really want to delete this record? This process cannot be undone.");
        alert.setResizable(false);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("icon.png"));

        Optional<ButtonType> result = alert.showAndWait();

        // If yes button is pressed then the announcement is gone
        if (result.isPresent() && result.get() == ButtonType.YES) {
            // Write to the file the new announcements list
            FileWriter file = new FileWriter("../tinCAR/src/main/resources/announcements.json");
            file.write(newListOfAnnouncements.toJSONString());
            file.flush();
            file.close();

            // Reinitialize the profile page
            initProfilePage(user);
        }
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

        // Write data
        FileWriter file = new FileWriter("../tinCAR/src/main/resources/users.json");
        file.write(userList.toJSONString());
        file.flush();
        file.close();

        // actualize data with all users in case we switch account
        UserService.loadUsersFromFile();
        successLabel.setText("Profile edited successfully !");
    }

    public void handleMainPageButton() throws IOException {
        //Get window
        Stage window = (Stage)mainPageButton.getScene().getWindow();
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

    public User getUser(){
        return this.user;
    }

    public JSONArray getAnnouncements(){
        return this.announcements;
    }
}
