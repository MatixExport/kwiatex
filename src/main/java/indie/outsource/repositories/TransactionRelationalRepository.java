package indie.outsource.repositories;

import indie.outsource.model.Client;
import indie.outsource.model.Transaction;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TransactionRelationalRepository extends RelationalRepository<Transaction> implements TransactionRepository {
    public TransactionRelationalRepository(EntityManager em) {
        super(Transaction.class, em);
    }


    //This is instead solved by making association between transaction and client bidirectional
    @Override
    public List<Transaction> getByClient(Client client) {
        return List.of();
    }
}
