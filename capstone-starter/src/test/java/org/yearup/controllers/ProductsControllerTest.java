package org.yearup.controllers;

import org.junit.jupiter.api.Test;
import org.yearup.data.ProductDao;
import org.yearup.models.Product;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductsControllerTest {

    @Test
    void searchByCat() {
        // This test should verify that the search method can filter products by category.
        // You would typically mock the ProductDao and verify that it is called with the correct parameters.
        // Example:
        // when(productDao.search(anyInt(), any(), any(), any())).thenReturn(expectedProducts);
        // List<Product> products = productsController.search(1, null, null, null);
        // assertEquals(expectedProducts, products);
        // Arrange
        ProductDao productDao = mock(ProductDao.class);
        ProductsController productsController = new ProductsController(productDao);
        List<Product> expectedProducts = List.of(new Product(1, "Product A", BigDecimal.valueOf(10.00), "Red", 1));
        when(productDao.search(1, null, null, null)).thenReturn(expectedProducts);

        // Act
        List<Product> products = productsController.search(1, null, null, null);

        // Assert
        assertEquals(expectedProducts, products);
        verify(productDao).search(1, null, null, null);
    }

    @Test
    void searchByMinPrice() {
    }

    @Test
    void searchByMaxPrice() {
    }

    @Test
    void searchByColor() {
    }


}