package indie.outsource.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import indie.outsource.model.AbstractEntity;
import indie.outsource.model.ProductWithInfo;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public abstract class MongoDbRepository<T extends AbstractEntity> implements Repository<T> {

    Class<T> classType;
    MongoDatabase db;
    MongoCollection<T> collection;

    public MongoDbRepository(Class<T> classType, MongoDatabase db) {
        this.classType = classType;
        this.db = db;
        this.collection = db.getCollection(classType.getSimpleName(), classType);
    }

    @Override
    public List<T> findAll() {
        this.db.getCollection(ProductWithInfo.class.getSimpleName(), ProductWithInfo.class).find();
        return collection.find().into(new ArrayList<T>());
    }

    @Override
    public T getById(ObjectId id) {
        return collection.find(new Document("_id", id)).first();
    }

    @Override
    public T add(T t) {
        ReplaceOptions options = new ReplaceOptions().upsert(true);
        Bson filter = new Document("_id", t.getId());
        collection.replaceOne(filter, t, options);
        return null;
    }

    @Override
    public void remove(T t) {
        collection.deleteOne(new Document("_id",t.getId()));
    }
}
