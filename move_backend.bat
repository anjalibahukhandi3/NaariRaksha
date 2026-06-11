@echo off
mkdir e:\NaariRaksha\backend_repo
xcopy /E /I /Y "e:\NaariRaksha\app\backend (1)\backend" "e:\NaariRaksha\backend_repo"
if %ERRORLEVEL% EQU 0 (
    rd /s /q "e:\NaariRaksha\app\backend (1)"
    echo Success
) else (
    echo Failure
)
