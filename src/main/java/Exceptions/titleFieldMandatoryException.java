package Exceptions;

public class titleFieldMandatoryException extends Exception{
    public titleFieldMandatoryException(String message){
        super(message);
    }

    public String toString(){
        return this.getMessage();
    }
}
