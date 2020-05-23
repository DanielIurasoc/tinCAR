package Exceptions;

public class phoneFieldMandatoryException extends Exception{
    public phoneFieldMandatoryException(String message){
        super(message);
    }

    public String toString(){
        return this.getMessage();
    }
}
