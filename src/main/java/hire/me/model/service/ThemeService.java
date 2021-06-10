package hire.me.model.service;

import hire.me.model.dao.daoFactory.DaoFactory;
import hire.me.model.dao.daoFactory.PrivateAccountDao;
import hire.me.model.dao.daoFactory.ThemeDao;
import hire.me.model.entity.language.Language;
import hire.me.model.entity.periodical.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThemeService {
    private static final Logger logger = LogManager.getLogger(ThemeService.class);

    private DaoFactory daoFactory;
    private static ThemeService instance;

    private ThemeService() {
        daoFactory = DaoFactory.getInstance();
    }

    public static ThemeService getInstance() {
        if (instance == null) {
            synchronized (ThemeService.class) {
                if (instance == null) {
                    instance = new ThemeService();
                }
            }
        }
        return instance;
    }

    public Theme getThemeById(long id) {
        ThemeDao dao = daoFactory.createThemeDao();
        return dao.findById(id).get();
    }

    public List<Theme> getThemes() {
        ThemeDao dao = daoFactory.createThemeDao();
        return dao.findAll();
    }
}
