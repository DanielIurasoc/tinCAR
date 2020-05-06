package Controllers;

import Model.Announcement;
import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
    public Label accountUsernameLabel;
    @FXML
    public ScrollPane announcementsList;

    private ArrayList<Announcement> announcements = new ArrayList<>();

    public void initMainPage(User account){
        this.accountUsernameLabel.setText(account.getUsername());

        try {
            loadAnnouncementsFromFile();
        }catch(IOException e) {
            System.out.println("IO Exception !");
        }catch(ParseException e){
            System.out.println("Parse Exception !");
        }

        for(Announcement a : announcements){
            System.out.println("Announce readed");
        }

        VBox vbox = new VBox();
        for(int i=1;i<=23;i++){

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
            picture.setStyle("-fx-background-color: #751921");
            //l1.setStyle(String.format("-fx-background-color: %s","sda"));

            // Title
            Label title = new Label("Title "+i);
            title.setLayoutX(400);
            title.setLayoutY(10);
            title.setFont(new Font("Arial", 30));
            title.setStyle("-fx-font-weight: bold");
            title.setPrefHeight(50);
            title.setPrefWidth(400);
            title.setStyle("-fx-background-color: #fcba03");

            // Price
            Label price = new Label("Price "+i);
            price.setLayoutX(600);
            price.setLayoutY(135);
            price.setFont(new Font("Arial", 30));
            price.setStyle("-fx-font-weight: bold");
            price.setPrefHeight(50);
            price.setPrefWidth(200);
            price.setStyle("-fx-background-color: #030ffc");

            // View Details Button

            Button viewDetails = new Button("View Details");
            viewDetails.setLayoutX(820);
            viewDetails.setLayoutY(135);
            viewDetails.setFont(new Font("Arial", 10));
            viewDetails.setStyle("-fx-font-weight: bold");
            viewDetails.setPrefHeight(50);
            viewDetails.setPrefWidth(150);
            viewDetails.setStyle("-fx-background-color: #03fc1c");
            viewDetails.setId(String.valueOf(i)); // set the fx Id for the button with the number of the announcement in the list, so we know which announcement is related to

            // Add elements to announcement pane ( for both user and administrator )
            announcementPane.getChildren().addAll(picture, title, price, viewDetails);

            // Delete announcement Button created and added only if the account have administrator role

            if(account.getRole().equals("admin")){
                //Create button
                Button deteleAnnouncement = new Button("Delete");
                deteleAnnouncement.setLayoutX(820);
                deteleAnnouncement.setLayoutY(85);
                deteleAnnouncement.setFont(new Font("Arial", 10));
                deteleAnnouncement.setStyle("-fx-font-weight: bold");
                deteleAnnouncement.setPrefHeight(50);
                deteleAnnouncement.setPrefWidth(150);
                deteleAnnouncement.setStyle("-fx-background-color: #fc0303");
                deteleAnnouncement.setId(String.valueOf(i)); // set the fx Id for the button with the number of the announcement in the list, so we know which announcement is related to
                //Add button to the announcement pane
                announcementPane.getChildren().add(deteleAnnouncement);
            }

            vbox.getChildren().add(announcementPane);
        }
        vbox.setSpacing(2);
        vbox.setStyle("-fx-background-color: #212121");
        this.announcementsList.setContent(vbox);
        this.announcementsList.setPannable(true);
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
            Announcement announcement = new Announcement((String) o.get("owner"),(String) o.get("status"),(String) o.get("title"),(String) o.get("description"),(String) o.get("Fuel type"),(String) o.get("Transmission"),(String) o.get("Transmission"),(String) o.get("First Registration"),(String) o.get("picture"),(String) o.get("phone"));
            announcements.add(announcement);
        }
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
