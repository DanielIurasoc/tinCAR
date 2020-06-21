import Controllers.LoginController;
import Services.UserService;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;

import static org.junit.Assert.*;

public class LoginControllerTest extends ApplicationTest {

    private LoginController controller;

    @BeforeClass
    public static void setupClass() throws Exception {
        UserService.loadUsersFromFile("../tinCAR/src/test/resources/users.json");
    }

    @Before
    public void setUp() throws IOException, ParseException {
        UserService.loadUsersFromFile("../tinCAR/src/test/resources/users.json");

        controller = new LoginController();
        controller.usernameField = new TextField();
        controller.passwordField = new PasswordField();
        controller.loginMessage = new Text();
    }

    @Test
    public void testHandleLoginButtonWithNonExistingAccount() throws IOException {
        controller.passwordField.setText("testUsername");
        controller.usernameField.setText("testPassword");
        controller.handleLoginButton();
        assertEquals("Username or password invalid. Please try again !", controller.loginMessage.getText());
    }
}
