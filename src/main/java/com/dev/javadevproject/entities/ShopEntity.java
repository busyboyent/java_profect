package com.dev.javadevproject.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ShopEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @OneToOne(fetch=FetchType.EAGER, orphanRemoval = true)
    private AddressEntity address;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }
}
