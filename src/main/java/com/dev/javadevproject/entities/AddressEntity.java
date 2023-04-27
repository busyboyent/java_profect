package com.dev.javadevproject.entities;

import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
public class AddressEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    public String country;
    public String city;
    public String locationAddress;
    public Integer postalCode;

    public String getCountry() { return country; }
    public String getCity() { return city; }
    public String getLocationAddress() { return locationAddress; }
    public Integer getPostalCode() { return postalCode; }

    public Long getId() {
        return Id;
    }
}