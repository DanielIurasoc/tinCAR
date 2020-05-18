package Controllers;

import Model.Announcement;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

    private final ArrayList<Announcement> announcements = new ArrayList<>();
    private User user;

    public void initMainPage(User account){
        this.accountUsernameLabel.setText(account.getUsername());
        this.user = account;
        announcements.clear();
        mainPageButton.setStyle("-fx-background-color: #005934");

        try { // load announcements from file
            loadAnnouncementsFromFile();
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

        for(Announcement announcement : announcements){

            // Announcement window
            AnchorPane announcementPane = new AnchorPane();
            announcementPane.setPrefHeight(200);
            announcementPane.setPrefWidth(1000);
            announcementPane.setStyle("-fx-background-color: #757575");

            // Picture
            AnchorPane picture = new AnchorPane();
            picture.setLayoutX(10);
            picture.setLayoutY(10);
            picture.setPrefWidth(250);
            picture.setPrefHeight(180);
            picture.setStyle("-fx-background-image: url("+announcement.getPicture()+")");

            // Title
            Label title = new Label(announcement.getTitle());
            title.setLayoutX(350);
            title.setLayoutY(10);
            title.setPrefHeight(60);
            title.setPrefWidth(500);
            title.setAlignment( Pos.CENTER );
            title.getStyleClass().add("titleLabel");

            // Price
            Label price = new Label(announcement.getPrice());
            price.setLayoutX(500);
            price.setLayoutY(135);
            price.setPrefHeight(50);
            price.setPrefWidth(300);
            price.setAlignment( Pos.CENTER );
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
                    initDetails(announcement, e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            // Add elements to announcement pane ( for both user and administrator )
            announcementPane.getChildren().addAll(picture, title, price, viewDetails);

            // Delete announcement Button created and added only if the account have administrator role
            if(account.getRole().equals("admin")){
                //Create button
                Button deteleAnnouncement = new Button("Delete");
                deteleAnnouncement.setLayoutX(820);
                deteleAnnouncement.setLayoutY(85);
                deteleAnnouncement.setPrefHeight(50);
                deteleAnnouncement.setPrefWidth(150);
                viewDetails.getStyleClass().add("deleteButton");
                //Add button to the announcement pane
                announcementPane.getChildren().add(deteleAnnouncement);
            }

            //Add announcement pane to the VBox
            vbox.getChildren().add(announcementPane);
        }
        vbox.setSpacing(2);
        vbox.setStyle("-fx-background-color: #212121");
        //Set announcementsList ( scrollPane ) content to be the VBox with all announcements
        this.announcementsList.setContent(vbox);
    }

    private void initDetails(Announcement announcement, ActionEvent actionEvent) throws IOException {
        //Get window
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewDetailsPage.fxml"));
        Parent detailsParent = loader.load();
        Scene page = new Scene(detailsParent, 1200, 800);
        ViewDetailsController controller = loader.getController();
        controller.initDetailsPage(announcement, user);

        //Adding logo
        window.setTitle("tinCAR - The place to find your new car");
        window.getIcons().add(new Image("icon.png"));

        //Display window
        window.setScene(page);
        window.show();
    }

    private void loadAnnouncementsFromFile() throws IOException, ParseException {

        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        FileReader reader = new FileReader("../tinCAR/src/main/resources/announcements.json");
        // Read JSON file
        Object obj = jsonParser.parse(reader);
        JSONArray announcementsList = (JSONArray) obj;

        for (Object value : announcementsList) {
            JSONObject o = (JSONObject) value;
            Announcement announcement = new Announcement((String) o.get("id"),(String) o.get("owner"),(String) o.get("status"), (String) o.get("price"),(String) o.get("title"),(String) o.get("description"),(String) o.get("Fuel type"),(String) o.get("Transmission"),(String) o.get("Transmission"),(String) o.get("First Registration"),(String) o.get("picture"),(String) o.get("phone"));
            announcements.add(announcement);
        }
    }

    public void handleMainPageButton() {
        initMainPage(user);
    }

    public void handleProfileButton(ActionEvent actionEvent) throws IOException {
        //Get window
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
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
        window.setScene(page);
        window.show();
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
        window.setScene(page);
        window.show();
    }

    public void handleValidatePageButton(ActionEvent actionEvent) throws IOException {
        //Get window
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
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
