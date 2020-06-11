package KeepAlive.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Client2 {
  public static void main(String args[]) {
    Client2 client = new Client2();
    client.run();
  }

  public void run() {
    System.out.println("Client2 start");

    SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).build();
    try (CloseableHttpClient client =
        HttpClients.custom().setDefaultSocketConfig(socketConfig).build()) {
      HttpGet request = new HttpGet("http://localhost:8090/status");

      try (CloseableHttpResponse response = client.execute(request)) {


        BufferedReader rd =
            new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder stringBuilder = new StringBuilder();

        String line = "";
        while ((line = rd.readLine()) != null) {
          stringBuilder.append(line);
        }

        System.out.println(stringBuilder.toString());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("Client2 end");
  }
}
