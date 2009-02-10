package server;

/**
 * Inner class
 * UnknownUserException is an exception thrown when a user
 * has been kicked or banned from the server
 * @author Eric Kisner
 */
public class UnknownUserException extends Exception{

    String error;

    /**
     * Default constructor
     */
    public UnknownUserException(){
        super();
        error = "You have been kicked by an admin! If you want to play, you have to log in again.";
    }

    /**
     * Overloaded constructor
     * @param err
     */
    public UnknownUserException(String err){
        super(err);
        error = err;
    }

    /**
     * Gets the exception message
     * @return
     */
    @Override
    public String getMessage(){
        return error;
    }
}