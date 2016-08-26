@echo off
cd /d "%~dp0"
title Droid-Explorer
call gradlew installDist
echo.
pause
