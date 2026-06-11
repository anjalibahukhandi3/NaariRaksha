#!/bin/bash
# Script to push backend code to GitHub

cd "$(dirname "$0")"

# Initialize git if not already done
if [ ! -d .git ]; then
    git init
fi

# Add remote
git remote remove origin 2>/dev/null
git remote add origin https://github.com/anjalibahukhandi3/NaariRaksha-backend.git

# Stage all changes
git add .

# Commit
git commit -m "Backend deployment: Spring Boot API with Docker support"

# Push to main branch
git branch -M main
git push -u origin main --force

echo "✅ Backend code pushed to GitHub successfully!"
