import Controllers.ViewDetailsController;
import Model.Announcement;
import Model.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

public class ViewDetailsControllerTest extends ApplicationTest {

    private ViewDetailsController viewDetailsController;

    @Before
    public void setUp(){
        viewDetailsController = new ViewDetailsController();
        viewDetailsController.Title = new Label();
        viewDetailsController.Fuel = new Label();
        viewDetailsController.Kilometres = new Label();
        viewDetailsController.Registration = new Label();
        viewDetailsController.Owner = new Label();
        viewDetailsController.Phone = new Label();
        viewDetailsController.Price = new Label();
        viewDetailsController.City = new Label();
        viewDetailsController.Transmission = new Label();
        viewDetailsController.Picture = new AnchorPane();
        viewDetailsController.backButton = new Button();
        viewDetailsController.Description = new ScrollPane();
    }

    @Test
    public void initViewDetailsPageTest(){
        Announcement announcement = new Announcement("jfabian","accepted", "57100 euro","BMW X5 M M50d","Pachet Sport M + Aerodinamic + Frane Sport M.\n Pachet M-Performance (Carbon), Head Up Display, Trapa Sticla Panoramica, Keyless-go, usi softclose, Jante Aluminiu M 20 inch.","diesel","automatic","86000","2017","BMWX5.jpg", "0284125281");
        User account = new User("diurasoc","pass3","user","0256128843","Daniel", "Timisoara");
        User owner = new User("jfabian","pass2","user","0256129943","Fabian", "Bucuresti");
        viewDetailsController.initDetailsPage(announcement, account, owner);
        assertEquals("BMW X5 M M50d", viewDetailsController.Title.getText());
        assertEquals("Owner :\nFabian", viewDetailsController.Owner.getText());
        assertEquals("Phone number :\n0256129943", viewDetailsController.Phone.getText());
        assertEquals("Fuel : diesel", viewDetailsController.Fuel.getText());
        assertEquals("City : Bucuresti", viewDetailsController.City.getText());
        assertEquals("Transmission : automatic", viewDetailsController.Transmission.getText());
        assertEquals("Kilometres : 86000", viewDetailsController.Kilometres.getText());
        assertEquals("First Registration : 2017", viewDetailsController.Registration.getText());
        assertEquals("Price : 57100 euro", viewDetailsController.Price.getText());
        assertEquals(account, viewDetailsController.getUser());
    }
}
