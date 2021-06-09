package hire.me.model.entity.periodical;

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
}
