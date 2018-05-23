@echo off

set srcfolder=%1
set servicename=%2
set weblogicdomain=%3
set weblogicfolder=%weblogicdomain%\servers\AdminServer\upload\

echo ========================Deploy:%2 =====================
cd %srcfolder%
cd %servicename%

call mvn clean install -DskipTests=true

echo ========================Copy %2.war to %weblogicfolder%%servicename%\app======================
cd target
copy *.war %weblogicfolder%%servicename%\app