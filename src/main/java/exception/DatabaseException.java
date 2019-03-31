package exception;

public class DatabaseException extends Exception{
    public DatabaseException(String description) {
        super(description);
    }
}
