package pl.beng.thesis.model;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "developer")
@PrimaryKeyJoinColumn(name = "developer_id", referencedColumnName = "employee_id")
public class Developer extends Employee {

    public Developer(String name,
                     String surname,
                     String username,
                     String password,
                     String email,
                     String phone,
                     LocalDate creationDate) {

        super(name, surname, username, password, email, phone, creationDate);
    }

    public Developer() {
    }

    @OneToMany(mappedBy = "developer", cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "developer", cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(mappedBy = "developer", cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private Set<Education> educations = new HashSet<>();

    @OneToMany(mappedBy = "developer", cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private Set<Project> projects = new HashSet<>();


    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Set<Education> getEducations() {
        return educations;
    }

    public void setEducations(Set<Education> educations) {
        this.educations = educations;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
