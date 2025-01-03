package indie.outsource.repositories.cassandra.transactions;

import indie.outsource.model.Client;
import indie.outsource.model.Transaction;
import indie.outsource.model.TransactionItem;
import indie.outsource.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class CassTransaction extends Transaction {

    private final TransactionDao transactionDao;
    private final ProductRepository productRepository;

    public CassTransaction(int id, Client client, TransactionDao transactionDao, ProductRepository productRepository) {
        super(id, client);
        this.transactionDao = transactionDao;
        this.productRepository = productRepository;
    }

    public List<TransactionItem> getItems() {
        List<TransactionItem> items = new ArrayList<TransactionItem>();
        List<CassTransactionItemEntity> cassItems = transactionDao.findItemsByTransactionId(this.getId()).toList();
        for (CassTransactionItemEntity cassItem : cassItems) {
            TransactionItem transactionItem = new TransactionItem();
            transactionItem.setId(cassItem.getItem_id());
            transactionItem.setAmount(cassItem.getAmount());
            transactionItem.setPrice(cassItem.getPrice());
            transactionItem.setProduct(productRepository.getById(cassItem.getProduct_id()).getProduct());
            items.add(transactionItem);
        }
        return items;
    }

    public void addTransactionItem(TransactionItem item){
        throw new UnsupportedOperationException("This transaction have already been saved, thus it's items are immutable.");
    }
}
