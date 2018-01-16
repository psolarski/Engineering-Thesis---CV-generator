package pl.beng.thesis.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "human_resource")
@JsonTypeName("human-resource")
@PrimaryKeyJoinColumn(name = "human_resource_id", referencedColumnName = "employee_id")
public class HumanResource extends Employee {

    public HumanResource(String name,
                         String surname,
                         String username,
                         String password,
                         String email,
                         String phone) {

        super(name, surname, username, password, email, phone);
    }

    public HumanResource() {

    }
}
