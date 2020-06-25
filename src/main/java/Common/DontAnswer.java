package Common;

import org.influxdb.dto.Point;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static Common.Queues.requestMap;
import static servlets.StartUp.influxDB;

public class DontAnswer implements Runnable {
    //int countError = 0;
    @Override
    public void run() {
        requestMap.entrySet().stream().filter(entry -> {
            Object value = entry.getValue();
            long transactionTime = (long) value;
            if (System.currentTimeMillis() - transactionTime > 60000) {
                //countError++;
                influxDB.write(Point.measurement("test")
                        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                        .tag("success", "false")
                        .addField("ResponseTime", 60000)
                        .build());
                return true;
            }
            return false;
        }).map(Map.Entry::getKey).forEach(requestMap::remove);

//        if (countError > 0) {
//
//
//        }
    }
}
