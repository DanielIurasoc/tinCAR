package Services;

import Model.Announcement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AnnouncementService {

    private static ArrayList<Announcement> announcements = new ArrayList<>();

    private static void loadAnnouncementsFromFile() throws IOException, ParseException {

        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        FileReader reader = new FileReader("../tinCAR/src/main/resources/announcements.json");
        // Read JSON file
        Object obj = jsonParser.parse(reader);
        JSONArray announcementsList = (JSONArray) obj;

        for (Object value : announcementsList) {
            JSONObject o = (JSONObject) value;
            Announcement announcement = new Announcement((String) o.get("id"), (String) o.get("owner"), (String) o.get("status"), (String) o.get("price"), (String) o.get("title"), (String) o.get("description"), (String) o.get("Fuel type"), (String) o.get("Transmission"), (String) o.get("Transmission"), (String) o.get("First Registration"), (String) o.get("picture"), (String) o.get("phone"));
            announcements.add(announcement);
        }
    }

    public static ArrayList<Announcement> getAnnouncements() throws IOException, ParseException {
        announcements.clear();
        loadAnnouncementsFromFile();
        return announcements;
    }

    /*public static void addAnnouncement(Announcement a){
        announcements.add(a);
    }*/
}
