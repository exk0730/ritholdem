
@REM Disable default output
@echo off

echo Building the jar files...

REM Make the client jar
jar cfm client.jar manifest-client.txt -C ..\build\web\WEB-INF\classes\ . && echo Client jar built successfully

REM Make the server jar
jar cfm server.jar manifest-server.txt -C ..\build\web\WEB-INF\classes\ . && echo Server jar built successfully

pause
