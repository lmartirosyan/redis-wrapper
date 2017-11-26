package com.redis.wrapper;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Properties;
import java.util.Set;

public class RedisWrapper {

    private static Jedis jedis;
    private static Logger logger = Logger.getLogger(PropertyLoader.class);

    private static void connectToRedis(Properties properties){
        String host = properties.getProperty("redis.host");
        int port = Integer.parseInt(properties.getProperty("redis.port"));

        jedis=new Jedis(host, port);
        logger.info("connected to Redis...");
    }

    public static void main(String[] args) {
        Properties properties=PropertyLoader.loadProperties("redis.properties");
        connectToRedis(properties);

        String country=args[0];
        long end=new Date().getTime()/60000;
        long start=end-7*24*60;

        if (args.length > 1) {
         start=Long.parseLong(args[1]);
          end=Long.parseLong(args[2]);
        }
        process(country, start, end);

    }

    private static void process(String country, long start, long end) {
        int result=0;
        long range=end-start;
        while (range>0){
            start++;
            String key = String.join("_", country, start + "");
            String val = jedis.get(key);
            if (val!=null) {
                result+=Integer.parseInt(val);
                logger.info("key: " + key + " val: " + val);
            }
            range--;
        }
        logger.info(country+": "+result);
    }

}
