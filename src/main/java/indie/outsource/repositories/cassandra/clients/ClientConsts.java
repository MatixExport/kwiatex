package indie.outsource.repositories.cassandra.clients;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public final class ClientConsts {

    private ClientConsts() {}

    public static final String BY_NAME_TABLE_NAME = "clients_by_name";
    public static final String BY_ID_TABLE_NAME = "clients_by_id";

    public static final CqlIdentifier NAME = CqlIdentifier.fromCql("name");
    public static final CqlIdentifier SURNAME = CqlIdentifier.fromCql("surname");
    public static final CqlIdentifier ID = CqlIdentifier.fromCql("id");
    public static final CqlIdentifier ADDRESS = CqlIdentifier.fromCql("address");
}
