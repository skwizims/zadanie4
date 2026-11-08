package zadanie1.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meters")
public class Meter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "premises_id", nullable = false)
    private Premises premises;

    @Column(nullable = false) private String resourceType;
    @Column(unique = true, nullable = false) private String serialNumber;
    @Column(nullable = false) private Double tariff;
    private String status;

    @OneToMany(mappedBy = "meter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeterReading> readings = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Premises getPremises() { return premises; }
    public void setPremises(Premises premises) { this.premises = premises; }
    public String getResourceType() { return resourceType; }
    public void setResourceType(String resourceType) { this.resourceType = resourceType; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public Double getTariff() { return tariff; }
    public void setTariff(Double tariff) { this.tariff = tariff; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}