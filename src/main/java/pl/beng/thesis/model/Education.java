package pl.beng.thesis.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class Education {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "education_id", nullable = false, updatable = false)
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(min = 2, max = 40)
    private String faculty;

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(min = 2, max = 40)
    private String specialization;

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

    public Education(String faculty, String specialization, LocalDate startDate, LocalDate endDate) {
        this.faculty = faculty;
        this.specialization = specialization;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Education() {
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
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

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Education)) {
            return false;
        }
        Education other = (Education) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

}
