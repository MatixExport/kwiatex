package indie.outsource.repositories;

import indie.outsource.model.Client;
import indie.outsource.model.Transaction;

import java.util.List;

public interface TransactionRepository extends Repository<Transaction> {
    public List<Transaction> getByClient(Client client);
}
