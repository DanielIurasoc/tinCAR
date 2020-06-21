package Controllers;

import Model.Announcement;
import Model.User;
import Services.AnnouncementService;
import Services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

@SuppressWarnings("unchecked")

public class MainPageController {

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
    @FXML
    public ScrollPane announcementsList;

    private JSONArray announcements = new JSONArray();
    private User user;

    public void initMainPage(User account){
        this.accountUsernameLabel.setText(account.getUsername());
        this.user = account;
        mainPageButton.setStyle("-fx-background-color: #005934");

        try { // load announcements from file
            announcements = AnnouncementService.getAnnouncements();
        }catch(IOException e) {
            System.out.println("IO Exception !");
        }catch(ParseException e){
            System.out.println("Parse Exception !");
        }

        if(account.getRole().equals("user")){ // show different menu button depends on account role
            ValidatePageButton.setVisible(false);
        }else{
            addButton.setVisible(false);
            profileButton.setVisible(false);
        }

        VBox vbox = new VBox();

        for(Object obj : announcements){
            JSONObject o = (JSONObject) obj;
            Announcement announcement = new Announcement((String) o.get("owner"), (String) o.get("status"), (String) o.get("price"), (String) o.get("title"), (String) o.get("description"), (String) o.get("Fuel type"), (String) o.get("Transmission"), (String) o.get("Kilometres"), (String) o.get("First Registration"), (String) o.get("picture"), (String) o.get("phone"));
            if(announcement.getStatus().equals("accepted")) {
                // Announcement window
                AnchorPane announcementPane = new AnchorPane();
                announcementPane.setPrefHeight(200);
                announcementPane.setPrefWidth(1000);
                announcementPane.setStyle("-fx-background-color: #ebeded");

                // Picture
                AnchorPane picture = new AnchorPane();
                picture.setLayoutX(10);
                picture.setLayoutY(10);
                picture.setPrefWidth(250);
                picture.setPrefHeight(180);
                picture.setStyle("-fx-background-image: url(" + "carPictures" + "/" + announcement.getPicture() + ")");/////////////////////

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
                Button viewDetails = new Button("View Details");
                viewDetails.setLayoutX(820);
                viewDetails.setLayoutY(135);
                viewDetails.setPrefHeight(50);
                viewDetails.setPrefWidth(150);
                viewDetails.getStyleClass().add("viewDetailsButton");
                viewDetails.setOnAction(e -> {
                    try {
                        handleDetailsButton(announcement);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });

                // Add elements to announcement pane ( for both user and administrator )
                announcementPane.getChildren().addAll(picture, title, price, viewDetails);

                // Delete announcement Button created and added only if the account have administrator role
                if (account.getRole().equals("admin")) {
                    //Create button
                    Button deleteAnnouncement = new Button("Delete");
                    deleteAnnouncement.setLayoutX(820);
                    deleteAnnouncement.setLayoutY(85);
                    deleteAnnouncement.setPrefHeight(50);
                    deleteAnnouncement.setPrefWidth(150);
                    deleteAnnouncement.getStyleClass().add("deleteButton");
                    deleteAnnouncement.setOnAction(e -> {
                        try {
                            handleDeleteButton(announcement);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    });
                    //Add button to the announcement pane
                    announcementPane.getChildren().add(deleteAnnouncement);
                }

                //Add announcement pane to the VBox
                vbox.getChildren().add(announcementPane);
            }
        }
        vbox.setSpacing(2);
        vbox.setStyle("-fx-background-color: #d4d9d9");
        //Set announcementsList ( scrollPane ) content to be the VBox with all announcements
        this.announcementsList.setContent(vbox);
    }

    public void handleDeleteButton(Announcement announcement) throws IOException {
        JSONArray newListOfAnnouncements = new JSONArray();

        // Add to the new list all elements except the one with same title, owner and price(the one we want to delete)
        for(Object o : announcements){
            JSONObject jo = (JSONObject) o;
            if(!jo.get("title").equals(announcement.getTitle())){
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
            FileWriter file = new FileWriter("../tinCAR/src/main/resources/users.json");
            file.write(newListOfAnnouncements.toJSONString());
            file.flush();
            file.close();

            // Reinitialize the Main page
            initMainPage(user);
        }
    }

    public void handleDetailsButton(Announcement announcement) throws IOException {
        //Get window
        Stage window = (Stage)mainPageButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewDetailsPage.fxml"));
        Parent detailsParent = loader.load();
        Scene page = new Scene(detailsParent, 1200, 800);
        ViewDetailsController controller = loader.getController();
        controller.initDetailsPage(announcement, user, UserService.searchUser(announcement.getOwner()));
        page.getStylesheets().add("/pageStyle.css");

        //Adding logo
        window.setTitle("tinCAR - The place to find your new car");
        window.getIcons().add(new Image("icon.png"));

        //Display window
        window.close();
        window.setScene(page);
        window.show();
    }

    public void handleMainPageButton() {
        initMainPage(user);
    }

    public void handleProfileButton() throws IOException {
        //Get window
        Stage window = (Stage)mainPageButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ProfilePage.fxml"));
        Parent profileParent = loader.load();
        Scene page = new Scene(profileParent, 1200, 800);
        ProfilePageController controller = loader.getController();
        controller.initProfilePage(user);

        //Adding logo
        window.setTitle("tinCAR - The place to find your new car");
        window.getIcons().add(new Image("icon.png"));

        //Display window
        window.close();
        window.setScene(page);
        window.show();
    }

    public void handleAddButton() throws IOException {
        //Get window
        Stage window = (Stage)mainPageButton.getScene().getWindow();
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

    public void handleValidatePageButton() throws IOException {
        //Get window
        Stage window = (Stage)mainPageButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/AdminValidatePage.fxml"));
        Parent profileParent = loader.load();
        Scene page = new Scene(profileParent, 1200, 800);
        ValidatePageController controller = loader.getController();
        controller.initValidatePage(user);

        //Adding logo
        window.setTitle("tinCAR - The place to find your new car");
        window.getIcons().add(new Image("icon.png"));

        //Display window
        window.close();
        window.setScene(page);
        window.show();
    }

    public void handleLogoutButton() throws IOException {
        //Get window
        Stage window = (Stage)mainPageButton.getScene().getWindow();
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

    public JSONArray getAnnouncements(){
        return this.announcements;
    }

    public User getUser(){
        return this.user;
    }
}
