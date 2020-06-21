import Services.AnnouncementService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AnnouncementServiceTest {
    @Test
    public void getAnnouncementsTest() throws IOException, ParseException {
        JSONArray announcements = AnnouncementService.getAnnouncements();
        JSONObject x;

        x = (JSONObject) announcements.get(0);
        assertEquals("jfabian", x.get("owner"));
        assertEquals("0725 912 234", x.get("phone"));

        x = (JSONObject) announcements.get(1);
        assertEquals("alxionescu", x.get("owner"));
        assertEquals("0736 562 526", x.get("phone"));

        x = (JSONObject) announcements.get(2);
        assertEquals("jfabian", x.get("owner"));
        assertEquals("0734 159 950", x.get("phone"));
    }
}
