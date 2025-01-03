package indie.outsource.repositories.cassandra.transactions;

import indie.outsource.model.Client;
import indie.outsource.model.Transaction;
import indie.outsource.model.TransactionItem;

import java.util.ArrayList;
import java.util.List;

public class CassTransaction extends Transaction {

    private final TransactionDao transactionDao;

    public CassTransaction(int id, Client client, TransactionDao transactionDao) {
        super(id, client);
        this.transactionDao = transactionDao;
    }

    @Override
    public List<TransactionItem> getItems() {
        if(!super.getItems().isEmpty()) {
            return super.getItems();
        }
        List<TransactionItem> items = new ArrayList<TransactionItem>();
        List<CassTransactionProduct> cassItems = transactionDao.findItemsByTransactionId(this.getId()).toList();
        for (CassTransactionProduct cassItem : cassItems) {
            TransactionItem transactionItem = cassItem.toTransactionItem();
            items.add(transactionItem);
        }
        setItems(items);
        return items;
    }

    public void addTransactionItem(TransactionItem item){
        throw new UnsupportedOperationException("This transaction have already been saved, thus it's items are immutable.");
    }

    public Transaction toDomainModel(){
        Transaction transaction = new Transaction(getId(), getClient());
        transaction.setItems(getItems());
        return transaction;
    }
}
