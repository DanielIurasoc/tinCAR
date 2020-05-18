package Model;
//"owner","status","title","description","Fuel type","Transmission",
// "Kilometres","First Registration","picture","phone"
public class Announcement {
    private String id;
    private String owner;
    private String status;
    private String price;
    private String title;
    private String description;
    private String fuel_type;
    private String transmission;
    private String kilometres;
    private String first_registration;
    private String picture;
    private String phone_number;

    public Announcement(String id, String owner, String status, String price, String title, String description, String fuel_type,String transmission, String kilometres, String first_registration, String picture, String phone_number){
        this.id = id;
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

    public String getId() { return id; }

    public String getPrice() { return price; }

    public String getOwner() { return owner; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getFuel_type() { return fuel_type; }

    public void setFuel_type(String fuel_type) { this.fuel_type = fuel_type; }

    public String getTransmission() { return transmission; }

    public void setTransmission(String transmission) { this.transmission = transmission; }

    public String getKilometres() { return kilometres; }

    public void setKilometres(String kilometres) { this.kilometres = kilometres; }

    public String getFirst_registration() { return first_registration; }

    public void setFirst_registration(String first_registration) { this.first_registration = first_registration; }

    public String getPicture() { return picture; }

    public void setPicture(String picture) { this.picture = picture; }

    public String getPhone_number() { return phone_number; }

    public void setPhone_number(String phone_number) { this.phone_number = phone_number; }

    public void setOwner(String owner) { this.owner = owner; }

    public void setPrice(String price) { this.price = price; }

    public void setId(String id) { this.id = id; }
}
