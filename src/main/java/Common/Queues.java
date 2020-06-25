package Common;

import java.util.concurrent.ConcurrentHashMap;

public class Queues {
    public static ConcurrentHashMap<String, Long> requestMap = new ConcurrentHashMap<String, Long>();
}
