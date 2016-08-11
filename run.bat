@echo off
title Droid-Explorer

set bat="./build/install/droidexplorer/bin/droidexplorer.bat"

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