package indie.outsource.repositories;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.UUID;


public interface Repository<T> {
    List<T> findAll();

    T getById(ObjectId id);

    T add(T t);

    void remove(T t);
}
