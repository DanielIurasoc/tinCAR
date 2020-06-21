import Controllers.ValidatePageController;
import Model.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

public class ValidatePageControllerTest extends ApplicationTest {

    private ValidatePageController validatePageController;

    @Before
    public void setUp(){
        validatePageController = new ValidatePageController();
        validatePageController.mainPageButton = new Button();
        validatePageController.logoutButton = new Button();
        validatePageController.ValidatePageButton = new Button();
        validatePageController.accountUsernameLabel = new Label();
        validatePageController.announcementsList = new ScrollPane();
    }

    @Test
    public void initValidatePageTest(){
        User u = new User("jfabian","pass2","user","0256129943","Costel", "Bucuresti");
        validatePageController.initValidatePage(u,"../tinCAR/src/test/resources/announcements.json");
        assertEquals("jfabian", validatePageController.accountUsernameLabel.getText());
        assertEquals(u, validatePageController.getUser());
        assertEquals(4, validatePageController.getAnnouncements().size());
        assertEquals(1, ((VBox)validatePageController.announcementsList.getContent()).getChildren().size());
    }
}
