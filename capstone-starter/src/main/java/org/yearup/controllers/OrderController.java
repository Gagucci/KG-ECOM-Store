package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.*;
import org.yearup.models.*;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class OrderController {

    private final OrderDao orderDao;
    private final OrderLineItemDao orderLineItemDao;
    private final ShoppingCartDao shoppingCartDao;
    private final UserDao userDao;
    private final ProfileDao profileDao;
    private final ProductDao productDao;

    @Autowired
    public OrderController(OrderDao orderDao, OrderLineItemDao orderLineItemDao, ShoppingCartDao shoppingCartDao, UserDao userDao, ProfileDao profileDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.orderLineItemDao = orderLineItemDao;
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.profileDao = profileDao;
        this.productDao = productDao;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order checkout(Principal principal) {
        try {
            // 1. Get user and profile
            User user = userDao.getByUserName(principal.getName());
            Profile profile = profileDao.getByUserId(user.getId());

            // 2. Get shopping cart
            ShoppingCart cart = shoppingCartDao.getByUserId(user.getId());
            if (cart == null || cart.getItems().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shopping cart is empty");
            }

            // 3. Create order from cart and profile
            Order order = new Order();
            order.setUserId(user.getId());
            order.setDate(LocalDateTime.now());
            order.setAddress(profile.getAddress());
            order.setCity(profile.getCity());
            order.setState(profile.getState());
            order.setZip(profile.getZip());
            order.setShippingAmount(calculateShipping(cart));

            // 4. Save order to get generated ID
            Order createdOrder = orderDao.create(order);

            // 5. Convert cart items to order line items
            for (ShoppingCartItem cartItem : cart.getItems().values()) {
                Product product = productDao.getById(cartItem.getProductId());

                OrderLineItem lineItem = new OrderLineItem();
                lineItem.setOrderId(createdOrder.getOrderId());
                lineItem.setProductId(product.getProductId());
                lineItem.setQuantity(cartItem.getQuantity());
                lineItem.setSalesPrice(product.getPrice());
                lineItem.setDiscount(BigDecimal.ZERO);

                orderLineItemDao.create(lineItem);
            }

            // 6. Clear shopping cart
            shoppingCartDao.clearCart(user.getId());

            // 7. Return complete order with line items
            return orderDao.getById(createdOrder.getOrderId());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error during checkout: " + e.getMessage(), e);
        }
    }


    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable int orderId, Principal principal) {
        try {
            // Get the order by ID
            Order order = orderDao.getById(orderId);
            if (order == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
            }

            // Check if the user is authorized to view this order
            User user = userDao.getByUserName(principal.getName());
            if (order.getUserId() != user.getId()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to view this order");
            }

            return order;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving order", e);
        }
    }


    @GetMapping
    public List<Order> getUserOrders(Principal principal) {
        try {
            // Get current user's ID
            User user = userDao.getByUserName(principal.getName());
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
            }

            // Retrieve orders for the user
            List<Order> orders = orderDao.getByUserId(user.getId());
            if (orders.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No orders found for this user");
            }

            return orders;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving orders", e);
        }
    }

    private BigDecimal calculateShipping(ShoppingCart cart) {
        // Simple shipping calculation logic
        BigDecimal total = cart.getTotal();
        if (total.compareTo(new BigDecimal("250.00")) < 0) {
            return new BigDecimal("5.99"); // Flat rate for orders under $250
        } else if (total.compareTo(new BigDecimal("500.00")) < 0) {
            return new BigDecimal("2.99"); // Reduced rate for orders between $250 and $500
        } else {
            return BigDecimal.ZERO; // Free shipping for orders over $500
        }
    }

}
