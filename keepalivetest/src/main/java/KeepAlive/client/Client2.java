package KeepAlive.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

public class Client2 {
  public static void main(String args[]) {
    Client2 client = new Client2();
    client.run();
  }

  public void run() {
    System.out.println("Client2 start");

    SocketConfig socketConfig =
        SocketConfig.custom().setSoKeepAlive(true).setSoTimeout(10000).build(); // We need to set
                                                                                // socket keep alive
    RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(20000).build();

//    PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
//    connManager.setMaxTotal(5);
//    connManager.setDefaultMaxPerRoute(4);
//
//    ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
//      public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
//        return 5;
//      }
//    };

    HttpClient client = HttpClients.custom()
//        .setKeepAliveStrategy(myStrategy)
        .setDefaultSocketConfig(socketConfig)
        .setDefaultRequestConfig(requestConfig)
//        .setConnectionManager(connManager)
        .build();
    
    HttpGet request = new HttpGet("http://localhost:8090/status");
    HttpResponse response;
    try {
      response = client.execute(request);


      BufferedReader rd =
          new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
      StringBuilder stringBuilder = new StringBuilder();

      String line = "";
      while ((line = rd.readLine()) != null) {
        stringBuilder.append(line);
      }

      System.out.println(stringBuilder.toString());

    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println("Client2 end");
  }


}
