import Exceptions.UsernameOrPasswordDoesNotExistException;
import Model.User;
import Services.UserService;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.Base64;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserServiceTests {
    @Test
    public void searchExistingUser() throws IOException, ParseException{
        UserService.loadUsersFromFile();
        User u = UserService.searchUser("jfabian");
        assertEquals(u.getUsername(), "jfabian");
    }

    @Test
    public void searchNotExistingUser() throws IOException, ParseException {
        UserService.loadUsersFromFile();
        User u = UserService.searchUser("alexandru");
        assertEquals("nan", u.getUsername());
    }

    @Test
    public void checkExistingCredentials() throws IOException, ParseException, UsernameOrPasswordDoesNotExistException {
        UserService.loadUsersFromFile();
        User u = UserService.checkCredentials("jfabian", Base64.getEncoder().encodeToString("pass2".getBytes()));
        assertEquals("jfabian", u.getUsername());
        assertEquals(Base64.getEncoder().encodeToString("pass2".getBytes()), u.getPassword());
    }

    @Test
    public void checkNonExistingCredentialsJUnit3() throws IOException, ParseException {
       UserService.loadUsersFromFile();
       try{
           User u = UserService.checkCredentials("diurasoc", Base64.getEncoder().encodeToString("parolaincorecta".getBytes()));
           fail();
       } catch(UsernameOrPasswordDoesNotExistException e){
           assertEquals("Username or password invalid. Please try again !", e.getMessage());
       }
    }

    @Test(expected = UsernameOrPasswordDoesNotExistException.class)
    public void checkNonExistingPasswordInCredentials() throws UsernameOrPasswordDoesNotExistException, IOException, ParseException {
        UserService.loadUsersFromFile();
        UserService.checkCredentials("diurasoc", Base64.getEncoder().encodeToString("parolaIncorecta".getBytes()));
    }

    @Test(expected = UsernameOrPasswordDoesNotExistException.class)
    public void checkNonExistingUsernameInCredentials() throws UsernameOrPasswordDoesNotExistException, IOException, ParseException {
        UserService.loadUsersFromFile();
        UserService.checkCredentials("cineva", Base64.getEncoder().encodeToString("pass1".getBytes()));
    }
}
