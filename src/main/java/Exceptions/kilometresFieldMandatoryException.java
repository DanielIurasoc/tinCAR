package Exceptions;

public class kilometresFieldMandatoryException extends Exception{
    public kilometresFieldMandatoryException(String message){
        super(message);
    }

    public String toString(){
        return this.getMessage();
    }
}
