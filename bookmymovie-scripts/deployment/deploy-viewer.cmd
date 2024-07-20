@echo off
title Deploy BookMyMovie Viewer 
@REM color 2F

@REM Print env variables set variables:
call set-env
echo Java Home=%JAVA_HOME%
echo:

@REM Deployment:
java -jar %DEPLOY_HOME%\bookmymovie-viewer\service\target\service.jar
echo:

echo Error code: %ERRORLEVEL%
echo:

<nul set /p "=Deployment failed! Press any key to exit..."
 pause >nul