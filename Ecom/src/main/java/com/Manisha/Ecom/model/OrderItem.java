package com.Manisha.Ecom.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Builder
public class OrderItem {
    @Id
    private int Id;
    @ManyToOne
    private Product product;
    private int quantity;
    private BigDecimal totalprice;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    public OrderItem(int Id, Product product, int quantity, BigDecimal totalprice, Order order) {
        this.Id = Id;
        this.product = product;
        this.quantity = quantity;
        this.totalprice = totalprice;
        this.order = order;
    }
}
