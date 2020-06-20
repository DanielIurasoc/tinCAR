package Controllers;

import Exceptions.*;
import Model.User;
import Services.AnnouncementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

@SuppressWarnings("unchecked")

public class AddAnnouncementController {
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
    public TextField title;
    @FXML
    public TextArea description;
    @FXML
    public TextField price;
    @FXML
    public ChoiceBox<String> currencyChoice;
    @FXML
    public ChoiceBox<String> fuelType;
    @FXML
    public ChoiceBox<String> transmission;
    @FXML
    public RadioButton negotiable;
    @FXML
    public TextField kilometres;
    @FXML
    public TextField firstRegistration;
    @FXML
    public TextField picture;
    @FXML
    public TextField phone;
    @FXML
    public Button publishButton;

    private User user;

    public void initAddPage(User account){
        accountUsernameLabel.setText(account.getUsername());
        this.user = account;
        addButton.setStyle("-fx-background-color: #005934");

        //Clear old data
        title.clear();
        description.clear();
        price.clear();
        kilometres.clear();
        firstRegistration.clear();
        picture.clear();
        phone.clear();
        negotiable.setSelected(false);

        // Add options for currency
        currencyChoice.getItems().clear();
        currencyChoice.getItems().addAll("euro", "ron");
        currencyChoice.setStyle("-fx-font-size: 20px;");

        // Add options for fuel type
        fuelType.getItems().clear();
        fuelType.getItems().addAll("diesel", "benzine", "hybrid", "electric");
        fuelType.setStyle("-fx-font-size: 20px;");

        // Add options for transmission
        transmission.getItems().clear();
        transmission.getItems().addAll("automatic", "manual");
        transmission.setStyle("-fx-font-size: 20px;");

        // Make price field to accept only numbers
        price.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                price.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Make kilometres field to accept only numbers
        kilometres.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                kilometres.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Make first registration field to accept only numbers
        firstRegistration.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                firstRegistration.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Initialize the phone field with phone number of the account, this field can be changed when add an announcement or on the profile page
        phone.setText(account.getPhone());

        // Make phone number field to accept only numbers
        phone.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                phone.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void handlePublishButton(){
        // After the announcement is published, an alert will be displayed and after press ok, it will redirect to profile page
        JSONObject newAnnouncement = new JSONObject();
        try{
            newAnnouncement.put("owner", user.getUsername());
            newAnnouncement.put("status", "pending");

            // Get title, make verifications, throw exception if needed or set field if everything is in order
            String titleField = this.title.getText();
            if(titleField == null || titleField.trim().isEmpty()){
                throw new titleFieldMandatoryException("Title field is mandatory !");
            } else{
                newAnnouncement.put("title", titleField);
            }

            // Get description, make verifications, throw exception if needed or set field if everything is in order
            String descriptionField = this.description.getText();
            if(descriptionField == null || descriptionField.trim().isEmpty()){
                throw new descriptionFieldAtLeast50CharException("Description field but have at least 50 characters");
            } else{
                newAnnouncement.put("description", descriptionField);
            }

            // Get price, make verifications, throw exception if needed or set field if everything is in order
            String priceField = this.price.getText();
            if(priceField == null || priceField.trim().isEmpty() || currencyChoice == null || currencyChoice.getValue().isEmpty()){
                throw new priceFieldMandatoryException("Price is mandatory !");
            } else {
                priceField += " " + currencyChoice.getValue();
                if(negotiable.isSelected())
                    priceField += " neg";
                newAnnouncement.put("price", priceField);
            }

            // Get fuel type, make verifications, throw exception if needed or set field if everything is in order
            String fuelTypeField = this.fuelType.getValue();
            if(fuelTypeField == null || fuelTypeField.isEmpty()){
                throw new fuelTypeFieldMandatoryException("Fuel type is mandatory !");
            } else {
                newAnnouncement.put("Fuel type", fuelTypeField);
            }

            // Get transmission, make verifications, throw exception if needed or set field if everything is in order
            String transmissionField = this.transmission.getValue();
            if(transmissionField == null || transmissionField.isEmpty()){
                throw new transmissionFieldMandatoryException("Transmission field is mandatory !");
            } else {
                newAnnouncement.put("Transmission", transmissionField);
            }

            // Get kilometres, make verifications, throw exception if needed or set field if everything is in order
            String kilometresField = this.kilometres.getText();
            if(kilometresField == null || kilometresField.trim().isEmpty()){
                throw new kilometresFieldMandatoryException("Number of kilometres is mandatory !");
            } else {
                newAnnouncement.put("Kilometres", kilometresField);
            }

            // Get first registration, make verifications, throw exception if needed or set field if everything is in order
            String registrationField = this.firstRegistration.getText();
            if(registrationField == null || registrationField.trim().isEmpty()){
                throw new registrationFieldMandatoryException("First registration year is mandatory !");
            } else {
                newAnnouncement.put("First Registration", registrationField);
            }

            // Get picture, this field is not mandatory so we do not apply any verification
            newAnnouncement.put("picture", picture.getText());

            // Get first registration, make verifications, throw exception if needed or set field if everything is in order
            String phoneField = this.phone.getText();
            if(phoneField == null || phoneField.trim().isEmpty()){
                throw new phoneFieldMandatoryException("Phone number is mandatory !");
            } else {
                newAnnouncement.put("phone", phoneField);
            }

            //Get older announcements and add new announcement to the array
            JSONArray array;
            array = AnnouncementService.getAnnouncements("../tinCAR/src/main/resources/announcements.json");
            array.add(newAnnouncement);

            // Write to file the announcements updated
            FileWriter file = new FileWriter("../tinCAR/src/main/resources/announcements.json");
            file.write(array.toJSONString());
            file.flush();
            file.close();

            // Alerta de adaugare with 2 buttons with differential actions
            ButtonType add_another = new ButtonType("Add another", ButtonBar.ButtonData.OTHER);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Announcement added successfully", ButtonType.OK, add_another);
            alert.setTitle("Success !");
            alert.setHeaderText("Added new announcement successfully !");
            alert.setContentText("Press OK to manage your announcements, or press add another to add another announcement !");
            alert.setResizable(false);
            alert.setGraphic(new ImageView(new Image("AddedSuccessfully.jpg")));
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("icon.png"));

            Optional<ButtonType> result = alert.showAndWait();

            // If ok is pressed, then you got redirected to profile page
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //Get window
                Stage window = (Stage)(title.getScene().getWindow());
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
            } else if(result.isPresent() && result.get() == add_another){
                // If add_another button is presed, then reinitialize the add page
                initAddPage(user);
            }

        } catch (titleFieldMandatoryException | descriptionFieldAtLeast50CharException | priceFieldMandatoryException | phoneFieldMandatoryException | registrationFieldMandatoryException | fuelTypeFieldMandatoryException | transmissionFieldMandatoryException | kilometresFieldMandatoryException e){
            // In case of an exception, show an alert box with the problem
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Something went wrong !");
            alert.setTitle("Error in adding new announcement");
            alert.setContentText(e.toString());
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("icon.png"));
            alert.show();
        } catch (ParseException e){
            System.out.println("Parse exception in announcements.json");
        } catch (IOException e){
            System.out.println("IOException");
        }

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
        window.close();
        window.setScene(page);
        window.show();
    }

    public void handleAddButton(){
        initAddPage(user);
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
