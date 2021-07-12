package api;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.PlaceWithActivities;
import processor.PlaceActivityProcessor;

public class PlaceActivityServlet extends HttpServlet {

  private PlaceActivityProcessor placeActivityProcessor;

  public PlaceActivityServlet() {
    placeActivityProcessor = PlaceActivityProcessor.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    String urlPath = request.getPathInfo();

    if (urlPath == null || urlPath.isEmpty()) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write(new Gson().toJson("missing parameter"));
      return;
    }
    String[] urlParts = urlPath.split("/");

    String date = urlParts[1];
    try {
      List<PlaceWithActivities> placeWithActivities = placeActivityProcessor.findPlaceLogWithActivitiesByDate(date);
      response.setStatus(HttpServletResponse.SC_OK);
      response.getWriter().write(new Gson().toJson(placeWithActivities));
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("invalid url");
      return;
    }
  }
}
