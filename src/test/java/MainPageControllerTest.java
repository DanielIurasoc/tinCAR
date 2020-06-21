import Controllers.MainPageController;
import Model.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class MainPageControllerTest extends ApplicationTest {
    private MainPageController mainPageController;

    @Before
    public void setUp(){
        mainPageController = new MainPageController();
        mainPageController.mainPageButton = new Button();
        mainPageController.profileButton = new Button();
        mainPageController.addButton = new Button();
        mainPageController.logoutButton = new Button();
        mainPageController.ValidatePageButton = new Button();
        mainPageController.accountUsernameLabel = new Label();
        mainPageController.announcementsList = new ScrollPane();
    }

    @Test
    public void initMainPageWithUserTest(){

        User u = new User("jfabian","pass2","user","0256129943","Costel", "Bucuresti");

        mainPageController.initMainPage(u, "../tinCAR/src/test/resources/announcements.json");
        // we have 4 announcements in json so the size of the array should be 4
        assertEquals(4, mainPageController.getAnnouncements().size());
        assertEquals(u, mainPageController.getUser());
        assertEquals("jfabian", mainPageController.accountUsernameLabel.getText());
        assertFalse(mainPageController.ValidatePageButton.isVisible());
        assertTrue(mainPageController.addButton.isVisible());
        assertTrue(mainPageController.profileButton.isVisible());
    }

    @Test
    public void initMainPageWithAdminTest(){

        User u = new User("admin","pass1","admin","0256129943","Costel", "Bucuresti");

        mainPageController.initMainPage(u, "../tinCAR/src/test/resources/announcements.json");
        // we have 4 announcements in json so the size of the array should be 4
        assertEquals(4, mainPageController.getAnnouncements().size());
        assertEquals(u, mainPageController.getUser());
        assertEquals("admin", mainPageController.accountUsernameLabel.getText());
        assertTrue(mainPageController.ValidatePageButton.isVisible());
        assertFalse(mainPageController.addButton.isVisible());
        assertFalse(mainPageController.profileButton.isVisible());
    }
}
