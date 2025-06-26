# 🛍️ KG E-Commerce Store

A full-stack e-commerce platform with Spring Boot backend and React frontend, featuring product management, user authentication, and secure checkout.

![App Screenshot](https://i.imgur.com/J5ZQ2rT.png)

## ✨ Features

- **Product Catalog** with categories and advanced search
- **JWT Authentication** (Customer/Admin roles)
- **Shopping Cart** with persistent storage
- **Admin Dashboard** for inventory management
- **Responsive Design** with mobile-first approach
- **RESTful API** with proper status codes

## 🛠️ Tech Stack

**Frontend:**  
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)  
![TailwindCSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)  
![Axios](https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white)

**Backend:**  
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)  
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)  
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Node.js 16+
- MySQL 8+
- Maven

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Gagucci/KG-ECOM-Store.git
   cd KG-ECOM-Store
Set up database:

bash
mysql -u root -p < database/schema.sql
Configure backend:

bash
cd backend
cp src/main/resources/application.example.properties src/main/resources/application.properties
# Edit the properties file with your DB credentials
Run backend:

bash
./mvnw spring-boot:run
Run frontend:

bash
cd ../frontend
npm install
npm start
🔍 Code Spotlight: Secure Product Update
java
@RestController
@RequestMapping("/products")
public class ProductsController {
    private final ProductDao productDao;
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateProduct(
            @PathVariable int id,
            @Valid @RequestBody Product product) {
        
        Product existing = productDao.getById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        product.setProductId(id);
        log.info("Admin updating product ID {}: {}", id, product);
        Product updated = productDao.update(id, product);
        return ResponseEntity.ok(updated);
    }
}
Security Features:

🔒 Role-based authorization

🛡️ Input validation

🔄 ID consistency

📝 Audit logging

📸 Application Screens
Home Page	Product Details	Admin Dashboard
https://i.imgur.com/J5ZQ2rT.png	https://i.imgur.com/8KQ1Y2x.png	https://i.imgur.com/L9p3nZl.png
🌐 API Endpoints
Method	Endpoint	Description	Auth Required
GET	/products	List all products	No
POST	/auth/login	User login	No
PUT	/products/{id}	Update product	Admin
GET	/categories/{id}/products	Products by category	No
🤝 Contributing
Fork the project

Create your feature branch (git checkout -b feature/AmazingFeature)

Commit your changes (git commit -m 'Add some amazing feature')

Push to the branch (git push origin feature/AmazingFeature)

Open a Pull Request

📜 License
Distributed under the MIT License.

📧 Contact
Project Link: https://github.com/Gagucci/KG-ECOM-Store
