package KeepAlive.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import org.eclipse.jetty.http.HttpMethod;

public class Client {

  public static void main(String args[]) {
    Client client = new Client();
    client.run();
  }

  public void run() {
    HttpURLConnection connection = null;
    URI uri;
    try {
      uri = new URI("http://localhost:8090/status");

      connection = (HttpURLConnection) uri.toURL().openConnection();
      connection.setRequestMethod(HttpMethod.GET.name());
      connection.connect();
      StringBuilder stringBuilder;
      BufferedReader reader = null;
      reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      stringBuilder = new StringBuilder();

      String line = null;
      while ((line = reader.readLine()) != null) {
        stringBuilder.append(line + "\n");
      }

      System.out.println("Got the response - " + stringBuilder.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
