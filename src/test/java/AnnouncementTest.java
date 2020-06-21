import Model.Announcement;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AnnouncementTest {
    @Test
    public void gettersTest(){
        Announcement add1 = new Announcement("alxionescu",
                "accepted", "19900 euro", "Audi A6 2014",
                "description1",
                "diesel", "manual",
                "71744", "2014",
                "AudiA6.jpg", "0766 094 644");
        assertEquals("alxionescu", add1.getOwner());
        assertEquals("accepted", add1.getStatus());
        assertEquals("19900 euro", add1.getPrice());
        assertEquals("Audi A6 2014", add1.getTitle());
        assertEquals("description1", add1.getDescription());
        assertEquals("diesel", add1.getFuel_type());
        assertEquals("manual", add1.getTransmission());
        assertEquals("71744", add1.getKilometres());
        assertEquals("2014", add1.getFirst_registration());
        assertEquals("AudiA6.jpg", add1.getPicture());
        assertEquals("0766 094 644", add1.getPhone_number());

    }
}
