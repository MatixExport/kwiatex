package indie.outsource.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    public Transaction(int id, Client client) {
        this.id = id;
        this.client = client;
    }

    private int id;
    private Client client;
    private List<TransactionItem> items = new ArrayList<TransactionItem>();

    public void addTransactionItem(TransactionItem item){
        items.add(item);
    }

}
