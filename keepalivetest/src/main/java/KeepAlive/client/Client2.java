package KeepAlive.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Client2 {
  public static void main(String args[]) {
    Client2 client = new Client2();
    client.run();
  }

  public void run() {
    HttpClient client = new DefaultHttpClient();
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
      System.out.println(e);
    }
  }


}
