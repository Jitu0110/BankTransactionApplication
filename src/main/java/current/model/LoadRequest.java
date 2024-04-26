package current.model;

import lombok.Data;

@Data
public class LoadRequest {
    private String userId;
    private String messageId;
    private Amount transactionAmount;

}
