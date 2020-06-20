package Services;

import Exceptions.UsernameOrPasswordDoesNotExistException;
import Model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class UserService {

    private final static ArrayList<User> users = new ArrayList<>();

    public static void loadUsersFromFile(String path) throws IOException, ParseException {

        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        FileReader reader = new FileReader(path);
        // Read JSON file
        Object obj = jsonParser.parse(reader);

        JSONArray userList = (JSONArray) obj;

        for (Object value : userList) {
            JSONObject o = (JSONObject) value;
            User u = new User((String) o.get("username"), (String) o.get("password"), (String) o.get("role"), (String) o.get("phone"), (String) o.get("full name"), (String) o.get("city"));
            users.add(u);
        }
    }

    public static User searchUser(String username){
        for(User u : users)
            if(u.getUsername().equals(username))
                return u;
        return new User("nan","nan","nan","nan","nan","nan");
    }

    public static User checkCredentials(String username, String password) throws UsernameOrPasswordDoesNotExistException {
        for (User user : users) {
            if (Objects.equals(username, user.getUsername()))
                if (Objects.equals(password, user.getPassword()))
                    return user;
                else throw new UsernameOrPasswordDoesNotExistException("Username or password invalid. Please try again !");
        }
        throw new UsernameOrPasswordDoesNotExistException("Username or password invalid. Please try again !");
    }
}
