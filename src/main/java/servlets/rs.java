package servlets;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import static servlets.StartUp.influxDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static Common.Queues.requestMap;

public class rs extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println("Ответ принят");
        String id = req.getParameter("id");
        try {
            long rqTime = requestMap.getOrDefault(id,0L);
            if(rqTime!=0) {


                influxDB.write(Point.measurement("test")
                        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                        .tag("success", "true")
                        .addField("ResponseTime", System.currentTimeMillis()-rqTime)
                        .build());


            }
        } catch (Exception ex) {
            resp.getWriter().println(ex.toString());
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
