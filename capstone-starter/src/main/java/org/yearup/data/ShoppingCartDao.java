package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    /* Retrieves the shopping cart for a specific user.
     * @param userId the ID of the user
     * @return the shopping cart of the user
     */
    ShoppingCart getByUserId(int userId);

    /* Adds a product to the user's shopping cart.
     * @param userId the ID of the user
     * @param productId the ID of the product to add
     * @param quantity the quantity of the product to add
     */
    void addToCart(int userId, int productId, int quantity);

    /* Updates the quantity of a product in the user's shopping cart.
     * @param userId the ID of the user
     * @param productId the ID of the product to update
     * @param quantity the new quantity of the product
     */
    void updateCart(int userId, int productId, int quantity);

    /* Clears the shopping cart for a specific user.
     *
     * @param userId the ID of the user
     */
    void clearCart(int userId);
}
