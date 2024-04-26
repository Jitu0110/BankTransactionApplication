package current.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Amount {
        private String amount;
        private String currency;
        private DebitCredit debitOrCredit;

}
