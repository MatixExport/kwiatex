package indie.outsource.repositories.cassandra.transactions;

import indie.outsource.model.Client;
import indie.outsource.model.Transaction;
import indie.outsource.model.TransactionItem;
import indie.outsource.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class CassTransaction extends Transaction {

    private final TransactionDao transactionDao;

    public CassTransaction(int id, Client client, TransactionDao transactionDao) {
        super(id, client);
        this.transactionDao = transactionDao;
    }

    public List<TransactionItem> getItems() {
        List<TransactionItem> items = new ArrayList<TransactionItem>();
        List<CassTransactionProduct> cassItems = transactionDao.findItemsByTransactionId(this.getId()).toList();
        for (CassTransactionProduct cassItem : cassItems) {
            TransactionItem transactionItem = cassItem.toTransactionItem();
            items.add(transactionItem);
        }
        return items;
    }

    public void addTransactionItem(TransactionItem item){
        throw new UnsupportedOperationException("This transaction have already been saved, thus it's items are immutable.");
    }
}
