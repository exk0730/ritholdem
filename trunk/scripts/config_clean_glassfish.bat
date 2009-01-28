
@REM Disable default output
@echo off

echo This script will modify your Glassfish configuration in order to clean the changes made by config_glassfish.
echo Here are the operation it will perform:
echo - Delete the physical topic named 'PhysicalBlackjackTopic'
echo - Delete the topic named 'jms/BlackjackTopic'
echo - Delete the connection factory named 'jms/BlackjackConnectionFactory'
echo Be sure that Glassfish is running.
echo
echo If you don't want to run this script, press Ctrl-C.
pause

asadmin delete-jmsdest -T topic PhysicalBlackjackTopic && asadmin delete-jms-resource jms/BlackjackTopic && asadmin delete-jms-resource jms/BlackjackConnectionFactory && pause
