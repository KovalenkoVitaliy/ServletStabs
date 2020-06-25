package servlets;

import Common.DontAnswer;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StartUp implements ServletContextListener {

    public static InfluxDB influxDB = null;

    public ScheduledExecutorService shedulerAnswer;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {


        final String serverURL = "http://localhost:8086";//, username = "root", password = "root";
        influxDB = InfluxDBFactory.connect(serverURL);
        String databaseName = "testDB";
        influxDB.query(new Query("CREATE DATABASE " + databaseName));
        influxDB.setDatabase(databaseName);
        influxDB.enableBatch(1000, 1000, TimeUnit.MILLISECONDS);

        shedulerAnswer = Executors.newSingleThreadScheduledExecutor();
        shedulerAnswer.scheduleAtFixedRate(new DontAnswer(), 0, 1000, TimeUnit.MILLISECONDS);
    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        influxDB.close();
    }
}
