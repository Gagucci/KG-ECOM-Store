package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.OrderDao;
import org.yearup.models.Order;
import org.yearup.models.OrderLineItem;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlOrderDao extends MySqlDaoBase implements OrderDao {

    public MySqlOrderDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Order create(Order order) {

        // Query to inject a new order into the database
        String sql = "INSERT INTO orders (user_id, date, address, city, state, zip, shipping_amount) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (var connection = getConnection();
                var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                // Set the parameters for the prepared statement based on the order object
                ps.setInt(1, order.getUserId());
                ps.setObject(2, order.getDate() != null ? order.getDate() : LocalDateTime.now());
                ps.setString(3, order.getAddress());
                ps.setString(4, order.getCity());
                ps.setString(5, order.getState());
                ps.setString(6, order.getZip());
                ps.setBigDecimal(7, order.getShippingAmount());

                ps.executeUpdate();

                // Retrieve the generated keys (order ID) after insertion
                try (var rs = ps.getGeneratedKeys()) {
                    // If a key was generated, retrieve it
                    if (rs.next()) {
                        // Set the generated order ID back to the order object
                        order.setOrderId(rs.getInt(1));
                    }
                }

                return order;
            } catch (Exception e) {
                throw new RuntimeException("Error creating order", e);
            }
    }

    @Override
    public Order getById(int orderId) {

        // Query to retrieve an order by its ID
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (var connection = getConnection();
             var ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToOrder(rs);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving order with ID " + orderId, e);
        }
        return null;
    }

    @Override
    public List<Order> getByUserId(int userId) {

        // Query to retrieve all orders for a specific user
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        List<Order> orders = new ArrayList<>();

        try (var connection = getConnection();
             var ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapRowToOrder(rs));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving orders for user " + userId, e);
        }

        return orders;
    }

    @Override
    public void update(Order order) {

        // Query to update an existing order in the database
        String sql = "UPDATE orders SET " +
                "user_id = ?, " +
                "date = ?, " +
                "address = ?, " +
                "city = ?, " +
                "state = ?, " +
                "zip = ?, " +
                "shipping_amount = ? " +
                "WHERE order_id = ?";

        try (var connection = getConnection();
             var ps = connection.prepareStatement(sql)) {

            ps.setInt(1, order.getUserId());
            ps.setObject(2, order.getDate() != null ? order.getDate() : LocalDateTime.now());
            ps.setString(3, order.getAddress());
            ps.setString(4, order.getCity());
            ps.setString(5, order.getState());
            ps.setString(6, order.getZip());
            ps.setBigDecimal(7, order.getShippingAmount());
            ps.setInt(8, order.getOrderId());

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error updating order with ID " + order.getOrderId(), e);
        }
    }

    // method to map a ResultSet row to an Order object
    private Order mapRowToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setDate(rs.getObject("date", LocalDateTime.class));
        order.setAddress(rs.getString("address"));
        order.setCity(rs.getString("city"));
        order.setState(rs.getString("state"));
        order.setZip(rs.getString("zip"));
        order.setShippingAmount(rs.getBigDecimal("shipping_amount"));

        // Fetch associated line items
        order.setLineItems(getLineItemsForOrder(order.getOrderId()));

        return order;
    }

    private List<OrderLineItem> getLineItemsForOrder(int orderId) {

        // Query to retrieve line items for a specific order
        String sql = "SELECT * FROM order_line_items WHERE order_id = ?";
        List<OrderLineItem> lineItems = new ArrayList<>();

        try (var connection = getConnection();
             var ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Map each row to an OrderLineItem object
                    OrderLineItem item = new OrderLineItem();
                    item.setOrderLineItemId(rs.getInt("order_line_item_id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setSalesPrice(rs.getBigDecimal("sales_price"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setDiscount(rs.getBigDecimal("discount"));

                    lineItems.add(item);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching line items for order " + orderId, e);
        }

        return lineItems;
    }
}
