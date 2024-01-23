package com.cbt.orderservicejan24;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "orderstatuses")
public class Orderstatus {
    @Id
    @Column(name = "id", nullable = false, length = 10)
    private String id;

    @Column(name = "orderid", nullable = false, length = 10)
    private String orderid;

    @Column(name = "status", length = 20)
    private String status;

}