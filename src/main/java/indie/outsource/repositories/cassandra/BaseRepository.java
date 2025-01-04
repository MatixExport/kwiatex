package indie.outsource.repositories.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import lombok.Getter;

@Getter
public class BaseRepository {

    private final CqlSession session;

    public BaseRepository(CqlSession session) {
        this.session = session;
    }
}
