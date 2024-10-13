package indie.outsource.repositories;

import java.util.List;


public interface Repository<T> {
    List<T> findAll();

    T getById(int id);

    T add(T t);

    void remove(T t);
}
