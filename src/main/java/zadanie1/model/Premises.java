package zadanie1.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "premises")
public class Premises {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String address;
    @Column(nullable = false) private Double area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "premises", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meter> meters = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Double getArea() { return area; }
    public void setArea(Double area) { this.area = area; }
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
}