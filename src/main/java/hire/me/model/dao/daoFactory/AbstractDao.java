package hire.me.model.dao.daoFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public interface AbstractDao<T> extends AutoCloseable{
    static final Logger logger = LogManager.getLogger(AbstractDao.class);

    void create(T entity);
    T findById(long id);
    List<T> findAll();
    void update(T t);
    void delete(long id);
    void close();
}