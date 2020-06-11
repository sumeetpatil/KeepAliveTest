import KeepAlive.client.Client2;

public class Main {
  public static void main(String args[]) throws Exception {
    KeepAlive.server.Server server = new KeepAlive.server.Server();
    server.start();

    Client2 client = new Client2();
    client.run();
  }
}
