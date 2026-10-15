
@echo off
title Expense Tracker Application

cd /d "%~dp0"

echo ======================================
echo      Expense Tracker Application
echo ======================================
echo.

where mvn >nul 2>&1
if errorlevel 1 (
    echo ERROR: Maven was not found.
    echo Please check your Maven installation and PATH.
    pause
    exit /b 1
)

echo Starting Expense Tracker...
echo Keep this window open while using the app.
echo.

call mvn spring-boot:run

echo.
echo Application stopped.
pause
