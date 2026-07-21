package com.cognizant.orderservice.dto;

import com.cognizant.orderservice.entity.Order;

public class OrderResponseDTO {
    private Order order;
    private UserDTO user;

    public OrderResponseDTO() {
    }

    public OrderResponseDTO(Order order, UserDTO user) {
        this.order = order;
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
