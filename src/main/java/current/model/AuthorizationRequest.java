package current.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorizationRequest {
    private String userId;
    private String messageId;
    private Amount transactionAmount;
}
