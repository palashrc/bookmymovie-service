@echo off
title Build BookMyMovie Utility Parent
@REM color 2F

@REM Print env variables set variables:
call set-env
echo Java Home=%JAVA_HOME%
echo Maven Home=%M2_HOME%
echo Build Home=%BUILD_HOME%

call mvn -e -f %BUILD_HOME%\bookmymovie-utility\pom.xml clean package -DskipTests=true

echo:
if %ERRORLEVEL% == 0 (
	echo ------------------------------------------------------------------------------
	echo !!!   P R O J E C T S   B U I L T   S U C C E S S F U L L Y   !!!
	echo ------------------------------------------------------------------------------
) else (
	echo Build Error!
	echo Error code: %ERRORLEVEL%
)

pause > nul