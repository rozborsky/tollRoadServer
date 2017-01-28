package ua.rozborsky.tollRoadServer.classes;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by roman on 28.01.2017.
 */
public class Properties {
    private static java.util.Properties propertie;
    private static final Logger log = Logger.getLogger(Properties.class);

    static {
        String resourceName = "myconf.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        propertie = new java.util.Properties();
        try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            propertie.load(resourceStream);
        }catch (IOException e){
            log.error(e);
        }
    }

    public static int port() {
        return Integer.valueOf(propertie.getProperty("port"));
    }

    public static String url() {
        return propertie.getProperty("url");
    }

    public static String user() {
        return propertie.getProperty("user");
    }

    public static String password() {
        return propertie.getProperty("password");
    }



}
