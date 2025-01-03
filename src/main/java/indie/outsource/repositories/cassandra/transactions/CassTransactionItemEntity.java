package indie.outsource.repositories.cassandra.transactions;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class CassTransactionItemEntity {

    private int item_id;
    private int amount;
    private int product_id;
    private double price;

}
