@echo off
echo Reorganizing backend folder...

REM Create backend folder at root
if not exist backend mkdir backend

REM Copy backend files
xcopy /E /I /Y "app\backend (1)\backend" "backend_temp"

REM Move to final location  
if exist backend_temp (
    xcopy /E /I /Y "backend_temp\*" "backend\"
    rd /s /q "backend_temp"
    rd /s /q "app\backend (1)"
    echo ✅ Backend reorganized successfully!
) else (
    echo ❌ Failed to reorganize
)

pause
