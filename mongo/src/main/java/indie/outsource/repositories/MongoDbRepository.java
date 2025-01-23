package indie.outsource.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import indie.outsource.documents.AbstractEntityDoc;
import indie.outsource.model.AbstractEntity;
import indie.outsource.model.ProductWithInfo;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public abstract class MongoDbRepository<T extends AbstractEntityDoc> {

    Class<T> classType;
    MongoDatabase db;
    MongoCollection<T> collection;

    protected MongoDbRepository(Class<T> classType, MongoDatabase db) {
        this.classType = classType;
        this.db = db;
        this.collection = db.getCollection(classType.getSimpleName(), classType);
    }

    protected List<T> mongoFindAll() {
        this.db.getCollection(classType.getSimpleName(), classType).find();
        return collection.find().into(new ArrayList<T>());
    }

    protected long mongoCount(){
        return this.db.getCollection(classType.getSimpleName(), classType).countDocuments();
    }

    protected T mongoGetById(UUID id) {
        return collection.find(new Document("_id", id)).first();
    }

    protected T mongoAdd(T t) {
        ReplaceOptions options = new ReplaceOptions().upsert(true);
        Bson filter = new Document("_id", t.getId());
        collection.replaceOne(filter, t, options);
        return null;
    }

    protected void mongoRemove(T t) {
        collection.deleteOne(new Document("_id",t.getId()));
    }
}
