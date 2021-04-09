

echo 'sending command1.xml'
ncat -u -w 1 127.0.0.1 1234 < command1.xml

sleep 3

echo 'sending command2.xml'
ncat -u -w 1 127.0.0.1 1234 < command2.xml 

sleep 47

echo 'sending command3.xml'
ncat -u -w 1 127.0.0.1 1234 < command3.xml 

sleep 50

echo 'sending command4.xml'
ncat -u -w 1 127.0.0.1 1234 < command4.xml 

sleep 150

echo 'sending command5.xml'
ncat -u -w 1 127.0.0.1 1234 < command5.xml 

echo 'DONE!!'
