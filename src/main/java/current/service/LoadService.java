package current.service;

import current.model.Amount;
import current.model.DebitCredit;
import current.model.LoadRequest;
import current.model.LoadResponse;
import org.springframework.stereotype.Service;

@Service
public class LoadService {
    public LoadResponse processLoad(LoadRequest loadRequest) {
        // Extract load request details - To add money to user account
        String userId = loadRequest.getUserId();
        String messageId = loadRequest.getMessageId();
        double transactionAmount = Double.parseDouble(loadRequest.getTransactionAmount().getAmount());
        String currency = loadRequest.getTransactionAmount().getCurrency();
        String debitOrCredit = String.valueOf(loadRequest.getTransactionAmount().getDebitOrCredit());

        // Business logic to process load transaction
        double updatedBalance = 0.0; // For simplicity, assume initial balance is 0

        // If it's a credit transaction, add the amount to the balance
        if ("CREDIT".equals(debitOrCredit)) {
            updatedBalance += transactionAmount;
        }

        // Create load response object
        LoadResponse loadResponse = new LoadResponse();
        loadResponse.setUserId(userId);
        loadResponse.setMessageId(messageId);

        // Set balance in the response
        Amount balance = new Amount();
        balance.setAmount(String.valueOf(updatedBalance));
        balance.setCurrency(currency);
        balance.setDebitOrCredit(DebitCredit.valueOf(debitOrCredit));
        loadResponse.setBalance(balance);

        return loadResponse;
    }
}
