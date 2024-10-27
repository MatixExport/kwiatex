package indie.outsource;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;

import java.util.function.Consumer;

public class Util {
    public static void inSession(MongoClient client, Consumer<MongoClient> work) {
        ClientSession session = client.startSession();
        try {
            session.startTransaction();
            work.accept(client);
            session.commitTransaction();
        }
        catch (Exception e) {
            session.abortTransaction();
            throw e;
        }
        finally {
            session.close();
        }
    }
}
