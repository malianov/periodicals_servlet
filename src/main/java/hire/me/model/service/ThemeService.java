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

//    public List<Theme> getAllThemes() {
//        logger.trace("inside getAllThemes()");
//        ThemeDao dao = daoFactory.createThemeDao();
//        return dao.findAll();
//    }

    public Map<String, List<Theme>> getAllThemes() {
        logger.trace("inside getAllThemes()");
        ThemeDao dao = daoFactory.createThemeDao();
        List<Theme> themes = dao.findAll();

        List<Theme> themes_en = themes.stream()
                .filter(t -> t.getLanguage().equals(Language.ENGLISH))
                .toList();

        List<Theme> themes_ua = themes.stream()
                .filter(t -> t.getLanguage().equals(Language.UKRAINIAN))
                .toList();

        List<Theme> themes_ru = themes.stream()
                .filter(t -> t.getLanguage().equals(Language.RUSSIAN))
                .toList();

        Map<String, List<Theme>> allThemesByLanguage = new HashMap<>();
        allThemesByLanguage.put("en", themes_en);
        allThemesByLanguage.put("ua", themes_ua);
        allThemesByLanguage.put("ru", themes_ru);

        return allThemesByLanguage;
    }


}
