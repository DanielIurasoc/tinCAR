import Model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTests {

    @Test
    public void fieldsTest(){
        User user = new User("username1", "pass1","admin", "1234567899", "Fabian", "Timisoara");
        assertEquals("username1", user.getUsername());
        assertEquals("pass1", user.getPassword());
        assertEquals("admin", user.getRole());
        assertEquals("1234567899", user.getPhone());
        assertEquals("Fabian", user.getFull_name());
        assertEquals("Timisoara", user.getCity());
    }
}
