package hire.me.model.dao.daoFactory;

import hire.me.model.entity.periodical.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<T> extends AutoCloseable{
    static final Logger logger = LogManager.getLogger(AbstractDao.class);

    void create(T entity);
    Optional<T> findById(long id);
    List<T> findAll();
    void update(T t);
    void delete(long id);
    void close();
}