# CityElectro - NetBeans Setup Guide

## Running CityElectro in Apache NetBeans

Yes! You can absolutely run the CityElectro project in Apache NetBeans. Here's a comprehensive guide to set it up and run it successfully.

---

## 📋 Prerequisites

- **Apache NetBeans 12.0 or higher** (preferably with JDK 17+ support)
- **Java Development Kit (JDK) 17 or higher** installed
- **Windows/Linux/macOS** operating system

---

## 🚀 Step-by-Step Setup Instructions

### Step 1: Create New Java Project

1. **Open NetBeans IDE**
2. **File → New Project**
3. **Choose Category**: `Java with Maven` or `Java Application`
   - **Recommended**: `Java Application` (simpler for this project)
4. **Project Name**: `CityElectro`
5. **Location**: Choose your desired folder
6. **Package**: Leave default or set to `cityelectro`
7. **Click Finish**

### Step 2: Set Up Project Structure

Your NetBeans project should have this structure:

```
CityElectro/
├── src/
│   ├── com/
│   │   └── cityelectro/
│   │       ├── model/     ← Entity classes
│   │       ├── service/   ← Business logic
│   │       └── ui/        ← GUI components
│   ├── Main.java          ← Console version
│   └── MainGUIApp.java    ← GUI version
├── images/                ← Background images
│   └── bg.png
└── build/                 ← NetBeans build output
```

### Step 3: Copy Source Files

#### Method 1: Copy Files Manually

1. **Copy all Java files** from your existing project to NetBeans `src` folder:
   ```
   Copy these files to: CityElectro/src/com/cityelectro/
   ├── model/Admin.java
   ├── model/Customer.java
   ├── model/Order.java
   ├── model/OrderItem.java
   ├── model/Product.java
   ├── model/User.java
   ├── service/AuthenticationService.java
   ├── service/StoreService.java
   ├── ui/AcknowledgmentDialog.java
   ├── ui/AddProductDialog.java
   ├── ui/AdminGUIDashboard.java
   ├── ui/AdminLoginDialog.java
   ├── ui/CartDetailsDialog.java
   ├── ui/CheckoutDialog.java
   ├── ui/CustomerGUIDashboard.java
   ├── ui/CustomerLoginDialog.java
   ├── ui/CustomerRegistrationDialog.java
   ├── ui/LandingPage.java
   ├── ui/MainGUI.java
   ├── ui/OrdersDialog.java
   ├── ui/QuantityDialog.java
   ├── ui/RemoveProductDialog.java
   ├── ui/UpdateProductDialog.java
   ```

2. **Copy main classes** to `src/` folder:
   ```
   ├── Main.java
   └── MainGUIApp.java
   ```

3. **Create images folder** in project root and copy:
   ```
   images/bg.png
   ```

#### Method 2: Import Existing Project

1. **File → Import Project → From ZIP**
2. **Select your existing project folder**
3. **NetBeans will detect the structure automatically**

### Step 4: Configure Project Properties

1. **Right-click project → Properties**
2. **Sources**:
   - **Source Package Folder**: `src`
   - **Test Package Folder**: (leave empty)

3. **Libraries**:
   - Should automatically detect JDK
   - No external libraries needed (pure Java)

4. **Run**:
   - **Main Class**: Choose based on version you want to run
     - Console: `Main`
     - GUI: `MainGUIApp`

### Step 5: Set Up Run Configurations

#### For Console Version:
1. **Right-click project → Properties → Run**
2. **Main Class**: `Main`
3. **Working Directory**: Project directory
4. **Arguments**: (leave empty)

#### For GUI Version:
1. **Right-click project → Set Main Class**
2. **Choose**: `MainGUIApp`
3. **Run → Run Project** (F6)

---

## 🎯 Running the Application

### Method 1: Using NetBeans Interface

1. **Select the main class** in Projects window
2. **Right-click → Run File** (Shift+F6)
   - Or **Run → Run Project** (F6)

### Method 2: Command Line (from NetBeans)

1. **Right-click project → Build** (F11)
2. **Navigate to project dist folder**
3. **Run**: `java -jar CityElectro.jar`

### Method 3: Direct Class Execution

