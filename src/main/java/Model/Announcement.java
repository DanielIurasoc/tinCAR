package Model;

public class Announcement {
    private final String owner;
    private final String status;
    private final String price;
    private final String title;
    private final String description;
    private final String fuel_type;
    private final String transmission;
    private final String kilometres;
    private final String first_registration;
    private final String picture;
    private final String phone_number;

    public Announcement(String owner, String status, String price, String title, String description, String fuel_type,String transmission, String kilometres, String first_registration, String picture, String phone_number){
        this.owner = owner;
        this.price = price;
        this.status = status;
        this.title = title;
        this.description = description;
        this.fuel_type = fuel_type;
        this.transmission = transmission;
        this.kilometres = kilometres;
        this.first_registration = first_registration;
        this.picture = picture;
        this.phone_number = phone_number;
    }

    public String getPrice() { return price; }

    public String getOwner() { return owner; }

    public String getStatus() { return status; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public String getFuel_type() { return fuel_type; }

    public String getTransmission() { return transmission; }

    public String getKilometres() { return kilometres; }

    public String getFirst_registration() { return first_registration; }

    public String getPicture() { return picture; }

    public String getPhone_number() { return phone_number; }

}
