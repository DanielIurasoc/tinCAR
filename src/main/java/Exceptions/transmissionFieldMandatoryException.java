package Exceptions;

public class transmissionFieldMandatoryException extends Exception{
    public transmissionFieldMandatoryException(String message){
        super(message);
    }

    public String toString(){
        return this.getMessage();
    }
}
