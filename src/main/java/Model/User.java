package Model;

public class User {

    private final String username;
    private final String password;
    private final String role;
    private String phone;
    private String full_name;
    private String city;

    public User(String username, String password, String role, String phone1, String full_name1, String city1) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.phone = phone1;
        this.full_name = full_name1;
        this.city = city1;
    }

    public String getUsername() { return username; }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getPhone() { return phone; }

    public String getFull_name() { return full_name; }

    public String getCity() { return city; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setFull_name(String name) { this.full_name = name; }

    public void setCity(String city) { this.city = city; }
}

