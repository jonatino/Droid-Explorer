@echo off
title Droid-Explorer

set bat="./build/install/Droid-Explorer/bin/Droid-Explorer.bat"

:loop
if exist %bat% (
    call %bat%
    pause
) else (
    call build.bat
    cls
    title Droid-Explorer
    goto loop
)