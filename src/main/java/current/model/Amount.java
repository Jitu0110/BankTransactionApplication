package current.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Amount {

        @NotBlank
        private String amount;

        @NotBlank
        private String currency;

        @NotNull
        private DebitCredit debitOrCredit;

}
