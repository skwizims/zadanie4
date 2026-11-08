package zadanie1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "utility_charges")
public class UtilityCharge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "premises_id", nullable = false)
    private Premises premises;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_id")
    private Meter meter;

    @Column(nullable = false) private String period;
    @Column(nullable = false) private Double volume;
    @Column(nullable = false) private Double amount;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Premises getPremises() { return premises; }
    public void setPremises(Premises premises) { this.premises = premises; }
    public Meter getMeter() { return meter; }
    public void setMeter(Meter meter) { this.meter = meter; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
    public Double getVolume() { return volume; }
    public void setVolume(Double volume) { this.volume = volume; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}