package indie.outsource.repositories.cassandra.transactions;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
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

    @CqlName("item_id")
    private int itemId;
    private int amount;
    @CqlName("product_id")
    private int productId;
    private double price;

}
