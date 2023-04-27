package com.dev.javadevproject.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ImageEntity implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] data;

    public Integer getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
