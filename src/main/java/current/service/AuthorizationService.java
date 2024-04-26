package current.service;

import current.model.*;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    public AuthorizationResponse processAuthorization(AuthorizationRequest authorizationRequest) {        // Extract authorization request details
        String userId = authorizationRequest.getUserId();
        String messageId = authorizationRequest.getMessageId();
        String responseCode = "APPROVED"; // Assume all authorizations are approved

        // Assume some business logic to check user balance and process authorization
        // Here, we'll just approve all authorizations and set balance to 0
        double transactionAmount = Double.parseDouble(authorizationRequest.getTransactionAmount().getAmount());
        String currency = authorizationRequest.getTransactionAmount().getCurrency();
        double updatedBalance = 0.0; // For simplicity, assuming balance is set to 0 after every authorization

        // Create authorization response object
        AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        authorizationResponse.setUserId(userId);
        authorizationResponse.setMessageId(messageId);
        authorizationResponse.setResponseCode(ResponseCode.APPROVED);

        // Set balance in the response
        Amount balance = new Amount();
        balance.setAmount(String.valueOf(updatedBalance));
        balance.setCurrency(currency);
        balance.setDebitOrCredit(DebitCredit.valueOf("DEBIT"));
        authorizationResponse.setBalance(balance);

        return authorizationResponse;
    }
}
