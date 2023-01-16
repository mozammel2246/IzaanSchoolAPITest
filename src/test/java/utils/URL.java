package utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class URL {


    private static Logger log = LogManager.getLogger(utils.URL.class.getName());
    // Base URL
    public static final String URL = "https://izaan-test.auth.us-east-1.amazoncognito.com";

    public static String getEndPoint(){
        log.info("Base URI : " + URL);
        return URL;
    }

    public static String getUrlWithEndPoint(String resource){
        log.info("URI End Point : " + URL + resource);
        return URL + resource;
    }

}
