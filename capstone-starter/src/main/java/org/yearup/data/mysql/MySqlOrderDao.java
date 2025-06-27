package org.yearup.data.mysql;

import org.yearup.data.OrderDao;
import org.yearup.models.Order;
import org.yearup.models.OrderLineItem;

import javax.sql.DataSource;
import java.util.List;

public class MySqlOrderDao extends MySqlDaoBase implements OrderDao {

    public MySqlOrderDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Order create(Order order) {
        String sql = "INSERT INTO orders (user_id, date, address, city, state, zip, shipping_amount) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (var connection = getConnection();
                var ps = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, order.getUserId());
                ps.setObject(2, order.getDate());
                ps.setString(3, order.getAddress());
                ps.setString(4, order.getCity());
                ps.setString(5, order.getState());
                ps.setString(6, order.getZip());
                ps.setBigDecimal(7, order.getShippingAmount());

                ps.executeUpdate();

                try (var rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
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
        return null;
    }

    @Override
    public List<Order> getByUserId(int userId) {
        return List.of();
    }

    @Override
    public void update(Order order) {

    }
}
