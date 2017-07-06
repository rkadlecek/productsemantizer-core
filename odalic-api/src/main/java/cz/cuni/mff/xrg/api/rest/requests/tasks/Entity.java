package cz.cuni.mff.xrg.api.rest.requests.tasks;

public class Entity {

    public static final String PREFIX_SEPARATOR = ":";

    private Prefix prefix;
    private String tail;
    private String label;

    public Entity() { super(); }

    public Prefix getPrefix() {
        return prefix;
    }

    public void setPrefix(Prefix prefix) {
        this.prefix = prefix;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return prefix{@value #PREFIX_SEPARATOR}tail, if prefix not {@code null}, otherwise the same as
     *         {@link #getResource()}
     */
    public String getPrefixed() {
        if (this.prefix == null) {
            return this.tail;
        }

        return this.prefix.getWith() + PREFIX_SEPARATOR + this.tail;
    }

    /**
     * @return the expanded resource ID
     */
    public String getResource() {
        if (this.prefix == null) {
            return this.tail;
        }
        return this.prefix.getWhat() + this.tail;
    }

    /**
     * @return the part of the resources ID that follows the part substitued by prefix, {@code null}
     *         if the prefix is not defined
     */
    public String getTail() {
        if (this.prefix == null) {
            return null;
        }
        return this.tail;
    }

    @Override
    public String toString() {
        return "Entity [prefix=" + this.prefix + ", suffix=" + this.tail + ", label=" + this.label
                + ", getResource()=" + getResource() + "]";
    }
}
