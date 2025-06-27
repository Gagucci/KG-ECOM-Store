package org.yearup.data.mysql;

import org.yearup.data.OrderDao;
import org.yearup.data.OrderLineItemDao;
import org.yearup.models.OrderLineItem;

import javax.sql.DataSource;
import java.util.List;

public class MySqlOrderLineItemDao extends MySqlDaoBase implements OrderLineItemDao {

    public MySqlOrderLineItemDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public OrderLineItem create(OrderLineItem item) {
        return null;
    }

    @Override
    public List<OrderLineItem> getByOrderId(int orderId) {
        return List.of();
    }

    @Override
    public void update(OrderLineItem item) {

    }

    @Override
    public void delete(int orderLineItemId) {

    }
}
