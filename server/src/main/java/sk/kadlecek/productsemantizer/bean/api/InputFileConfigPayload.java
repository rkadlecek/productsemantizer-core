package sk.kadlecek.productsemantizer.bean.api;

import org.hibernate.validator.constraints.NotEmpty;

public class InputFileConfigPayload {

    private final Character DEFAULT_SEPARATOR = ',';
    private final Character DEFAULT_ENCLOSING = '"';

    @NotEmpty
    private String path;
    private Character separator;
    private Character columnEnclosing;

    public InputFileConfigPayload() { super(); }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public char getSeparator() {
        return (separator != null) ? separator : DEFAULT_SEPARATOR;
    }

    public void setSeparator(Character separator) {
        this.separator = separator;
    }

    public char getColumnEnclosing() {
        return (columnEnclosing != null) ? columnEnclosing : DEFAULT_ENCLOSING;
    }

    public void setColumnEnclosing(Character columnEnclosing) {
        this.columnEnclosing = columnEnclosing;
    }
}
