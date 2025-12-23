@echo off
echo Compiling CityElectro Electronics Store Management System - GUI Version...

REM Create bin directory if it doesn't exist
if not exist bin mkdir bin

REM Compile all Java files including GUI classes
javac -d bin -cp src ^
src/MainGUIApp.java ^
src/com/cityelectro/model/Product.java ^
src/com/cityelectro/model/OrderItem.java ^
src/com/cityelectro/model/Order.java ^
src/com/cityelectro/model/User.java ^
src/com/cityelectro/model/Admin.java ^
src/com/cityelectro/model/Customer.java ^
src/com/cityelectro/service/AuthenticationService.java ^
src/com/cityelectro/service/StoreService.java ^
src/com/cityelectro/ui/LandingPage.java ^
src/com/cityelectro/ui/AcknowledgmentDialog.java ^
src/com/cityelectro/ui/MainGUI.java ^
src/com/cityelectro/ui/AdminLoginDialog.java ^
src/com/cityelectro/ui/CustomerLoginDialog.java ^
src/com/cityelectro/ui/CustomerRegistrationDialog.java ^
src/com/cityelectro/ui/AdminGUIDashboard.java ^
src/com/cityelectro/ui/AddProductDialog.java ^
src/com/cityelectro/ui/UpdateProductDialog.java ^
src/com/cityelectro/ui/RemoveProductDialog.java ^
src/com/cityelectro/ui/OrdersDialog.java ^
src/com/cityelectro/ui/CustomerGUIDashboard.java ^
src/com/cityelectro/ui/QuantityDialog.java ^
src/com/cityelectro/ui/CartDetailsDialog.java ^
src/com/cityelectro/ui/CheckoutDialog.java ^
src/com/cityelectro/ui/OrderHistoryDialog.java

if %errorlevel% equ 0 (
    echo Compilation successful!
    echo.
    echo To run the GUI application, use:
    echo java -cp bin MainGUIApp
) else (
    echo Compilation failed!
)
