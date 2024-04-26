package current.model;

import lombok.Data;

@Data
public class LoadResponse {
    private String userId;
    private String messageId;
    private Amount balance;
}
