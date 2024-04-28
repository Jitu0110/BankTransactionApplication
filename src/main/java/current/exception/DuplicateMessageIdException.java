package current.exception;

public class DuplicateMessageIdException extends RuntimeException {
    public DuplicateMessageIdException(String messageId) {
        super("MessageId : " + messageId + " already exists in database . Please change it and try again.");
    }
}
