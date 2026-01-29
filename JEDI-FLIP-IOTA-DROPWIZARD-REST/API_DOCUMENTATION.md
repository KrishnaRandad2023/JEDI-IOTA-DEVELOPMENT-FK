# 🚀 FlipFit EXHAUSTIVE API Documentation (Postman Ready)

This document contains **every single endpoint** in the FlipFit REST system, complete with inline **Sample JSON** for every request.

**Base URL**: `http://localhost:8080`

---

## 🔐 1. Authentication Endpoints (`/auth`)

### 🟢 Login
- **Method**: `POST`
- **URL**: `/auth/login`
- **Body (JSON)**:
```json
{
    "email": "admin@flipfit.com",
    "password": "admin123"
}
```

### 🟢 Register Customer
- **Method**: `POST`
- **URL**: `/auth/register/customer`
- **Body (JSON)**:
```json
{
    "name": "Alex Johnson",
    "email": "alex@test.com",
    "password": "password123",
    "mobileNumber": "9876543210",
    "aadhaarNumber": "123456789012"
}
```

### 🟢 Register Gym Owner
*Registers a pending request for Admin approval.*
- **Method**: `POST`
- **URL**: `/auth/register/owner`
- **Body (JSON)**:
```json
{
    "name": "Michael Smith",
    "email": "smith@gym.com",
    "password": "password123",
    "mobileNumber": "9988776655",
    "city": "Bangalore",
    "panNumber": "ABCDE1234F",
    "gstNumber": "22AAAAA0000A1Z5",
    "cin": "L12345KA2023PLC123456",
    "aadhaarNumber": "112233445566"
}
```

### 🟢 Change Password (Public)
- **Method**: `POST`
- **URL**: `/auth/change-password`
- **Query Params**:
  - `email`: `admin@flipfit.com`
  - `oldPassword`: `admin123`
  - `newPassword`: `newAdmin123`

---

## 🛡️ 2. Administrator Endpoints (`/admin`)

| Action | Method | URL | Body / Params |
| :--- | :--- | :--- | :--- |
| **Pending Owners** | `GET` | `/admin/pending-owners` | N/A |
| **Pending Gyms** | `GET` | `/admin/pending-centers` | N/A |
| **Approve Owner** | `PUT` | `/admin/approve-owner/1` | **Path**: `regId` |
| **Reject Owner** | `PUT` | `/admin/reject-owner/1` | **Path**: `regId` |
| **Approve Gym** | `PUT` | `/admin/approve-center/1` | **Path**: `centerId` |
| **Reject Gym** | `PUT` | `/admin/reject-center/1` | **Path**: `centerId` |
| **All Bookings** | `GET` | `/admin/bookings` | N/A |
| **All Users** | `GET` | `/admin/users` | N/A |
| **System Stats** | `GET` | `/admin/statistics` | N/A |
| **Activate User** | `PUT` | `/admin/users/5/activate` | **Path**: `userId` |
| **Deactivate User** | `PUT` | `/admin/users/5/deactivate`| **Path**: `userId` |
| **Delete User** | `DELETE`| `/admin/users/5` | **Path**: `userId` |
| **Filter by Role**| `GET` | `/admin/users/role/CUSTOMER`| **Path**: `role` |

---

## 👤 3. Gym Customer Endpoints (`/customer`)
*Requires Header: `userId` (the ID of the logged-in customer)*

### 🔵 View Available Gyms
- **Method**: `GET`
- **URL**: `/customer/gyms`
- **Query Params**: `city=Bangalore` (Optional)

### 🔵 Search Gyms by Name
- **Method**: `GET`
- **URL**: `/customer/gyms/search`
- **Query Params**: `name=Power`

### 🔵 Get Specific Gym Details
- **Method**: `GET`
- **URL**: `/customer/gyms/1`
- **Path Param**: `centerId`

### 🔵 View Gym Slots
- **Method**: `GET`
- **URL**: `/customer/gyms/1/slots`
- **Path Param**: `centerId`

