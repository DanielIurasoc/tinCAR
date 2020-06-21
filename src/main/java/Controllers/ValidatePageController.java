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
import javafx.scene.layout.VBox;
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

public class ValidatePageController {
    @FXML
    public Button mainPageButton;
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

    public void initValidatePage(User account, String path){
        this.accountUsernameLabel.setText(account.getUsername());
        this.user = account;
        ValidatePageButton.setStyle("-fx-background-color: #005934");
        announcementsList.getStylesheets().add("/pageStyle.css");

        // load announcements from file
        try {
            announcements = AnnouncementService.getAnnouncements(path);
        }catch(IOException e) {
            System.out.println("IO Exception !");
        }catch(ParseException e){
            System.out.println("Parse Exception !");
        }

        VBox vbox = new VBox();

        for(Object obj : announcements){
            JSONObject o = (JSONObject) obj;
            Announcement announcement = new Announcement((String) o.get("owner"), (String) o.get("status"), (String) o.get("price"), (String) o.get("title"), (String) o.get("description"), (String) o.get("Fuel type"), (String) o.get("Transmission"), (String) o.get("Kilometres"), (String) o.get("First Registration"), (String) o.get("picture"), (String) o.get("phone"));
            if(announcement.getStatus().equals("pending")) {
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

                // Add elements to announcement pane ( for both user and administrator )
                announcementPane.getChildren().addAll(picture, title, price);

                // View Details Button
                Button viewDetails = new Button("View Details");
                viewDetails.setLayoutX(820);
                viewDetails.setLayoutY(135);
                viewDetails.setPrefHeight(50);
                viewDetails.setPrefWidth(150);
                viewDetails.getStyleClass().add("viewDetailsButton");
                viewDetails.setOnAction(e -> {
                    try {
                        handleDetailsButton(e, announcement);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });

                //Add View Details button to the announcement pane
                announcementPane.getChildren().add(viewDetails);

                //Create Accept button
                Button acceptAnnouncement = new Button("Accept");
                acceptAnnouncement.setLayoutX(820);
                acceptAnnouncement.setLayoutY(25);
                acceptAnnouncement.setPrefHeight(50);
                acceptAnnouncement.setPrefWidth(150);
                acceptAnnouncement.getStyleClass().add("acceptButton");
                acceptAnnouncement.setOnAction(e -> {
                    try {
                        handleAcceptButton(announcement);
                    } catch (IOException | ParseException ioException) {
                        ioException.printStackTrace();
                    }
                });

                //Add Accept button to the announcement pane
                announcementPane.getChildren().add(acceptAnnouncement);

                //Create Deny button
                Button denyAnnouncement = new Button("Deny");
                denyAnnouncement.setLayoutX(820);
                denyAnnouncement.setLayoutY(80);
                denyAnnouncement.setPrefHeight(50);
                denyAnnouncement.setPrefWidth(150);
                denyAnnouncement.getStyleClass().add("denyButton");
                denyAnnouncement.setOnAction(e -> {
                    try {
                        handleDenyButton(announcement);
                    } catch (IOException | ParseException ioException) {
                        ioException.printStackTrace();
                    }
                });

                //Add Deny button to the announcement pane
                announcementPane.getChildren().add(denyAnnouncement);

                //Add announcement pane to the VBox
                vbox.getChildren().add(announcementPane);
            }
        }

        vbox.setSpacing(2);
        vbox.setStyle("-fx-background-color: #d4d9d9");

        //Set announcementsList ( scrollPane ) content to be the VBox with all announcements
        this.announcementsList.setContent(vbox);

    }

    private void handleAcceptButton(Announcement announcement) throws IOException, ParseException {
        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        FileReader reader = new FileReader("../tinCAR/src/main/resources/announcements.json");
        // Read JSON file
        Object obj = jsonParser.parse(reader);

        JSONArray announceList = (JSONArray) obj;

        for (Object value : announceList) {
            JSONObject o = (JSONObject) value;

            //search for the right announcement by title, price and owner combination
            if(o.get("title").equals(announcement.getTitle()) && o.get("owner").equals(announcement.getOwner()) && o.get("price").equals(announcement.getPrice())){
                // change the value in json document
                o.put("status", "accepted");
                //break;
            }
        }

        // Write data
        FileWriter file = new FileWriter("../tinCAR/src/main/resources/announcements.json");
        file.write(announceList.toJSONString());
        file.flush();
        file.close();

        //reload announcements with updated changes
        try {
            announcements = AnnouncementService.getAnnouncements("../tinCAR/src/main/resources/announcements.json");
        }catch(IOException ex) {
            System.out.println("IO Exception !");
        }catch(ParseException ex){
            System.out.println("Parse Exception !");
        }

        //show success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Announcement accepted", ButtonType.OK);
        alert.setTitle("Announcement accepted1");
        alert.setHeaderText("Announcement accepted2");
        //alert.setContentText("Announcement accepted3");
        alert.setResizable(false);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("icon.png"));

        Optional<ButtonType> result = alert.showAndWait();

        // If OK button is pressed reload page
        if (result.isPresent() && result.get() == ButtonType.OK) {
            //reload Validate Page with updated announcements
            initValidatePage(user, "../tinCAR/src/main/resources/announcements.json");
        }
    }

