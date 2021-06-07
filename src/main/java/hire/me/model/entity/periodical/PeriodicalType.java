package hire.me.model.entity.periodical;

import hire.me.model.entity.language.Language;

public class PeriodicalType {
    private int id;
    private int typeId;
    private String type;
    private Language language;


    public PeriodicalType(int id, int typeId, String type, Language language) {
        this.id = id;
        this.typeId = typeId;
        this.type = type;
        this.language = language;
    }

    public PeriodicalType(int typeId, String type, Language language) {
        this.typeId = typeId;
        this.type = type;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int type_id) {
        this.typeId = type_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
