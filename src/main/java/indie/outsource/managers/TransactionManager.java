package indie.outsource.managers;

import indie.outsource.model.Client;
import indie.outsource.model.Transaction;
import indie.outsource.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TransactionManager {
    private TransactionRepository transactionRepository;            // needs to be injected

    public Transaction registerTransaction(Transaction transaction) {
        return transactionRepository.add(transaction);
    }

    public Transaction getTransactionById(int id) {
        return transactionRepository.getById(id);
    }

    public List<Transaction> getClientsTransactions(Client client) {
        return transactionRepository.getByClient(client);
    }



}
