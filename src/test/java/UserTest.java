import Model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void gettersAndSettersTest(){
        User user = new User("username1", "pass1","admin", "1234567899", "Fabian", "Timisoara");
        assertEquals("username1", user.getUsername());
        assertEquals("pass1", user.getPassword());
        assertEquals("admin", user.getRole());
        assertEquals("1234567899", user.getPhone());
        assertEquals("Fabian", user.getFull_name());
        assertEquals("Timisoara", user.getCity());
        user.setCity("Bucuresti");
        assertEquals("Bucuresti", user.getCity());
        user.setFull_name("Cristian");
        assertEquals("Cristian", user.getFull_name());
        user.setPhone("0727928521");
        assertEquals("0727928521", user.getPhone());

    }
}
