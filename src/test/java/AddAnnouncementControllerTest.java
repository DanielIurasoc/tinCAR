import Controllers.AddAnnouncementController;
import Model.User;
import javafx.scene.control.*;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AddAnnouncementControllerTest extends ApplicationTest {

    private AddAnnouncementController addAnnouncementController;

    @Before
    public void setUp(){

        addAnnouncementController = new AddAnnouncementController();
        addAnnouncementController.mainPageButton = new Button();
        addAnnouncementController.profileButton = new Button();
        addAnnouncementController.addButton = new Button();
        addAnnouncementController.logoutButton = new Button();
        addAnnouncementController.accountUsernameLabel = new Label();
        addAnnouncementController.title = new TextField();
        addAnnouncementController.description = new TextArea();
        addAnnouncementController.price = new TextField();
        addAnnouncementController.currencyChoice = new ChoiceBox<>();
        addAnnouncementController.fuelType = new ChoiceBox<>();
        addAnnouncementController.transmission = new ChoiceBox<>();
        addAnnouncementController.negotiable = new RadioButton();
        addAnnouncementController.kilometres = new TextField();
        addAnnouncementController.firstRegistration = new TextField();
        addAnnouncementController.picture = new TextField();
        addAnnouncementController.phone = new TextField();
        addAnnouncementController.publishButton = new Button();
    }

    @Test
    public void initAddPageTest(){
        User u = new User("jfabian","pass2","user","0256129943","Costel", "Bucuresti");
        addAnnouncementController.initAddPage(u);
        assertEquals("jfabian", addAnnouncementController.accountUsernameLabel.getText());
        assertEquals(u, addAnnouncementController.getUser());
        assertEquals("", addAnnouncementController.title.getText());
        assertFalse(addAnnouncementController.negotiable.isSelected());
        assertEquals(2, addAnnouncementController.currencyChoice.getItems().size());
        assertEquals(2, addAnnouncementController.transmission.getItems().size());
        assertEquals(4, addAnnouncementController.fuelType.getItems().size());
        assertEquals("0256129943", addAnnouncementController.phone.getText());
    }
}