### 🔵 Book a Slot
- **Method**: `POST`
- **URL**: `/customer/bookings`
- **Header**: `userId: 5`
- **Query Param**: `slotId=1`

### 🔵 My Bookings (All)
- **Method**: `GET`
- **URL**: `/customer/bookings`
- **Header**: `userId: 5`

### 🔵 My Active Bookings
- **Method**: `GET`
- **URL**: `/customer/bookings/active`
- **Header**: `userId: 5`

### 🔵 Cancel Booking
- **Method**: `DELETE`
- **URL**: `/customer/bookings/1`
- **Header**: `userId: 5`, **Path**: `bookingId`

### 🔵 My Waitlist Status
- **Method**: `GET`
- **URL**: `/customer/waitlist`
- **Header**: `userId: 5`

### 🔵 My Booking Stats
- **Method**: `GET`
- **URL**: `/customer/statistics`
- **Header**: `userId: 5`

### 🔵 Update My Profile
- **Method**: `PUT`
- **URL**: `/customer/profile`
- **Header**: `userId: 5`
- **Body (JSON)**:
```json
{
    "name": "Alex Updated",
    "email": "alex@test.com",
    "mobileNumber": "9876543210",
    "aadhaarNumber": "123456789012"
}
```

### 🔵 Change My Password
- **Method**: `POST`
- **URL**: `/customer/change-password`
- **Header**: `userId: 5`
- **Query Params**: `oldPassword=xxx`, `newPassword=yyy`

---

## 🏋️ 4. Gym Owner Endpoints (`/owner`)
*Requires Header: `userId` (the ID of the logged-in owner)*

### 🟡 Add Gym Center
- **Method**: `POST`
- **URL**: `/owner/centers`
- **Header**: `userId: 2`
- **Body (JSON)**:
```json
{
    "centerName": "Power Fit HSR",
    "address": "Sector 7, Bangalore",
    "city": "Bangalore",
    "capacity": 40
}
```

### 🟡 Update Gym Center
- **Method**: `PUT`
- **URL**: `/owner/centers/1`
- **Header**: `userId: 2`, **Path**: `centerId`
- **Body (JSON)**:
```json
{
    "centerName": "Power Fit HSR (Updated)",
    "address": "Sector 7, Bangalore",
    "city": "Bangalore",
    "capacity": 50
}
```

### 🟡 Delete Gym Center
- **Method**: `DELETE`
- **URL**: `/owner/centers/1`
- **Header**: `userId: 2`, **Path**: `centerId`

### 🟡 View My Centers (All)
- **Method**: `GET`
- **URL**: `/owner/centers`
- **Header**: `userId: 2`

### 🟡 View My Approved Centers
- **Method**: `GET` | `/owner/centers/approved` | `userId Header`

### 🟡 View My Pending Centers
- **Method**: `GET` | `/owner/centers/pending` | `userId Header`

### 🟡 Add a Time Slot
- **Method**: `POST`
- **URL**: `/owner/slots`
- **Header**: `userId: 2`
- **Query Params**:
  - `centerId`: `1`
  - `startTime`: `06:00`
  - `endTime`: `07:30`
  - `totalSeats`: `20`

### 🟡 View My Slots
- **Method**: `GET` | `/owner/slots` | `userId Header`

### 🟡 Delete a Slot
- **Method**: `DELETE` | `/owner/slots/1` | `userId Header`, `Path: slotId`

### 🟡 View Gym Bookings
- **Method**: `GET`
- **URL**: `/owner/bookings`
- **Header**: `userId: 2`
- **Query Param**: `centerId=1` (Optional)

### 🟡 My Owner Stats
- **Method**: `GET` | `/owner/statistics` | `userId Header`

---

## 🛠️ Testing Tip (Postman)
1. Set the **Body** to `raw` and select `JSON` from the dropdown. 
2. For Headers, go to the **Headers** tab and add a new key `userId` with the value (e.g., `5`).
3. For query params, you can type them directly in the URL (e.g., `?city=Bangalore`) or use the **Params** tab.
