package pl.beng.thesis.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Embeddable
public class Address {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    @Size(min = 3, max = 50)
    private String city;

    @Column(nullable = false)
    @Size(min = 3, max = 50)
    private String street;

    @Column(nullable = false)
    @Min(1)
    @Max(300)
    private int number;

    public Address() {
    }

    public Address(String city, String street, int number) {
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(city)
                     .append(", ")
                     .append(street)
                     .append(" ")
                     .append(number);
        return stringBuilder.toString();
    }
}
