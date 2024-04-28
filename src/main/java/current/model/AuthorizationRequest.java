package current.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthorizationRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String messageId;

    @NotNull
    @Valid
    private Amount transactionAmount;
}
