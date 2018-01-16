package pl.beng.thesis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "employee")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Developer.class, name = "developer"),
        @JsonSubTypes.Type(value = Administrator.class, name = "administrator"),
        @JsonSubTypes.Type(value = HumanResource.class, name = "human-resource")
})

public abstract class Employee implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false, updatable = false)
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(min = 2, max = 40)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(min = 2, max = 40)
    private String surname;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String username;

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(min = 8, max = 64)
    private String password;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(min = 7, max = 9)
    private String phone;

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDate creationDate;

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean active;

    @Version
    @Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;

    @Embedded
    private Address address;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinTable(
            name = "employees_roles",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "employee_id",
                    nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "role_name", referencedColumnName = "name",
                    nullable = false, updatable = false)
    )
    private Set<Role> roles = new HashSet<>();

    public Employee() {
        this.creationDate = LocalDate.now();
        this.active = true;
    }

    public Employee(String name, String surname, String username,
                    String password, String email, String phone) {

        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.creationDate = LocalDate.now();
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles.isEmpty()) {
            return Collections.emptySet();
        }

        return roles.stream()
                .map(Role::getName)
                .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }
}
