# KeepAliveTest
Test to check keep alive probes in java

## Problem Statement
Long running java http requests to server cause packet drops due to firewall configuration tcp timeout

## Curl to see keepalives in tcpdump/wireshark
curl -vv http://localhost:8090/status --keepalive-time 10

## TCP dump command to check keep alive packets. Use wireshark to get more insights.
tcpdump -i lo0 port 8090 (For Mac), Use tcpdump -D to get the network interface

## Solution
1. Sending keep-alive probes will keep the connection alive. Which is seen in curl command.
2. The socket API (java and apache) traditionally does not support to configure the keepalive times, they only allow to turn it on.
3. Used the below code to turn on keepalive in java using apache http client
4. Changed the OS level params. Config shown below is for MacOS. The configuraion is in milliseconds

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
> sysctl net.inet.tcp | grep -E "keepidle|keepintvl|keepcnt"
net.inet.tcp.keepidle: 60000
net.inet.tcp.keepintvl: 75000
net.inet.tcp.keepcnt: 8
````

## Working process (Got this from [Stackoverflow](https://stackoverflow.com/questions/1480236/does-a-tcp-socket-connection-have-a-keep-alive#:~:text=Keep-Alive%20Process))
1. Client opens a TCP Connection
2. If the connection is silent for net.inet.tcp.keepidle(tcp_keepalive_time) seconds, send a single empty ACK packet
3. Did the server respond with a corresponding ACK of its own?
   -  **YES** - > Return to step 2
   -  **NO**  - > Wait net.inet.tcp.keepintvl(tcp_keepalive_intvl) seconds, then send another ACK. Repeat until the number of ACK probes that have been sent equals net.inet.tcp.keepcnt(tcp_keep_alive_probes). If no response has been received at this point, send a RST and terminate the connection.


## References 
- https://hc.apache.org/httpcomponents-core-ga/httpcore/apidocs/org/apache/http/params/CoreConnectionPNames.html#SO_KEEPALIVE
- https://en.wikipedia.org/wiki/HTTP_persistent_connection#Keepalive_with_chunked_transfer_encoding
- https://fastmail.blog/2011/06/28/http-keep-alive-connection-timeouts/
- http://mail-archives.apache.org/mod_mbox/hc-httpclient-users/201605.mbox/%3CCABNS0sUrTU1FvWVAN3TqaGqAbZgKWvNQmoj5ZBEce_FLJL1QYg@mail.gmail.com%3E
- https://bugs.openjdk.java.net/browse/JDK-4143518
- [Setting keepalives on OS level](https://www.gnugk.org/keepalive.html)
- [Info on keep alive OS params](https://stackoverflow.com/questions/1480236/does-a-tcp-socket-connection-have-a-keep-alive#:~:text=Keep-Alive%20Process)
