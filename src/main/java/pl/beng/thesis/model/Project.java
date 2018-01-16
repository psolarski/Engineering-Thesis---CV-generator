package pl.beng.thesis.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    @Size(min = 5, max = 100)
    private String description;

    @Column(nullable = false)
    @Size(min = 10, max = 100)
    private String position;

    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String city;

    @Column(nullable = false)
    @Size(min = 3, max = 80)
    private String company;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id")
    private Developer developer;

    @Version
    @Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;

    public Project() {
    }

    public Project(String description, String position,
                   String city, String company,
                   LocalDate startDate, LocalDate endDate) {

        this.description = description;
        this.position = position;
        this.city = city;
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }
}
