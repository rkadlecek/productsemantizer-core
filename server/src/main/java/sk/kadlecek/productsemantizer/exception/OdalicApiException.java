package sk.kadlecek.productsemantizer.exception;

public class OdalicApiException extends Exception {

    public OdalicApiException(String msg) {
        super(msg);
    }

    public OdalicApiException(String msg, Exception e) {
        super(msg, e);
    }

}
