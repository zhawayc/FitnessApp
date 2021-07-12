package api;

import com.google.gson.Gson;
import dao.PlaceLogDao;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.PlaceLog;

public class PlaceLogServlet extends HttpServlet {

  private PlaceLogDao placeLogDao;

  public PlaceLogServlet() {
    placeLogDao = PlaceLogDao.getInstance();
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
      PlaceLog placeLog = new Gson().fromJson(body, PlaceLog.class);
      Long id = placeLogDao.createPlaceLog(placeLog);
      response.setStatus(HttpServletResponse.SC_CREATED);
      response.getWriter().write(new Gson().toJson(id));
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("invalid url");
      return;
    }
  }
}
