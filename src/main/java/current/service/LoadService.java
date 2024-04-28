package current.service;

import current.exception.DuplicateMessageIdException;
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
public class LoadService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public LoadResponse processLoad(LoadRequest loadRequest) throws Exception {
        String userId = loadRequest.getUserId();
        String messageId = loadRequest.getMessageId();
        double transactionAmount = Double.parseDouble(loadRequest.getTransactionAmount().getAmount());
        String currency = loadRequest.getTransactionAmount().getCurrency();
        DebitCredit debitCredit = loadRequest.getTransactionAmount().getDebitOrCredit();

        //Check if Transaction with message ID already exists
        isTransactionWithMessageIdPresent(messageId);


        User user = userRepository.findByUserId(userId);


        if (user == null) {
            user = createNewUser(userId, transactionAmount);
        } else {
            user = updateUserBalance(user, transactionAmount);
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

    private User updateUserBalance(User user, double transactionAmount) {
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
