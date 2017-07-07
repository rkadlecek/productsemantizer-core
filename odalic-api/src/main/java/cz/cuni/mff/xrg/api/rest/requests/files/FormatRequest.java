package cz.cuni.mff.xrg.api.rest.requests.files;

public class FormatRequest {

    private String charset;
    private Character commentMarker;
    private Character delimiter;
    private boolean emptyLinesIgnored;
    private Character escapeCharacter;
    private Character quoteCharacter;

    private final String DEFAULT_CHARSET = "UTF-8";
    private final Character DEFAULT_COMMENT_MARKER = null;
    private final boolean DEFAULT_EMPTY_LINES_IGNORED = true;
    private final Character DEFAULT_ESCAPE_CHARACTER = null;

    public FormatRequest() {
        super();
        setCharset(DEFAULT_CHARSET);
        setCommentMarker(DEFAULT_COMMENT_MARKER);
        setEmptyLinesIgnored(DEFAULT_EMPTY_LINES_IGNORED);
        setEscapeCharacter(DEFAULT_ESCAPE_CHARACTER);
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Character getCommentMarker() {
        return commentMarker;
    }

    public void setCommentMarker(Character commentMarker) {
        this.commentMarker = commentMarker;
    }

    public Character getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(Character delimiter) {
        this.delimiter = delimiter;
    }

    public boolean isEmptyLinesIgnored() {
        return emptyLinesIgnored;
    }

    public void setEmptyLinesIgnored(boolean emptyLinesIgnored) {
        this.emptyLinesIgnored = emptyLinesIgnored;
    }

    public Character getEscapeCharacter() {
        return escapeCharacter;
    }

    public void setEscapeCharacter(Character escapeCharacter) {
        this.escapeCharacter = escapeCharacter;
    }

    public Character getQuoteCharacter() {
        return quoteCharacter;
    }

    public void setQuoteCharacter(Character quoteCharacter) {
        this.quoteCharacter = quoteCharacter;
    }
}
