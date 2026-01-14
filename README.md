# CityElectro - Smart Electronics Store Management System 

## Project Overview
Fast. Reliable. Digital Electronics Shopping.

A JAVA-based Electronics Store Management System for City Electronics, a retail electronics store located in Galle, Sri Lanka.

## Features 

### User Roles
1. **Administrator**
   - Manages products and monitors customer orders
   - Predefined credentials: username: `admin`, password: `admin123`

2. **Customer**
   - Browses products and places orders
   - Can register new accounts and login

### System Capabilities

#### Admin Features
- Add new products (ID, Name, Category, Price)
- Update existing product details
- Remove products from inventory
- View all available products
- View all customer orders with details

#### Customer Features
- Browse all available electronic products
- Add products to shopping cart with quantities
- View cart contents
- Calculate total payment
- Confirm and place orders
- View order summary

## Technical Implementation

### Architecture
- **Object-Oriented Design** with proper inheritance
- **In-memory storage** using ArrayList collections
- **Menu-driven console interface**
- **No database or file persistence**

### Class Structure
```
Main.java (Console Entry point)
MainGUI.java (GUI Entry point)
├── com.cityelectro.model/ (Entity classes)
│   ├── User.java (Base class)
│   ├── Admin.java (Admin user)
│   ├── Customer.java (Customer user)
│   ├── Product.java (Product entity)
│   ├── Order.java (Order entity)
│   └── OrderItem.java (Order line item)
├── com.cityelectro.service/ (Business logic)
│   ├── AuthenticationService.java (Login/Registration)
│   └── StoreService.java (Product/Order management)
└── com.cityelectro.ui/ (User interface)
    ├── Console UI (Original)
    │   ├── AdminUI.java
    │   └── CustomerUI.java
    └── GUI Components (AWT-based)
        ├── LandingPage.java (Splash screen)
        ├── AcknowledgmentDialog.java (Success confirmations)
        ├── MainGUI.java (Main window)
        ├── AdminGUIDashboard.java + dialogs
        ├── CustomerGUIDashboard.java + dialogs
        ├── Login/Registration dialogs
        └── Shopping cart dialogs
```

### Sample Products
The system comes pre-loaded with sample electronic products:
- Smartphones (Samsung, iPhone)
- Laptops (MacBook, Dell)
- Headphones (Sony, AirPods)
- TVs (Samsung, LG)
- Tablets (iPad, Samsung)

## How to Run

### Prerequisites
- Java 8 or higher installed
- Windows/Linux/Mac OS
- For GUI version: AWT-compatible display environment

### Available Versions

#### 1. Console Version (Original)
Perfect for academic environments and headless servers.

```bash
# Compile console version
javac -d bin -cp src src/Main.java src/com/cityelectro/model/*.java src/com/cityelectro/service/*.java

# Run console version
java -cp bin Main
```

#### 2. GUI Version (AWT-based)
Modern graphical user interface with intuitive dialogs and forms.

```bash
# Compile GUI version (Windows)
compile-gui.bat

# Or compile manually
javac -d bin -cp src src/MainGUI.java src/com/cityelectro/model/*.java src/com/cityelectro/service/*.java src/com/cityelectro/ui/*.java

# Run GUI version
java -cp bin MainGUI
```

### GUI Features
- **Landing Page**: Beautiful splash screen with background image and centered enter button overlay
- **Acknowledgment Dialogs**: Success confirmations for all user actions (registration, orders, cart operations, admin tasks)
- **Modern Interface**: Clean, professional AWT-based GUI with larger windows
- **Role-based Dashboards**: Separate interfaces for Admin and Customer
- **Interactive Product Management**: Add, update, remove products with dialogs
- **Shopping Cart**: Visual cart management with quantity controls
- **Order Processing**: Complete checkout workflow with confirmation
- **Order History**: Customer can view complete purchase history
- **Real-time Updates**: Live cart totals and product listings
- **Enhanced Admin Orders**: Detailed order view with individual items

### GUI User Experience
1. **Landing Page**: Application starts with a beautiful splash screen displaying the background image
2. **Enter Store**: Click the centered "ENTER STORE" button overlaying the image to access the main application
3. **Authentication**: Choose Admin or Customer login, or register as a new customer
4. **Role-based Dashboard**: Access appropriate features based on user role
5. **Enhanced Features**: Larger windows, detailed order views, and order history

### Alternative (Direct run with source)
```bash
java -cp src Main
```

## Usage Guide

1. **Start the Application**
   - Run the Main class
   - Choose from main menu options

2. **Admin Access**
   - Select "Admin Login"
   - Username: `admin`
   - Password: `admin123`

3. **Customer Registration**
   - Select "Customer Registration"
   - Provide name, username, and password
   - Minimum password length: 6 characters

4. **Shopping Process**
   - Login as customer
   - Browse products
   - Add items to cart with quantities
   - Review cart and total
   - Confirm order

## Data Storage
- All data is stored in memory using ArrayList collections
- No persistence - data resets when program terminates
- Sample products are loaded automatically on startup

## Key Features Implemented
- ✅ Role-based authentication
- ✅ Product catalog management
- ✅ Shopping cart functionality
- ✅ Order processing and tracking
- ✅ Menu-driven console interface
- ✅ **Modern AWT-based GUI interface**
- ✅ **Interactive dialogs and forms**
- ✅ **Visual product browsing and cart management**
- ✅ **Real-time cart updates and totals**
- ✅ Input validation and error handling
- ✅ Clean, readable code structure
- ✅ Object-oriented principles (encapsulation, inheritance, polymorphism)

## Academic Compliance
- Pure Java implementation
- No external libraries or frameworks
- Console-based interface (no GUI requirement)
- In-memory data storage (no database/file I/O)
- Comprehensive documentation and code comments
