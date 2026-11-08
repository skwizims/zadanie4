package zadanie1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "meter_readings")
public class MeterReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_id", nullable = false)
    private Meter meter;

    @Column(nullable = false) private String period;
    @Column(nullable = false) private Double value;
    private String verificationStatus;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Meter getMeter() { return meter; }
    public void setMeter(Meter meter) { this.meter = meter; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
    public String getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(String verificationStatus) { this.verificationStatus = verificationStatus; }
}