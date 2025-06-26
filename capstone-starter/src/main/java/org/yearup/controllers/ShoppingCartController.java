package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;


@RestController // convert this class to a REST controller
@RequestMapping("/cart") // add the @RequestMapping annotation to set the base URL for this controller
@CrossOrigin // add the @CrossOrigin annotation to allow cross-origin requests
@PreAuthorize("isAuthenticated()") // add the @PreAuthorize annotation to restrict access to logged in users
public class ShoppingCartController
{
    // a shopping cart requires
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao)
    {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }


    // each method in this controller requires a Principal object as a parameter
    @GetMapping("")
    public ShoppingCart getCart(Principal principal)
    {
        try
        {
            // get the currently logged in username
            String userName = principal.getName();
            User currentUser = userDao.getByUserName(userName);
            return shoppingCartDao.getByUserId(currentUser.getId());
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added
    @PostMapping("/products/{productId}")
    public ShoppingCart addProductToCart(@PathVariable int productId, Principal principal) {
        try
        {
            String userName = principal.getName();
            User currentUser = userDao.getByUserName(userName);
            shoppingCartDao.addToCart(currentUser.getId(), productId, 1);
            return shoppingCartDao.getByUserId(currentUser.getId());
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated
    @PutMapping("/products/{productId}")
    public ShoppingCart updateProductInCart(@PathVariable int productId, @RequestBody ShoppingCartItem item, Principal principal) {
        try
        {
            String userName = principal.getName();
            User currentUser = userDao.getByUserName(userName);
            shoppingCartDao.updateCart(currentUser.getId(), productId, item.getQuantity());
            return shoppingCartDao.getByUserId(currentUser.getId());
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart
    @DeleteMapping("")
    public void emptyCart(Principal principal) {
        try
        {
            String userName = principal.getName();
            User currentUser = userDao.getByUserName(userName);
            shoppingCartDao.emptyCart(currentUser.getId());
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

}
