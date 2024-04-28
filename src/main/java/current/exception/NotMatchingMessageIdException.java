package current.exception;

public class NotMatchingMessageIdException extends RuntimeException {
    public NotMatchingMessageIdException(String messageId1 , String messageId2) {
        super("MessageID in path variable and in JSON body does not match! " + messageId1 + " and " + messageId2 + ". Please change it and try again.");
    }
}
