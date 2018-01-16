package pl.beng.thesis.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "administrator")
@JsonTypeName("administrator")
@PrimaryKeyJoinColumn(name = "administrator_id", referencedColumnName = "employee_id")
public class Administrator extends Employee {

    public Administrator(String name,
                         String surname,
                         String username,
                         String password,
                         String email,
                         String phone) {

        super(name, surname, username, password, email, phone);
    }

    public Administrator() {

    }
}