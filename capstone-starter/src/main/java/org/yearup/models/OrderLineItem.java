package org.yearup.models;

import java.math.BigDecimal;

public class OrderLineItem {
    private int orderLineItemId;
    private int orderId;
    private int productId;
    private BigDecimal salesPrice;
    private int quantity;
    private BigDecimal discount;

    public OrderLineItem(int orderLineItemId, int orderId, int productId, BigDecimal salesPrice, int quantity, BigDecimal discount) {
        this.orderLineItemId = orderLineItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.salesPrice = salesPrice;
        this.quantity = quantity;
        this.discount = discount;
    }

    public OrderLineItem() {
    }

    public int getOrderLineItemId() { return orderLineItemId; }
    public void setOrderLineItemId(int orderLineItemId) { this.orderLineItemId = orderLineItemId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public BigDecimal getSalesPrice() { return salesPrice; }
    public void setSalesPrice(BigDecimal salesPrice) { this.salesPrice = salesPrice; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }

    /* Calculates the total price for this line item, taking into account the sales price,
     * quantity, and any applicable discount.
     * @return The total price for this line item.
     */
    public BigDecimal getLineTotal() {
        BigDecimal itemTotal = salesPrice.multiply(BigDecimal.valueOf(quantity));
        return itemTotal.subtract(discount != null ? discount : BigDecimal.ZERO);
    }
}
