package api;

import com.google.gson.Gson;
import dao.DailyEventDao;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DailyEvent;

public class DailyEventServlet extends HttpServlet {
  private DailyEventDao dailyEventDao;

  public DailyEventServlet() {
    dailyEventDao = DailyEventDao.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    Map<String, String[]> map = request.getParameterMap();
    if (map.size() != 0) {
      try {
        List<DailyEvent> dailyEvents = dailyEventDao.getDailyEventStatisticsByDateRange(map.get("startDate")[0], map.get("endDate")[0]);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(dailyEvents));
      } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("invalid url");
      }
      return;
    }
    try {
      List<DailyEvent> dailyEvents = dailyEventDao.getDailyEventDetails();
      response.setStatus(HttpServletResponse.SC_OK);
      response.getWriter().write(new Gson().toJson(dailyEvents));
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("invalid url");
      return;
    }
  }
}
