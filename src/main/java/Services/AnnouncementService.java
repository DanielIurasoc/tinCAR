package Services;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class AnnouncementService {

    private static JSONArray announcements = new JSONArray();

    private static void loadAnnouncementsFromFile() throws IOException, ParseException {

        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        FileReader reader = new FileReader("../tinCAR/src/main/resources/announcements.json");
        // Read JSON file
        Object obj = jsonParser.parse(reader);

        announcements = (JSONArray) obj;
    }

    public static JSONArray getAnnouncements() throws IOException, ParseException {
        announcements.clear();
        loadAnnouncementsFromFile();
        return announcements;
    }
}
