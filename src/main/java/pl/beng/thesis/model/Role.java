package pl.beng.thesis.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * Entity that represents a user role.
 */
@Entity
public class Role {

    @Id
    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Employee.class, mappedBy = "roles")
    private Set<Employee> employee = new HashSet<>();

    @Version
    @Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
