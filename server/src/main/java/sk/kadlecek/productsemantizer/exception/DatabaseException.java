package sk.kadlecek.productsemantizer.exception;

public class DatabaseException extends Exception {

    public DatabaseException(String msg) {
        super(msg);
    }

    public DatabaseException(Exception e) {
        super(e);
    }

    public DatabaseException(String msg, Exception e) {
        super(msg, e);
    }

}
