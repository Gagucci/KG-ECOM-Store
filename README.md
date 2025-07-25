<h1 align="center">🛍️ KG E-Commerce Store</h1>

<h3 align="center">
Enhanced E-Commerce Platform | Spring Boot Backend + Vanilla JS Frontend
</h3>

<p align="center">
A capstone project where we transformed an incomplete/buggy prebuilt system into a fully functional e-commerce solution. We fixed broken product search and filtering, then built a complete shopping cart system with persistent storage and checkout functionality. The enhanced platform now supports secure user authentication, real-time shopping cart management, and frontend-backend integration through REST APIs.<br><br>Working with the existing Java/Spring Boot backend, critical issues were debugged while adding essential e-commerce features. The revamped system delivers reliable product browsing, cart operations, and order processing.
</p>

<div align="center">
<h2>
Project Context
</h2>

<strong>This project began with a prebuilt but incomplete Spring Boot backend containing:</strong>

<p>
• A functional but buggy product search system<br>
• Partial user authentication flow<br>
• No shopping cart implementation<br>
• No order processing system<br>
• A separate vanilla JavaScript frontend requiring backend integration<br>
</p>

<strong>Students were tasked with:</strong>

<p>
• <strong>Debugging</strong> critical issues with the existing codebase<br>
• <strong>Implementing</strong> missing features per business requirements<br>
• <strong>Connecting</strong> everything to the prebuilt frontend<br>
</p>

</div>

<div align="center">
<h2>📸 Screenshots</h2>

<img src="./images/webpage.png" width="70%">
<img src="./images/cart.png" width="70%">
<img src="./images/profile.png" width="50%">
<img src="./images/postman.png" width="50%">

</div>

<div align="center">

## ✨ Key Features

**The enhanced system now includes:**

• **User Authentication** - Secure JWT login/registration flow  
• **Product Catalog** - Fixed search with category/price filters  
• **Shopping Cart** - Full CRUD operations with database persistence  
• **Order Processing** - Checkout system converting carts to orders  

**Critical technical improvements:**  

• Fixed broken product search functionality  
• Implemented missing cart/order APIs from scratch  
• Secured authentication vulnerabilities  
• Added proper input validation/error handling  
</div>


<div align="center">

## 🛠️ Tech Stack

### **Frontend:**  
[![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)](https://developer.mozilla.org/en-US/docs/Web/JavaScript)
[![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/HTML)
[![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/CSS)  
[![Axios](https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white)](https://github.com/axios/axios)
[![jQuery](https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white)](https://jquery.com)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)](https://getbootstrap.com)  
[![Mustache](https://img.shields.io/badge/Mustache-000000?style=for-the-badge&logo=mustache&logoColor=white)](https://github.com/janl/mustache.js)

### **Backend:**  
[![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)](https://java.com)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![JSON](https://img.shields.io/badge/JSON-000000?style=for-the-badge&logo=json&logoColor=white)](https://www.json.org)  
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://mysql.com)
[![JDBC](https://img.shields.io/badge/JDBC-007396?style=for-the-badge&logo=java&logoColor=white)](https://docs.oracle.com/javase/tutorial/jdbc/)
[![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)](https://jwt.io)

</div>


<div align="center">



## 🚀 Installation

### Prerequisites:

<p style="text-align: center; margin-left: 0; padding-left: 0">
• <strong>IntelliJ</strong> IDEA (Community or Ultimate Edition)<br>
• <strong>VSCode</strong> with Live Server extension<br>
• <strong>Java 24</strong> JDK installed<br>
• <strong>Maven</strong> 3.9+<br>
• <strong>MySQL</strong> 8.0+
</p>

### **1.**
<strong>Clone the repository</strong>:<br>
<code>git clone https://github.com/Gagucci/KG-ECOM-Store.git</code><br>
<code>cd KG-ECOM-Store</code>

### **2.**
<strong>Open "capstone-starter" in IntelliJ</strong>:<br>
<code>Run application using IDE tools or via CLI<br><br></code>

### **3.**
<strong>Open "capstone-client-web-application" in VSCode</strong>:<br>
<code>Launch with Live Server extension</code>


</div>


<h2 align="center">  
🔍 Interesting Code Snippet
</h2>

```java
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
```

<div align="center">
<h3>Checkout Endpoint</h3>
<p>This Java Spring Boot controller method handles the complete checkout process:</p>

<p>
• Converts shopping cart items to permanent orders<br>
• Applies user profile shipping details<br>
• Calculates shipping costs<br>
• Clears cart upon successful order creation<br>
• Returns complete order confirmation
</p>
</div>

<div align="center">
  
<h2>📜 License:</h2><br>

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

</div>
