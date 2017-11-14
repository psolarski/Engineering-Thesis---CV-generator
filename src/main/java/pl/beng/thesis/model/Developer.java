package pl.beng.thesis.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "developer")
@JsonTypeName("developer")
@PrimaryKeyJoinColumn(name = "developer_id", referencedColumnName = "employee_id")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@NamedEntityGraph(name = "graph.developer",
    attributeNodes = @NamedAttributeNode("notifications"))
public class Developer extends Employee {

    public Developer(String name,
                     String surname,
                     String username,
                     String password,
                     String email,
                     String phone,
                     LocalDate creationDate) {

        super(name, surname, username, password, email, phone, creationDate);
        dataLastModificationDate = LocalDate.now();
    }

    public Developer() {
    }


    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDate dataLastModificationDate;

    private LocalDate lastNotificationDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    private Set<Education> educations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
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

    public LocalDate getDataLastModificationDate() {
        return dataLastModificationDate;
    }

    public void setDataLastModificationDate(LocalDate dataLastModificationDate) {
        this.dataLastModificationDate = dataLastModificationDate;
    }

    public LocalDate getLastNotificationDate() {
        return lastNotificationDate;
    }

    public void setLastNotificationDate(LocalDate lastNotificationDate) {
        this.lastNotificationDate = lastNotificationDate;
    }
}
