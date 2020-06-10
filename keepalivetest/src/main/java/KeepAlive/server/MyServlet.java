package KeepAlive.server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_OK);
    response.getWriter().println("{ \"status\": \"ok\"}");

    try {
      Thread.sleep(10 * 60 * 1000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    System.out.println("Done");
  }
}
