package org.yearup.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private LocalDateTime date;
    private String address;
    private String city;
    private String state;
    private String zip;
    private BigDecimal shippingAmount;
    private List<OrderLineItem> lineItems = new ArrayList<>();

    public Order(int orderId, int userId, LocalDateTime date, String address, String city, String state, String zip, BigDecimal shippingAmount, List<OrderLineItem> lineItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.date = date;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.shippingAmount = shippingAmount;
        this.lineItems = lineItems;
    }

    public Order() {
        // Default constructor
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public BigDecimal getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(BigDecimal shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public List<OrderLineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<OrderLineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public void addLineItem(OrderLineItem lineItem) {
        this.lineItems.add(lineItem);
    }

    // Calculates the total amount for the order, including line items and shipping
    public BigDecimal calculateTotal() {
        BigDecimal subtotal = lineItems.stream()
                .map(OrderLineItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return subtotal.add(shippingAmount != null ? shippingAmount : BigDecimal.ZERO);
    }
}
