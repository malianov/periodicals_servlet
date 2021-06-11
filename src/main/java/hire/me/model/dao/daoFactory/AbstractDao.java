package hire.me.model.dao.daoFactory;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<T> extends AutoCloseable {
    void create(T entity);

    Optional<T> findById(long id);

    List<T> findAll();

    void update(T t);

    void delete(long id);

    void close();
}