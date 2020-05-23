package Exceptions;

public class fuelTypeFieldMandatoryException extends Exception{
    public fuelTypeFieldMandatoryException(String message){
        super(message);
    }

    public String toString(){
        return this.getMessage();
    }
}
