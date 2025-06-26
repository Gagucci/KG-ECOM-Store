package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.Product;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.HashMap;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        ShoppingCart cart = new ShoppingCart();
        cart.setItems(new HashMap<>());

        String sql = """
            SELECT
                sc.product_id,
                sc.quantity,
                p.product_id as productId,
                p.name,
                p.price,
                p.category_id as categoryId,
                p.description,
                p.color,
                p.stock,
                p.image_url as imageUrl,
                p.featured
            FROM
                shopping_cart sc
            JOIN
                products p ON sc.product_id = p.product_id
            WHERE
                sc.user_id = ?
            """;

        try (var connection = getConnection();
             var ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            var rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("productId"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setCategoryId(rs.getInt("categoryId"));
                product.setDescription(rs.getString("description"));
                product.setColor(rs.getString("color"));
                product.setStock(rs.getInt("stock"));
                product.setImageUrl(rs.getString("imageUrl"));
                product.setFeatured(rs.getBoolean("featured"));

                ShoppingCartItem item = new ShoppingCartItem();
                item.setProduct(product);
                item.setQuantity(rs.getInt("quantity"));

                cart.add(item);
            }
            return cart;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving shopping cart", e);
        }
    }

    @Override
    public void addToCart(int userId, int productId, int quantity) {
        String sql = """
                MERGE INTO shopping_cart AS target
                    USING (SELECT CAST(? AS INT) AS user_id,
                                  CAST(? AS INT) AS product_id,
                                  CAST(? AS INT) AS quantity) AS source
                    ON target.user_id = source.user_id AND target.product_id = source.product_id
                    WHEN MATCHED THEN
                        UPDATE SET quantity = target.quantity + source.quantity
                    WHEN NOT MATCHED THEN
                        INSERT (user_id, product_id, quantity)
                        VALUES (source.user_id, source.product_id, source.quantity);
                """;

        try (var connection = getConnection();
             var ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding product to cart for user ID: " + userId, e);
        }
    }

    @Override
    public void updateCart(int userId, int productId, int quantity) {
        String sql = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? AND product_id = ?";

        try (var connection = getConnection();
             var ps = connection.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, userId);
            ps.setInt(3, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating cart for user ID: " + userId, e);
        }
    }

    @Override
    public void emptyCart(int userId) {
        String sql = "DELETE FROM shopping_cart WHERE user_id = ?";

        try (var connection = getConnection();
             var ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error emptying cart for user ID: " + userId, e);
        }
    }
}