package current.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class LoadRequest {
    @NotBlank
    private String userId;

    @NotBlank
    private String messageId;

    @NotNull
    @Valid
    private Amount transactionAmount;

}