1. **Open terminal in NetBeans**: Window → IDE Tools → Terminal
2. **Compile**: `javac -d build -cp src src/*.java src/com/cityelectro/**/*.java`
3. **Run Console**: `java -cp build Main`
4. **Run GUI**: `java -cp build MainGUIApp`

---

## 🔧 Troubleshooting

### Common Issues:

#### 1. **"Main class not found" Error**
- **Solution**: Right-click project → Set Main Class → Choose correct class
- **Verify**: Check that Main.java or MainGUIApp.java exists in src/

#### 2. **Package Structure Issues**
- **Solution**: Ensure files are in correct package folders
- **Verify**: `src/com/cityelectro/model/Product.java` should exist

#### 3. **Image Not Loading**
- **Solution**: Place `bg.png` in `images/` folder in project root
- **Alternative**: Run from project root directory

#### 4. **Compilation Errors**
- **Solution**: Clean and Build project (Shift+F11)
- **Check**: JDK version compatibility (17+ recommended)

#### 5. **GUI Not Displaying**
- **Solution**: Ensure system has GUI support
- **Try**: Run console version first to verify Java works

---

## 🏗️ NetBeans-Specific Features

### Debugging:
1. **Set breakpoints** by clicking line numbers
2. **Debug → Debug Project** (Ctrl+F5)
3. **Step through code** with F8 (Step Over), F7 (Step Into)

### Testing:
1. **Right-click class → Tools → Create Tests**
2. **Run tests**: Run → Test Project

### Code Analysis:
- **Inspect Code**: Source → Inspect
- **Find Usages**: Right-click → Find Usages
- **Refactor**: Right-click → Refactor

### Version Control (Git):
- **Team → Git → Initialize Repository**
- **Add .gitignore**: Copy from project root
- **Commit**: Team → Commit

---

## 📊 Project Statistics in NetBeans

After setup, you'll see:

- **~25 Java files** in the project
- **~2000+ lines of code**
- **Multiple packages** (model, service, ui)
- **AWT-based GUI** components
- **Clean architecture** with separation of concerns

---

## 🎨 NetBeans Advantages for This Project

1. **Visual GUI Builder**: Design dialogs visually (though we use code)
2. **Code Completion**: IntelliSense-like features for Java
3. **Integrated Debugger**: Step-through debugging capabilities
4. **Project Management**: Easy file organization and navigation
5. **Built-in Terminal**: Compile and run from IDE
6. **Maven Support**: If you want to upgrade to Maven project later

---

## 🚀 Advanced NetBeans Configuration

### Enable Assertions:
1. **Project Properties → Run**
2. **VM Options**: `-ea`

### Add VM Arguments:
1. **Project Properties → Run**
2. **Arguments**: Add any runtime arguments

### Custom Build Scripts:
1. **Files tab → build.xml**
2. **Add custom targets** for specific build tasks

---

## 📝 Tips for Development

### Code Navigation:
- **Ctrl+Space**: Code completion
- **Ctrl+Click**: Go to definition
- **Alt+Insert**: Generate code (constructors, getters/setters)

### Refactoring:
- **Right-click → Refactor → Rename**: Safe renaming
- **Extract Method**: Select code → Refactor → Extract Method

### Productivity Shortcuts:
- **F6**: Run project
- **F11**: Build project
- **Shift+F11**: Clean and Build
- **Ctrl+Shift+I**: Fix imports

---

## ✅ Success Checklist

- [ ] NetBeans project created successfully
- [ ] All Java files copied to correct locations
- [ ] Package structure matches: `com.cityelectro.*`
- [ ] Images folder created with `bg.png`
- [ ] Main class configured (Main or MainGUIApp)
- [ ] Project builds without errors
- [ ] Console version runs successfully
- [ ] GUI version displays properly

---

## 🎯 Next Steps

Once set up in NetBeans, you can:

1. **Explore the code** using NetBeans navigation features
2. **Debug specific functions** using breakpoints
3. **Modify and extend** the application
4. **Add new features** with NetBeans assistance
5. **Generate documentation** using built-in tools

**Happy coding with NetBeans!** 🚀

---

*This guide is specific to the CityElectro project structure and requirements.*
