# KeepAliveTest
Test program to check keep alive in java


#### Curl to see keepalives
curl -vv http://localhost:8090/status --keepalive-time 10

#### TCP dump command 
tcpdump -i lo0 port 8090 (For Mac), Use tcpdump -D to get the network adapters

#### References 
- https://hc.apache.org/httpcomponents-core-ga/httpcore/apidocs/org/apache/http/params/CoreConnectionPNames.html#SO_KEEPALIVE
- https://en.wikipedia.org/wiki/HTTP_persistent_connection#Keepalive_with_chunked_transfer_encoding
- https://fastmail.blog/2011/06/28/http-keep-alive-connection-timeouts/
- http://mail-archives.apache.org/mod_mbox/hc-httpclient-users/201605.mbox/%3CCABNS0sUrTU1FvWVAN3TqaGqAbZgKWvNQmoj5ZBEce_FLJL1QYg@mail.gmail.com%3E
- https://bugs.openjdk.java.net/browse/JDK-4143518
- Setting keepalives on OS level https://www.gnugk.org/keepalive.html
- Info on keep alive OS params https://stackoverflow.com/questions/1480236/does-a-tcp-socket-connection-have-a-keep-alive#:~:text=Keep%2Dalive%20packets%20MUST%20only,too%20long%20for%20most%20applications.
