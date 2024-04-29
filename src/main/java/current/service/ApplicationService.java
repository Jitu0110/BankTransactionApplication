package current.service;

import current.exception.DuplicateMessageIdException;
import current.exception.UserNotFoundException;
import current.model.*;
import current.persistence.Transaction;
import current.persistence.User;
import current.repository.TransactionRepository;
import current.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ApplicationService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public AuthorizationResponse processAuthorization(AuthorizationRequest authorizationRequest) {
        String userId = authorizationRequest.getUserId();
        String messageId = authorizationRequest.getMessageId();
        double transactionAmount = Double.parseDouble(authorizationRequest.getTransactionAmount().getAmount());
        String currency = authorizationRequest.getTransactionAmount().getCurrency();
        DebitCredit debitCredit = authorizationRequest.getTransactionAmount().getDebitOrCredit();

        if (!debitCredit.getValue().equals("DEBIT")) {
            throw new IllegalArgumentException("Authorization request requires debitOrCredit to be 'DEBIT'");
        }

        //Check if Transaction with message ID already exists
        isTransactionWithMessageIdPresent(messageId);

        User user = userRepository.findByUserId(userId);

        if(user == null){
            throw new UserNotFoundException(userId);
        }

        //Update User balance if required and save transaction
        Transaction transaction = new Transaction();
        AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        double currentBalance = user.getBalance();

        if(currentBalance - transactionAmount >=0) {
            user.setBalance(currentBalance - transactionAmount);

            transaction.setBalance(String.valueOf(user.getBalance()));
            transaction.setMessageId(messageId);
            transaction.setTimestamp(Instant.now().toString());
            transaction.setResponseCode(ResponseCode.APPROVED);
            transaction.setTypeOfRequest(TypeOfRequest.AUTHORIZATION);
            transaction.setUserId(userId);
            transactionRepository.save(transaction);

            authorizationResponse.setResponseCode(ResponseCode.APPROVED);
        }
        else{

            transaction.setBalance(String.valueOf(user.getBalance()));
            transaction.setMessageId(messageId);
            transaction.setTimestamp(Instant.now().toString());
            transaction.setResponseCode(ResponseCode.DECLINED);
            transaction.setTypeOfRequest(TypeOfRequest.AUTHORIZATION);
            transaction.setUserId(userId);
            transactionRepository.save(transaction);

            authorizationResponse.setResponseCode(ResponseCode.DECLINED);
        }

        user =  userRepository.save(user);

        Amount amount = new Amount();
        amount.setAmount(String.valueOf(user.getBalance()));
        amount.setCurrency(currency);
        amount.setDebitOrCredit(DebitCredit.DEBIT);
        authorizationResponse.setMessageId(messageId);
        authorizationResponse.setUserId(userId);
        authorizationResponse.setBalance(amount);


        return authorizationResponse;
    }

    @Transactional
    public LoadResponse processLoad(LoadRequest loadRequest) throws Exception {
        String userId = loadRequest.getUserId();
        String messageId = loadRequest.getMessageId();
        double transactionAmount = Double.parseDouble(loadRequest.getTransactionAmount().getAmount());
        String currency = loadRequest.getTransactionAmount().getCurrency();
        DebitCredit debitCredit = loadRequest.getTransactionAmount().getDebitOrCredit();

        if (!debitCredit.getValue().equals("CREDIT")) {
            throw new IllegalArgumentException("Load request requires debitOrCredit to be 'CREDIT'");
        }

        //Check if Transaction with message ID already exists
        isTransactionWithMessageIdPresent(messageId);


        User user = userRepository.findByUserId(userId);


        if (user == null) {
            user = createNewUser(userId, transactionAmount);
        } else {
            user = updateUserBalanceLoad(user, transactionAmount);
        }


        userRepository.save(user);


        //Save the transaction
        Transaction transaction = new Transaction();
        transaction.setBalance(String.valueOf(user.getBalance()));
        transaction.setMessageId(messageId);
        transaction.setTimestamp(Instant.now().toString());
        transaction.setResponseCode(ResponseCode.APPROVED);
        transaction.setTypeOfRequest(TypeOfRequest.LOAD);
        transaction.setUserId(userId);

        transactionRepository.save(transaction);


        LoadResponse loadResponse = new LoadResponse();
        Amount amount = new Amount();
        amount.setAmount(String.valueOf(user.getBalance()));
        amount.setCurrency(currency);
        amount.setDebitOrCredit(DebitCredit.CREDIT);
        loadResponse.setMessageId(messageId);
        loadResponse.setUserId(userId);
        loadResponse.setBalance(amount);

        return loadResponse;
    }

    private User createNewUser(String userId, double transactionAmount) {
        User user = new User();
        user.setUserId(userId);
        user.setBalance(transactionAmount);
        return user;
    }

    private User updateUserBalanceLoad(User user, double transactionAmount) {
        double currentBalance = user.getBalance();
        user.setBalance(currentBalance + transactionAmount);
        return user;
    }


    private void isTransactionWithMessageIdPresent(String messageId) {
        Transaction transaction = transactionRepository.findByMessageId(messageId);
        if (transaction != null) {
            throw new DuplicateMessageIdException(messageId);
        }
    }
}
