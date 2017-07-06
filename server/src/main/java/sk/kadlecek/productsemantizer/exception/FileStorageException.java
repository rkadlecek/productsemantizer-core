package sk.kadlecek.productsemantizer.exception;

public class FileStorageException extends Exception {

    public FileStorageException(String msg) {
        super(msg);
    }

    public FileStorageException(String msg, Exception e) {
        super(msg, e);
    }

}