    private void handleDenyButton(Announcement announcement) throws IOException, ParseException {
        String reasonOfDenial;

        // create a text input dialog
        TextInputDialog td = new TextInputDialog("write here");

        td.setWidth(350);
        td.setTitle("Deny announcement");
        td.setHeaderText("Enter reason of denial");
        td.setResizable(false);
        Stage stage = (Stage) td.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("icon.png"));
        //stage.setHeight(200);
        //stage.setWidth(400);

        Optional<String> result = td.showAndWait();

        // If OK button is pressed update JSON and reload page
        if (result.isPresent()) {
            //get the String user entered in the TextInputDialog
            reasonOfDenial = td.getEditor().getText();

            // JSON parser object to parse read file
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader("../tinCAR/src/main/resources/announcements.json");
            // Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray announceList = (JSONArray) obj;

            for (Object value : announceList) {
                JSONObject o = (JSONObject) value;

                //search for the right announcement by title, price and owner combination
                if(o.get("title").equals(announcement.getTitle()) && o.get("owner").equals(announcement.getOwner()) && o.get("price").equals(announcement.getPrice())){
                    // change the value in json document
                    o.put("status", reasonOfDenial);
                }
            }

            // Write data
            FileWriter file = new FileWriter("../tinCAR/src/main/resources/announcements.json");
            file.write(announceList.toJSONString());
            file.flush();
            file.close();

            //reload announcements with updated changes
            try {
                announcements = AnnouncementService.getAnnouncements("../tinCAR/src/main/resources/announcements.json");
            }catch(IOException ex) {
                System.out.println("IO Exception !");
            }catch(ParseException ex){
                System.out.println("Parse Exception !");
            }

            //reload Validate Page with updated announcements
            initValidatePage(user, "../tinCAR/src/main/resources/announcements.json");
        }
    }

    private void handleDetailsButton(ActionEvent actionEvent, Announcement announcement) throws IOException {
        //Get window
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
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

    public void handleMainPageButton(String path) throws IOException {
        //Get window
        Stage window = (Stage)mainPageButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/MainPage.fxml"));
        Parent profileParent = loader.load();
        Scene page = new Scene(profileParent, 1200, 800);
        MainPageController controller = loader.getController();
        controller.initMainPage(user, path);
        page.getStylesheets().add("/pageStyle.css");

        //Adding logo
        window.setTitle("tinCAR - The place to find your new car");
        window.getIcons().add(new Image("icon.png"));

        //Display window
        window.close();
        window.setScene(page);
        window.show();
    }

    public void handleValidatePageButton(){
        initValidatePage(user, "../tinCAR/src/main/resources/announcements.json");
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
