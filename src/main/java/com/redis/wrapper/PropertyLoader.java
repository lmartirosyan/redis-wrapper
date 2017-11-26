package com.redis.wrapper;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public  class PropertyLoader {

    private static Logger logger = Logger.getLogger(PropertyLoader.class);


    /**
     * Loads properties frm specified resource file
     *
     * @return
     */
    public static Properties loadStreamProperties(String propertyFile) {

        return loadProperties( propertyFile);
    }

    /**
     *
      * @param propertyFile
     * @return
     */
    public static Properties loadProperties(String propertyFile) {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertyLoader.class.getClassLoader().getResourceAsStream(propertyFile)) {
            properties.load(inputStream);

        } catch (IOException e) {
            logger.error("property file is not found: " + propertyFile,e);
        }
        return properties;
    }
}
