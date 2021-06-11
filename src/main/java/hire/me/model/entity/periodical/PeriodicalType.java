package hire.me.model.entity.periodical;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeriodicalType that = (PeriodicalType) o;
        return id == that.id && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return "PeriodicalType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
