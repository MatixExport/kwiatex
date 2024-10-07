package indie.outsource.repositories;

import jakarta.transaction.Transactional;

import java.util.List;


public interface Repository<T> {
    List<T> getAll();

    T getById(int id);

    T add(T t);

    void remove(T t);
}
