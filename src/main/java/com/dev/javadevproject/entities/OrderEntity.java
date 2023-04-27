package com.dev.javadevproject.entities;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer Id;

    public Integer userId;

    @OneToOne
    public AddressEntity toAddress;

    @ManyToMany(fetch = FetchType.EAGER)
    public List<ProductEntity> goods;

    public Integer getCost(){
        Integer sum = 0;
        for(ProductEntity item : goods) {
            sum += item.getPrice();
        }
        return sum;
    }

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    public Date creationDateTime;

    public List<ProductEntity> getGoods() {
        return goods;
    }

    public void setGoods(List<ProductEntity> goods) {
        this.goods = goods;
    }

    public AddressEntity getToAddress() {
        return toAddress;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}

