package org.yearup.data.mysql;

import org.yearup.data.OrderDao;
import org.yearup.data.OrderLineItemDao;
import org.yearup.models.OrderLineItem;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlOrderLineItemDao extends MySqlDaoBase implements OrderLineItemDao {

    public MySqlOrderLineItemDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public OrderLineItem create(OrderLineItem item) {
        String sql = "INSERT INTO order_line_items (order_id, product_id, sales_price, quantity, discount) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (var connection = getConnection();
             var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getProductId());
            ps.setBigDecimal(3, item.getSalesPrice());
            ps.setInt(4, item.getQuantity());
            ps.setBigDecimal(5, item.getDiscount());

            ps.executeUpdate();

            try (var rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    item.setOrderLineItemId(rs.getInt(1));
                }
            }

            return item;
        } catch (Exception e) {
            throw new RuntimeException("Error creating order line item", e);
        }
    }

    @Override
    public List<OrderLineItem> getByOrderId(int orderId) {
        String sql = "SELECT * FROM order_line_items WHERE order_id = ?";
        try (var connection = getConnection();
             var ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            try (var rs = ps.executeQuery()) {
                List<OrderLineItem> items = new ArrayList<>();
                while (rs.next()) {
                    items.add(mapRowToOrderLineItem(rs));
                }
                return items;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving order line items for order " + orderId, e);
        }
    }


    @Override
    public void update(OrderLineItem item) {
        String sql = "UPDATE order_line_items SET product_id = ?, sales_price = ?, quantity = ?, discount = ? " +
                "WHERE order_line_item_id = ?";
        try (var connection = getConnection();
             var ps = connection.prepareStatement(sql)) {

            ps.setInt(1, item.getProductId());
            ps.setBigDecimal(2, item.getSalesPrice());
            ps.setInt(3, item.getQuantity());
            ps.setBigDecimal(4, item.getDiscount());
            ps.setInt(5, item.getOrderLineItemId());

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error updating order line item with ID " + item.getOrderLineItemId(), e);
        }
    }

    @Override
    public void delete(int orderLineItemId) {
        String sql = "DELETE FROM order_line_items WHERE order_line_item_id = ?";
        try (var connection = getConnection();
             var ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderLineItemId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting order line item with ID " + orderLineItemId, e);
        }
    }

    private OrderLineItem mapRowToOrderLineItem(ResultSet rs) throws SQLException {
        OrderLineItem item = new OrderLineItem();
        item.setOrderLineItemId(rs.getInt("order_line_item_id"));
        item.setOrderId(rs.getInt("order_id"));
        item.setProductId(rs.getInt("product_id"));
        item.setSalesPrice(rs.getBigDecimal("sales_price"));
        item.setQuantity(rs.getInt("quantity"));
        item.setDiscount(rs.getBigDecimal("discount"));
        return item;
    }
}
