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

    public String getTransactionInfo(){
        StringBuilder info = new StringBuilder();
//        info.append(client.getClientInfo()).append(" bought: /n");
        for(TransactionItem item : items){
            info.append(item.getTransactionItemInfo()).append("\n");
        }
        return info.toString();
    }

    public void addTransactionItem(TransactionItem item){
        items.add(item);
    }

}
