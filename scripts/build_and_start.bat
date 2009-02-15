
@echo off

REM Make the client jar
jar cfm client.jar manifest-client.txt -C ..\build\web\WEB-INF\classes\ . && echo Client jar built successfully

REM Make the server jar
jar cfm server.jar manifest-server.txt -C ..\build\web\WEB-INF\classes\ . && echo Server jar built successfully

REM Start the server
echo Starting server...
start appclient -client server.jar

REM Start the client
echo Starting client...
start appclient -client client.jar

