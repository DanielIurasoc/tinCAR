package Model;
//"owner","status","title","description","Fuel type","Transmission",
// "Kilometres","First Registration","picture","phone"
public class Announcement {
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

    public boolean equals(Object o){
        if(o instanceof Announcement ){
            Announcement add = (Announcement)o;
            if(!add.getPrice().equals(this.getPrice()))
                return false;
            if(!add.getOwner().equals(this.getOwner()))
                return false;
            if(!add.getStatus().equals(this.getStatus()))
                return false;
            if(!add.getTitle().equals(this.getTitle()))
                return false;
            if(!add.getDescription().equals(this.getDescription()))
                return false;
            if(!add.getFuel_type().equals(this.getFuel_type()))
                return false;
            if(!add.getTransmission().equals(this.getTransmission()))
                return false;
            if(!add.getKilometres().equals(this.getKilometres()))
                return false;
            if(!add.getFirst_registration().equals(this.getFirst_registration()))
                return false;
            if(!add.getPicture().equals(this.getPicture()))
                return false;
            if(!add.getPhone_number().equals(this.getPhone_number()))
                return false;
        }
        return true;
    }

}
