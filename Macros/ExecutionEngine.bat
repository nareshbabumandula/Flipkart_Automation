@echo off
SET DEVELOPMENT_HOME= C:\Selenium_Scripts\Demo_Automation
cd %DEVELOPMENT_HOME%
call  mvn package
call  mvn exec:java
PAUSE
