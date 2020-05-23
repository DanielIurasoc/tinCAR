package Exceptions;

public class priceFieldMandatoryException extends Exception{
    public priceFieldMandatoryException(String message){
        super(message);
    }

    public String toString(){
        return this.getMessage();
    }
}
