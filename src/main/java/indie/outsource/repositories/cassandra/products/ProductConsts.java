package indie.outsource.repositories.cassandra.products;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public final class ProductConsts {

    public static final String BY_ID_TABLE_NAME = "products_by_id";

    public static final CqlIdentifier NAME = CqlIdentifier.fromCql("name");
    public static final CqlIdentifier QUANTITY = CqlIdentifier.fromCql("quantity");
    public static final CqlIdentifier ID = CqlIdentifier.fromCql("product_id");
    public static final CqlIdentifier PRICE = CqlIdentifier.fromCql("price");
    public static final CqlIdentifier GROWTH_STAGE = CqlIdentifier.fromCql("growthStage");
    public static final CqlIdentifier COLOR = CqlIdentifier.fromCql("color");
    public static final CqlIdentifier IS_EDIBLE = CqlIdentifier.fromCql("edible");
    public static final CqlIdentifier HEIGHT = CqlIdentifier.fromCql("height");
    public static final CqlIdentifier WEIGHT = CqlIdentifier.fromCql("weight");
    public static final CqlIdentifier DISCRIMINATOR = CqlIdentifier.fromCql("discriminator");
}
