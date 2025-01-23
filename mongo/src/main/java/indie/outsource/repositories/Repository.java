package indie.outsource.repositories;

import java.util.List;
import java.util.UUID;


public interface Repository<T> {
    List<T> findAll();

    T getById(UUID id);

    T add(T t);

    void remove(T t);
}
