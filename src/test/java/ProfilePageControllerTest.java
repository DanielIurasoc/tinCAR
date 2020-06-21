import Controllers.ProfilePageController;
import Model.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

public class ProfilePageControllerTest extends ApplicationTest {

    private ProfilePageController profilePageController;

    @Before
    public void setUp(){
        profilePageController = new ProfilePageController();
        profilePageController.mainPageButton = new Button();
        profilePageController.profileButton = new Button();
        profilePageController.addButton = new Button();
        profilePageController.logoutButton = new Button();
        profilePageController.accountUsernameLabel = new Label();
        profilePageController.phoneLabel = new TextField();
        profilePageController.nameLabel = new TextField();
        profilePageController.editButton = new Button();
        profilePageController.cityLabel = new TextField();
        profilePageController.listOfItems = new VBox();
        profilePageController.informationArea = new AnchorPane();
        profilePageController.successLabel = new Label();
    }

    @Test
    public void initProfilePageTest(){
        User u = new User("jfabian","pass2","user","0256129943","Costel", "Bucuresti");
        profilePageController.initProfilePage(u);
        assertEquals("jfabian", profilePageController.accountUsernameLabel.getText());
        assertEquals(u, profilePageController.getUser());
        assertEquals(u.getFull_name(), profilePageController.nameLabel.getText());
        assertEquals(u.getPhone(), profilePageController.phoneLabel.getText());
        assertEquals(u.getCity(), profilePageController.cityLabel.getText());
        assertEquals(15, profilePageController.getAnnouncements().size());
        // -1 to listOfItems size because first item is information Area ( there are 1 + number of announcements items )
        assertEquals(6, profilePageController.listOfItems.getChildren().size() - 1);
    }
}
