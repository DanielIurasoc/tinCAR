package Exceptions;

public class UsernameOrPasswordDoesNotExistException extends Exception{
    public UsernameOrPasswordDoesNotExistException(String message){
        super(message);
    }
}
