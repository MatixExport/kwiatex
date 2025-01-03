package indie.outsource.repositories.cassandra.transactions;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public final class TransactionConsts {

    public static final String BY_CLIENT_TABLE_NAME = "transactions_by_client";
    public static final String BY_ID_TABLE_NAME = "transactions_by_id";
    public static final String ITEMS_BY_TRANSACTION_TABLE_NAME = "items_by_transaction";

    public static final CqlIdentifier TRANSACTION_ID = CqlIdentifier.fromCql("transaction_id");
    public static final CqlIdentifier ITEM_ID = CqlIdentifier.fromCql("item_id");
    public static final CqlIdentifier CLIENT_ID = CqlIdentifier.fromCql("client_id");
    public static final CqlIdentifier PRODUCT_ID = CqlIdentifier.fromCql("product_id");
    public static final CqlIdentifier AMOUNT = CqlIdentifier.fromCql("amount");
    public static final CqlIdentifier PRICE = CqlIdentifier.fromCql("price");
}
