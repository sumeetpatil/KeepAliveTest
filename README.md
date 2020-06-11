# KeepAliveTest
Test to check keep alive in java


## Curl to see keepalives
curl -vv http://localhost:8090/status --keepalive-time 10

## TCP dump command 
tcpdump -i lo0 port 8090 (For Mac), Use tcpdump -D to get the network interface

## Working Example
- The socket API (java and apache) traditionally does not support to configure the keepalive times, they only allow to turn it on.
- Used the below code to set Socket Config
- Changed the OS level params. Config shown below is for MacOS. The configuraion is in milliseconds

```
SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).build();
CloseableHttpClient client = HttpClients.custom().setDefaultSocketConfig(socketConfig).build();
```

```
> sysctl net.inet.tcp | grep -E "keepidle|keepintvl|keepcnt"
net.inet.tcp.keepidle: 7200000
net.inet.tcp.keepintvl: 75000
net.inet.tcp.keepcnt: 8

> sudo sysctl -w net.inet.tcp.keepidle=60000
> sudo sysctl -w net.inet.tcp.keepintvl=75000
> sudo sysctl -w net.inet.tcp.keepcnt=8
````

## Working process (Got this from [Stackoverflow](https://stackoverflow.com/questions/1480236/does-a-tcp-socket-connection-have-a-keep-alive#:~:text=Keep%2Dalive%20packets%20MUST%20only,too%20long%20for%20most%20applications.)) -
- Client opens a TCP Conn
- If the connection is silent for net.inet.tcp.keepidle(tcp_keepalive_time) seconds, send a single empty ACK packet
- Did the server respond with a corresponding ACK of its own?
- **YES** - > Return to step 2
- **NO**  - > Wait net.inet.tcp.keepintvl(tcp_keepalive_intvl) seconds, then send another ACK. Repeat until the number of ACK probes that have been sent equals net.inet.tcp.keepcnt(tcp_keep_alive_probes). If no response has been received at this point, send a RST and terminate the connection.


## References 
- https://hc.apache.org/httpcomponents-core-ga/httpcore/apidocs/org/apache/http/params/CoreConnectionPNames.html#SO_KEEPALIVE
- https://en.wikipedia.org/wiki/HTTP_persistent_connection#Keepalive_with_chunked_transfer_encoding
- https://fastmail.blog/2011/06/28/http-keep-alive-connection-timeouts/
- http://mail-archives.apache.org/mod_mbox/hc-httpclient-users/201605.mbox/%3CCABNS0sUrTU1FvWVAN3TqaGqAbZgKWvNQmoj5ZBEce_FLJL1QYg@mail.gmail.com%3E
- https://bugs.openjdk.java.net/browse/JDK-4143518
- Setting keepalives on OS level https://www.gnugk.org/keepalive.html
- Info on keep alive OS params https://stackoverflow.com/questions/1480236/does-a-tcp-socket-connection-have-a-keep-alive#:~:text=Keep%2Dalive%20packets%20MUST%20only,too%20long%20for%20most%20applications.
