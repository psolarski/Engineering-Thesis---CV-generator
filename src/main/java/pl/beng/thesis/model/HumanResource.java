package pl.beng.thesis.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "human_resource")
@PrimaryKeyJoinColumn(name = "human_resource_id", referencedColumnName = "employee_id")
public class HumanResource extends Employee {

    public HumanResource(String name, String surname, String password, String email, LocalDate creationDate) {
        super(name, surname, password, email, creationDate);
    }

    public HumanResource() {

    }
}
