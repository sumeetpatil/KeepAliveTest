package KeepAlive.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

public class Server {
  private org.eclipse.jetty.server.Server server;

  public void start() throws Exception {
    server = new org.eclipse.jetty.server.Server();
    ServerConnector connector = new ServerConnector(server);
    connector.setPort(8090);
    server.setConnectors(new Connector[] {connector});
    ServletHandler handler = new ServletHandler();
    server.setHandler(handler);
    handler.addServletWithMapping(MyServlet.class, "/status");
    server.start();
  }
}
