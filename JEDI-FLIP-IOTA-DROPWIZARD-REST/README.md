# 🏋️ FlipFit REST API

FlipFit is a comprehensive gym management system migrated from a legacy console application to a modern, robust **Dropwizard-based REST API**. It follows a stateless architecture and provides distinct functionalities for Admins, Gym Owners, and Customers.

## 🚀 Quick Start

### 📋 Prerequisites
- **Java SDK**: Version 8, 11, or 17 (Java 17 recommended)
- **Apache Maven**: Version 3.6.0+
- **MySQL Server**: Version 8.0+

### 🛠️ Step 1: Database Setup
1. Start your MySQL server.
2. Create a database named `flipfit_schema`:
   ```sql
   CREATE DATABASE flipfit_schema;
   ```
3. Import the schema and initial data (Role table):
   ```bash
   mysql -u your_username -p flipfit_schema < flipfit_schema.sql
   mysql -u your_username -p flipfit_schema < init_data.sql
   ```

### 🔐 Step 2: Environment Configuration
Create a `.env` file in the project root directory and add your database credentials:

```ini
DB_URL=jdbc:mysql://localhost:3306/flipfit_schema
DB_USER=your_mysql_username
DB_PASS=your_mysql_password
```
*(Note: `.env` is excluded from version control via `.gitignore` for security)*

### 🏗️ Step 3: Build & Run
1.  **Clean and Install Dependencies**:
    ```bash
    mvn clean install -DskipTests
    ```
2.  **Start the REST Server**:
    ```bash
    java -jar target/flipfit_d-1.0-SNAPSHOT.jar server
    ```
    - **API Port**: `8080`
    - **Admin/Health Port**: `8081`

---

## 📖 Features & Roles

### 🛡️ Admin
- Approve/Reject Gym Owners and Gym Centers.
- View system-wide statistics and user management.
- Manage slot bookings across all centers.

### 🏋️ Gym Owner
- Register and Manage Gym Centers.
- Define available time slots.
- View bookings for their respective centers.

### 👤 Customer
- Browse approved gyms and search by city.
- Book available slots and manage personal profile.
- View booking history and waitlist status.

---

## 🧪 Documentation & Testing

- **API Reference**: Detailed endpoint documentation with JSON samples can be found in [API_DOCUMENTATION.md](API_DOCUMENTATION.md).
- **Javadoc**: Generate deep technical documentation for the codebase:
  ```bash
  mvn javadoc:javadoc
  ```
  *(Output located in `target/site/apidocs/index.html`)*

---

## 🏗️ Project Architecture
- **Framework**: Dropwizard (Stateless REST)
- **Service Layer**: Singleton-based Business Logic
- **Persistence**: Optimized JDBC with `Dotenv` integration
- **Security**: SHA-256 Password Hashing

---
*Developed by Team IOTA @ Flipkart JEDI*
