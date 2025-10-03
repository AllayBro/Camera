@echo off
cd /d C:\Users\AllayBro\Desktop\Camera

echo ===== КОМПИЛЯЦИЯ ВСЕХ ПРОГРАММ =====

javac -d out src\DronePhotoDOMAnalyzer.java src\DronePhotoSAXAnalyzer.java src\DronePhotoStAXAnalyzer.java src\CustomErrorHandler.java

if %errorlevel% neq 0 (
    echo *** ОШИБКА компиляции! ***
    pause
    exit /b %errorlevel%
)

echo.
echo ===== ВЫБЕРИТЕ ПРОГРАММУ ДЛЯ ЗАПУСКА =====
echo 1. DOM-анализатор
echo 2. SAX-анализатор
echo 3. StAX-анализатор
set /p choice="Введите номер (1-3): "

if "%choice%"=="1" (
    echo --- Запуск DOM версии ---
    type example.xml | java -cp out DronePhotoDOMAnalyzer
    start "" "C:\Users\AllayBro\Desktop\Camera\save\report.html"
) else if "%choice%"=="2" (
    echo --- Запуск SAX версии ---
    type example.xml | java -cp out DronePhotoSAXAnalyzer
    start "" "C:\Users\AllayBro\Desktop\Camera\save\report.html"
) else if "%choice%"=="3" (
    echo --- Запуск StAX версии ---
    type example.xml | java -cp out DronePhotoStAXAnalyzer
    start "" "C:\Users\AllayBro\Desktop\Camera\save\report.html"
) else (
    echo Неверный выбор!
)

pause
