package Exceptions;

public class registrationFieldMandatoryException extends Exception{
    public registrationFieldMandatoryException(String message){
        super(message);
    }

    public String toString(){
        return this.getMessage();
    }
}
