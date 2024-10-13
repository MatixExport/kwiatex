package indie.outsource.repositories;

import indie.outsource.model.Client;
import indie.outsource.model.Transaction;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TransactionRelationalRepository extends RelationalRepository<Transaction> implements TransactionRepository {
    public TransactionRelationalRepository(EntityManager em) {
        super(Transaction.class, em);
    }


    @Override
    public List<Transaction> getByClient(Client client) {
        return client.getTransactions();
    }
}
