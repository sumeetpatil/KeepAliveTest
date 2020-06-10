# KeepAliveTest
Test program to check keep alive in java


#### Curl to see keepalives
curl -vv http://localhost:8090/status --keepalive-time 10

#### TCP dump command 
tcpdump -i lo0 port 809 (For Mac), Use tcpdump -D to get the network adapters
