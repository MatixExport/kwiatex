package indie.outsource.repositories;

import java.util.List;

public interface Repository<T> {
    List<T> getAll();

    T getById(int id);

    T add(T t);

    T remove(T t);
}
