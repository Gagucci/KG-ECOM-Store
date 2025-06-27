package org.yearup.data;

import org.yearup.models.OrderLineItem;

import java.util.List;

public interface OrderLineItemDao {
    /**
     * Creates a new order line item in the database
     * @param item the line item to create
     * @return the created line item with generated ID
     */
    OrderLineItem create(OrderLineItem item);

    /**
     * Gets all line items for a specific order
     * @param orderId the ID of the order
     * @return list of line items (empty list if none found)
     */
    List<OrderLineItem> getByOrderId(int orderId);

    /**
     * Updates an existing order line item
     * @param item the line item with updated information
     */
    void update(OrderLineItem item);

    /**
     * Deletes an order line item
     * @param orderLineItemId the ID of the line item to delete
     */
    void delete(int orderLineItemId);
}
