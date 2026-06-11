@echo off
REM Script to push backend code to GitHub

cd /d "%~dp0"

REM Initialize git if not already done
if not exist .git (
    git init
)

REM Add remote
git remote remove origin 2>nul
git remote add origin https://github.com/anjalibahukhandi3/NaariRaksha-backend.git

REM Stage all changes
git add .

REM Commit
git commit -m "Backend deployment: Spring Boot API with Docker support"

REM Push to main branch
git branch -M main
git push -u origin main --force

echo.
echo ✅ Backend code pushed to GitHub successfully!
pause
