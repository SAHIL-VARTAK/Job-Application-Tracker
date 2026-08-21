@echo off
setlocal

set "JAVA_HOME=C:\Users\SAHIL\Downloads\graalvm-jdk-22_windows-x64_bin\graalvm-jdk-22.0.2+9.1"
set "PATH=%JAVA_HOME%\bin;%PATH%"

call "C:\Program Files (x86)\Microsoft Visual Studio\18\BuildTools\VC\Auxiliary\Build\vcvarsall.bat" amd64

call mvnw.cmd -Pnative clean package -Dmaven.test.skip=true

pause