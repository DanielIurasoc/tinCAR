package Exceptions;

public class descriptionFieldAtLeast50CharException extends Exception{
    public descriptionFieldAtLeast50CharException(String message){
        super(message);
    }

    public String toString(){
        return this.getMessage();
    }
}
