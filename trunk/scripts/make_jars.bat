
@REM Disable default output
@echo off

echo Building the jar files...

REM Make the client jar
jar cfm client.jar manifest-client.txt -C ..\build\classes\ deliverable3 && echo Client jar built successfully

REM Make the server jar
jar cfm server.jar manifest-server.txt -C ..\build\classes\ deliverable3 && echo Server jar built successfully

pause
