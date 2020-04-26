package Services;

import Exceptions.UsernameOrPasswordDoesNotExistException;
import Model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

public class UserService {

    private static List<User> users;
    private static final Path USERS_PATH = FileSystemService.getPathToFile("config", "users.json");

    public static void loadUsersFromFile() throws IOException {

        if (!Files.exists(USERS_PATH)) {
            FileUtils.copyURLToFile(UserService.class.getClassLoader().getResource("users.json"), USERS_PATH.toFile());
        }

        ObjectMapper objectMapper = new ObjectMapper();

        users = objectMapper.readValue(USERS_PATH.toFile(), new TypeReference<List<User>>() {
        });
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
