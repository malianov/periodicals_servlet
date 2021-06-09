package hire.me.model.entity.periodical;

import hire.me.model.entity.language.Language;

public class PeriodicalType {
    private long id;
    private String type;

    public PeriodicalType() {
    }

    public PeriodicalType(long id, String type) {
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
