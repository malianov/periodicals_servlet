package hire.me.model.dao.daoFactory;

import java.util.List;

public interface AbstractDao<T> extends AutoCloseable{

    void create(T entity);
    T findById(long id);
    List<T> findAll();
    void update(T t);
    void delete(long id);
    void close();
}