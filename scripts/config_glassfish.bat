
@REM Disable default output
@echo off

echo This script will modify your Glassfish configuration. Here are the operation it will perform:
echo - Add a physical topic named 'PhysicalBlackjackTopic'
echo - Add a topic named 'jms/BlackjackTopic'
echo - Add a connection factory named 'jms/BlackjackConnectionFactory'
echo Be sure that Glassfish is running.
echo
echo If you don't want to run this script, press Ctrl-C.
pause


asadmin create-jmsdest -T topic PhysicalBlackjackTopic && asadmin create-jms-resource --restype javax.jms.Topic --property Name=PhysicalBlackjackTopic jms/BlackjackTopic && asadmin create-jms-resource --restype javax.jms.ConnectionFactory jms/BlackjackConnectionFactory && pause
