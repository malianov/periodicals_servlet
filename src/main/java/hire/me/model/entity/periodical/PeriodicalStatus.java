package hire.me.model.entity.periodical;

import java.util.Objects;

public class PeriodicalStatus {
    private Long id;
    private String status;

    public PeriodicalStatus(Long id, String status) {
        this.status = status;
    }

    public PeriodicalStatus() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeriodicalStatus that = (PeriodicalStatus) o;
        return Objects.equals(id, that.id) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }

    @Override
    public String toString() {
        return "PeriodicalStatus{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
