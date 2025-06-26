<h1 align="center">🛍️ KG E-Commerce Store</h1>

<p align="center">A full-stack e-commerce platform with Spring Boot backend and React frontend,<br>featuring product management, user authentication, and secure checkout.</p>

<div align="center">

## ✨ Features

<p style="text-align: center; margin-left: 0; padding-left: 0">
• <strong>Product Catalog</strong> with categories and advanced search<br>
• <strong>JWT Authentication</strong> (Customer/Admin roles)<br>
• <strong>Shopping Cart</strong> with persistent storage<br>
• <strong>Admin Dashboard</strong> for inventory management<br>
• <strong>Responsive Design</strong> with mobile-first approach<br>
• <strong>RESTful API</strong> with proper status codes
</p>

</div>

<div align="center">

## 🛠️ Tech Stack

### **Frontend**  
[![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)](https://developer.mozilla.org/en-US/docs/Web/JavaScript)
[![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/HTML)
[![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/CSS)  
[![Axios](https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white)](https://github.com/axios/axios)
[![jQuery](https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white)](https://jquery.com)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)](https://getbootstrap.com)  
[![Mustache](https://img.shields.io/badge/Mustache-000000?style=for-the-badge&logo=mustache&logoColor=white)](https://github.com/janl/mustache.js)

### **Backend**  
[![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)](https://java.com)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![JSON](https://img.shields.io/badge/JSON-000000?style=for-the-badge&logo=json&logoColor=white)](https://www.json.org)  
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://mysql.com)
[![JDBC](https://img.shields.io/badge/JDBC-007396?style=for-the-badge&logo=java&logoColor=white)](https://docs.oracle.com/javase/tutorial/jdbc/)
[![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)](https://jwt.io)

</div>

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
```
2. **Open "capstone-starter" in Intellij**:
```bash
run application with CLI in IDE
```
3. **Open "capstone-client-web-application" in VSCode**:
```bash
open and view webpage in browser using live server
```
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
