package hire.me.model.dao.daoFactory;

import hire.me.model.entity.periodical.Theme;

import java.util.List;

public interface ThemeDao extends AbstractDao<Theme> {
    List<Theme> findAll();
}
