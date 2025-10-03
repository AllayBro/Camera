@echo off
cd /d C:\Users\AllayBro\Desktop\Camera

echo ===== Компиляция программы XSLT =====

javac -d out src\DronePhotoXSLTTransformer.java

if %errorlevel% neq 0 (
    echo *** Ошибка компиляции! ***
    pause
    exit /b %errorlevel%
)

echo.
echo ===== Запуск программы XSLT =====
java -cp out DronePhotoXSLTTransformer

pause
