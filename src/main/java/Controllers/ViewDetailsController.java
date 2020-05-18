package Controllers;

import Model.Announcement;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ViewDetailsController {

    @FXML
    public Label titleLabel;
    public Button backButton;

    private User user;

    public void initDetailsPage(Announcement announcement, User account) {
        this.titleLabel.setText(announcement.getTitle());
        this.user = account;
    }
}
