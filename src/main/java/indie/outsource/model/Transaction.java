package indie.outsource.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Transaction {

    public Transaction(int id, Client client) {
        this.id = id;
        this.client = client;
    }

    private int id;
    private Client client;
    private final List<TransactionItem> items = new ArrayList<TransactionItem>();

    public void addTransactionItem(TransactionItem item){
        items.add(item);
    }

}
