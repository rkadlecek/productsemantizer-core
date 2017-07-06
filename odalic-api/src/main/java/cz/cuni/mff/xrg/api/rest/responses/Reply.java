package cz.cuni.mff.xrg.api.rest.responses;

/**
 * <p>
 * A wrapper that either contains the actual data returned by the API implementation or any kind of
 * alternative content.
 * </p>
 *
 * <p>
 * It helps the receiver to determine the correct processing workflow by providing a type of the
 * payload in the type attribute.
 * </p>
 *
 *
 * @author Václav Brodec
 *
 */
public final class Reply {

    /**
     * Name of the URI query parameter that hold the optional string sent by a client that is sent
     * back to it as a part of the response.
     */
    public static final String STAMP_QUERY_PARAMETER_NAME = "stamp";

    private int status;

    private ReplyType type;

    private Object payload;

    private String stamp;

    @Override
    public String toString() {
        return "Reply [status=" + this.status + ", type=" + this.type + ", payload=" + this.payload
                + ", stamp=" + this.stamp + "]";
    }

    public int getStatus() {
        return status;
    }

    public ReplyType getType() {
        return type;
    }

    public Object getPayload() {
        return payload;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setType(ReplyType type) {
        this.type = type;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }
}
