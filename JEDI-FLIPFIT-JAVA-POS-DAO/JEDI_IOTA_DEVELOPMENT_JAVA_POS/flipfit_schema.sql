CREATE DATABASE IF NOT EXISTS flipfit_schema;
USE flipfit_schema;

-- 1. Role Table
CREATE TABLE IF NOT EXISTS Role (
    roleId INT AUTO_INCREMENT PRIMARY KEY,
    roleName VARCHAR(50) NOT NULL,
    roleDescription VARCHAR(255)
);

-- 2. User Table
CREATE TABLE IF NOT EXISTS User (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    mobileNumber VARCHAR(20),
    roleId INT,
    FOREIGN KEY (roleId) REFERENCES Role(roleId)
);

-- 3. GymOwner Table
CREATE TABLE IF NOT EXISTS GymOwner (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    gstNumber VARCHAR(50),
    cin VARCHAR(50),
    panNumber VARCHAR(50),
    FOREIGN KEY (userId) REFERENCES User(userId) ON DELETE CASCADE
);

-- 4. GymCenter Table
CREATE TABLE IF NOT EXISTS GymCenter (
    centerId INT AUTO_INCREMENT PRIMARY KEY,
    ownerId INT,
    centerName VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    city VARCHAR(50),
    capacity INT,
    isApproved BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (ownerId) REFERENCES User(userId) ON DELETE CASCADE
);

-- 5. Slot Table
CREATE TABLE IF NOT EXISTS Slot (
    slotId INT AUTO_INCREMENT PRIMARY KEY,
    centerId INT,
    startTime TIME NOT NULL,
    endTime TIME NOT NULL,
    totalSeats INT DEFAULT 10,
    availableSeats INT DEFAULT 10,
    FOREIGN KEY (centerId) REFERENCES GymCenter(centerId) ON DELETE CASCADE
);

-- 6. GymCustomer Table
CREATE TABLE IF NOT EXISTS GymCustomer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    FOREIGN KEY (userId) REFERENCES User(userId) ON DELETE CASCADE
);

-- 7. Booking Table
CREATE TABLE IF NOT EXISTS Booking (
    bookingId INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    slotId INT,
    bookingDate DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'CONFIRMED', -- CONFIRMED, CANCELLED, WAITLISTED
    FOREIGN KEY (userId) REFERENCES User(userId) ON DELETE CASCADE,
    FOREIGN KEY (slotId) REFERENCES Slot(slotId) ON DELETE CASCADE
);

-- 8. Payment Table
CREATE TABLE IF NOT EXISTS Payment (
    paymentId INT AUTO_INCREMENT PRIMARY KEY,
    bookingId INT,
    amount DOUBLE NOT NULL,
    transactionId VARCHAR(50),
    FOREIGN KEY (bookingId) REFERENCES Booking(bookingId) ON DELETE CASCADE
);

-- 9. Notification Table
CREATE TABLE IF NOT EXISTS Notification (
    notificationId INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    message TEXT,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    isRead BOOLEAN DEFAULT FALSE,
    notificationType VARCHAR(50),
    FOREIGN KEY (userId) REFERENCES User(userId) ON DELETE CASCADE
);

-- 10. Registration Table
CREATE TABLE IF NOT EXISTS Registration (
    registrationId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    mobileNumber VARCHAR(20),
    roleType VARCHAR(20),
    registrationDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    isApproved BOOLEAN DEFAULT FALSE,
    city VARCHAR(50),
    panNumber VARCHAR(50),
    gstNumber VARCHAR(50),
    cin VARCHAR(50)
);

-- 11. Waitlist Table
CREATE TABLE IF NOT EXISTS Waitlist (
    waitlistId INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    slotId INT,
    requestDate DATE,
    priorityPosition INT,
    FOREIGN KEY (userId) REFERENCES User(userId) ON DELETE CASCADE,
    FOREIGN KEY (slotId) REFERENCES Slot(slotId) ON DELETE CASCADE
);
