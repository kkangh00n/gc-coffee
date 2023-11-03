package com.example.gccoffee.model.order;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Order {
    private final String orderId;
    private final Email email;
    private String address;
    private String postcode;
    private final List<OrderItem> orderItems;
    private OrderStatus orderStatus;
    private final LocalDateTime createAt;
    private LocalDateTime updatedAt;

    public void setAddress(String address) {
        this.address = address;
        this.updatedAt = updatedAt;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
        this.updatedAt = updatedAt;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        this.updatedAt = updatedAt;
    }

}
