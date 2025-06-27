package org.yearup.data;

import org.yearup.models.Order;
import org.yearup.models.OrderLineItem;

import java.util.List;

public interface OrderDao {

    /**
     * Creates a new order in the database
     *
     * @param order the order to create
     * @return the created order with generated ID
     */
    Order create(Order order);

    /**
     * Gets an order by its ID
     *
     * @param orderId the ID of the order to find
     * @return the order or null if not found
     */
    Order getById(int orderId);

    /**
     * Gets all orders for a specific user
     *
     * @param userId the ID of the user
     * @return list of orders (empty list if none found)
     */
    List<Order> getByUserId(int userId);

    /**
     * Updates an existing order
     *
     * @param order the order with updated information
     */
    void update(Order order);
}

