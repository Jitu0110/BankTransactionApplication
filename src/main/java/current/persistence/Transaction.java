package current.persistence;

import current.model.ResponseCode;
import current.model.TypeOfRequest;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
/*
To capture each Event in the application :
 */
@Data
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;
    private String messageId;
    private String balance;
    private String userId;
    private String timestamp;

    //Store : Load OR Authorization
    private TypeOfRequest typeOfRequest;

    //Store : Approved OR Declined
    private ResponseCode responseCode;

}