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
    //private static final Path USERS_PATH = FileSystemService.getPathToFile("config", "users.json");

    public static void loadUsersFromFile() throws IOException, ParseException {

        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        FileReader reader = new FileReader("../tinCAR/src/main/resources/users.json");
        // Read JSON file
        Object obj = jsonParser.parse(reader);

        JSONArray userList = (JSONArray) obj;

        for (Object value : userList) {
            JSONObject o = (JSONObject) value;
            User u = new User((String) o.get("username"), (String) o.get("password"), (String) o.get("role"));
            users.add(u);
        }
    }

    public static void printUsers(){
        for(User user : users){
            System.out.println(user.getUsername() + " " + user.getPassword());
        }
    }

    public static String checkCredentials(String username, String password) throws UsernameOrPasswordDoesNotExistException {
        for (User user : users) {
            if (Objects.equals(username, user.getUsername()))
                if (Objects.equals(password, user.getPassword()))
                    return user.getRole();
                else throw new UsernameOrPasswordDoesNotExistException("Username or password invalid. Please try again !");
        }
        throw new UsernameOrPasswordDoesNotExistException("Username or password invalid. Please try again !");
    }

    /*public static void encodePasswords() throws Exception {
        for(User user : users){
            user.setPassword(encodePassword(user.getUsername(), user.getPassword()));
        }
        persistUsers();
        try{
            loadUsersFromFile();
        }catch(IOException e){throw new Exception("Couldn't read user database");}
    }*/

    /*private static void persistUsers() throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(USERS_PATH.toFile(), users);
        } catch (IOException e) {
            throw new Exception("Couldn't write users!");
        }
    }*/

    /*private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }*/

    /*private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }*/

}
