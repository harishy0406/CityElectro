@echo off
echo Compiling CityElectro Electronics Store Management System...

REM Create bin directory if it doesn't exist
if not exist bin mkdir bin

REM Compile all Java files
javac -d bin -cp src ^
src/Main.java ^
src/com/cityelectro/model/Product.java ^
src/com/cityelectro/model/OrderItem.java ^
src/com/cityelectro/model/Order.java ^
src/com/cityelectro/model/User.java ^
src/com/cityelectro/model/Admin.java ^
src/com/cityelectro/model/Customer.java ^
src/com/cityelectro/service/AuthenticationService.java ^
src/com/cityelectro/service/StoreService.java ^
src/com/cityelectro/ui/AdminUI.java ^
src/com/cityelectro/ui/CustomerUI.java

if %errorlevel% equ 0 (
    echo Compilation successful!
    echo.
    echo To run the program, use:
    echo java -cp bin Main
) else (
    echo Compilation failed!
)
