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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewDetailsController {

    @FXML
    public Label Title;
    @FXML
    public Label Fuel;
    @FXML
    public Label Kilometres;
    @FXML
    public Label Registration;
    @FXML
    public Label Owner;
    @FXML
    public Label Phone;
    @FXML
    public Label Price;
    @FXML
    public Label Transmission;
    @FXML
    public AnchorPane Picture;
    @FXML
    public Button backButton;
    @FXML
    public ScrollPane Description;

    private User user;

    public void initDetailsPage(Announcement announcement, User account) {
        this.Title.setText(announcement.getTitle());
        this.Title.setAlignment(Pos.CENTER);
        this.Picture.setStyle("-fx-background-image: url(" + "carPictures" + "/" + announcement.getPicture() + ")");
        this.Owner.setText("Owner : " + announcement.getOwner());
        this.Phone.setText("Phone number :\n" + announcement.getPhone_number());
        Text txt = new Text(announcement.getDescription());
        txt.setWrappingWidth(900);
        txt.getStyleClass().add("descriptionField");
        StackPane sp = new StackPane();
        sp.setStyle("-fx-background-color: #ebeded");
        sp.getChildren().add(txt);
        this.Description.setContent(sp);
        this.Description.setStyle("-fx-background: #ebeded");
        this.Fuel.setText("Fuel : " + announcement.getFuel_type());
        this.Transmission.setText("Transmission : " + announcement.getTransmission());
        this.Kilometres.setText("Kilometres : " + announcement.getKilometres());
        this.Registration.setText("First Registration : " + announcement.getFirst_registration());
        this.Price.setText("Price : " + announcement.getPrice());
        this.user = account;
    }

    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        //Get window
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/MainPage.fxml"));
        Parent profileParent = loader.load();
        Scene page = new Scene(profileParent, 1200, 800);
        //Get controller
        MainPageController controller = loader.getController();
        controller.initMainPage(user);
        page.getStylesheets().add("/pageStyle.css");

        //Adding logo
        window.setTitle("tinCAR - The place to find your new car");
        window.getIcons().add(new Image("icon.png"));

        //Display window
        window.close();
        window.setScene(page);
        window.show();
    }
}
