package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);

    void addToCart(int userId, int productId, int quantity);

    void updateCart(int userId, int productId, int quantity);

    void emptyCart(int userId);
}
