package indie.outsource.repositories.cassandra.transactions;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CassTransactionEntity {

    @CqlName("transaction_id")
    private int transactionId;
    @CqlName("client_id")
    private int clientId;

}
