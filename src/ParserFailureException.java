public class ParserFailureException extends RuntimeException {
    public ParserFailureException(String msg) {
            super(msg);
        }

    public String getMessage() {
        return detailMessage;
    }
    private String detailMessage;
}
