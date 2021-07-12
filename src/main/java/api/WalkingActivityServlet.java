package api;

import com.google.gson.Gson;
import dao.WalkingActivityDao;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.WalkingActivity;

public class WalkingActivityServlet extends HttpServlet {

  private WalkingActivityDao walkingActivityDao;

  public WalkingActivityServlet() {
    walkingActivityDao = WalkingActivityDao.getInstance();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    StringBuilder sb = new StringBuilder();
    BufferedReader reader = request.getReader();
    String line;
    while ((line = reader.readLine()) != null) {
      sb.append(line);
    }
    String body = sb.toString();
    try {
      WalkingActivity walkingActivity = new Gson().fromJson(body, WalkingActivity.class);
      walkingActivityDao.createWalkingActivity(walkingActivity);
      response.setStatus(HttpServletResponse.SC_CREATED);
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("invalid url");
      return;
    }
  }
}
