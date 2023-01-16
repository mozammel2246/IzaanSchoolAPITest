package base.general;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.URL;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;

import static base.general.PayloadProcessor.getValueFromPropertiesFile;

public class Base {

    private static Logger log = LogManager.getLogger(Base.class.getName());

    Properties properties = getValueFromPropertiesFile();

    public static String completeURL(String host, String resource){
        String url = getValueFromPropertiesFile().getProperty(host) + resource;
        log.info("End Point : " + resource);
        return url;
    }

    public static Response getToken() {
        try {
            String encoding = Base64.getEncoder().encodeToString((getValueFromPropertiesFile().getProperty("username")+":"+getValueFromPropertiesFile().getProperty("password")).getBytes("UTF-8"));
            String authorization = "Basic " + encoding;
            Response response = RestAssured
                    .given()
                    .header("Authorization", authorization)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .formParam("grant_type", getValueFromPropertiesFile().getProperty("grantType"))
                    .formParam("scope", getValueFromPropertiesFile().getProperty("scope"))
                    .post(completeURL("token_url", "/oauth2/token"));
            return response;
        }
        catch (Exception e){
            log.error(e);
            return null;
        }
    }

    /**
     * GetSessionId
     * */

    public static String getSessionId(){
        Response response;

        log.info("Starting Test Case : doLogin");

        String loginPayload = generatePayLoadString("login.json");

        String endPointURI = URL.getUrlWithEndPoint("/rest/auth/1/session");

        response = POSTRequest(endPointURI, loginPayload);
        log.info("Login Response Body: "+response.getBody().asString());

        String strResponse = TestUtils.getResposeString(response);
        JsonPath jsonRes = TestUtils.jsonParser(strResponse);
        String sessionID = jsonRes.getString("session.value");
        log.info("JIRA JSession ID : " + sessionID);
        return sessionID;
    }

    /**
     * Payload generator
     *
     * */

    public static String generatePayLoadString(String filename){
        log.info("Inside PayloadConverter function");
        String filePath = System.getProperty("user.dir")+"/payloads/"+filename;
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    public static Response POSTRequest(String uRI, String strJSON) {
        log.info("Inside POSTRequest call");
        RequestSpecification requestSpecification = RestAssured.given().body(strJSON);
        requestSpecification.contentType(ContentType.JSON);
        Response response = requestSpecification.post(uRI);
        log.debug(requestSpecification.log().all());
        return response;
    }

    /**
     * Methods to make http call
     */

    public static Response GETRequest(String uRI, String authToken) {

        log.info("Inside GETRequest call");
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Authorization", authToken);
        requestSpecification.contentType(ContentType.JSON);
        Response response = requestSpecification.get(uRI);
        log.debug(requestSpecification.log().all());
        return response;
    }
    public static Response POSTRequest(String uRI, String strJSON, String sessionID) {
        log.info("Inside POSTRequest call");
        RequestSpecification requestSpecification = RestAssured.given().body(strJSON);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.header("cookie", "JSESSIONID=" + sessionID+"");
        Response response = requestSpecification.post(uRI);
        log.debug("Post Request Specifications"+requestSpecification.log().all());
        return response;
    }
    public static Response postRequest(String uRI, String strJSON, String authToken){
        log.info("Inside POSTRequest call");
        RequestSpecification requestSpecification = RestAssured.given().body(strJSON);
        requestSpecification.header("Authorization", authToken);
        requestSpecification.contentType(ContentType.JSON);
        Response response = requestSpecification.post(uRI);
        log.debug(requestSpecification.log().all());
        return response;
    }

    public static Response postRequestWithoutPayload(String uRI, String authToken){
        log.info("Inside POSTRequest call");
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Authorization", authToken);
        requestSpecification.contentType(ContentType.JSON);
        Response response = requestSpecification.post(uRI);
        log.debug(requestSpecification.log().all());
        return response;
    }


    public static Response PUTRequest(String uRI, String strJSON, String sessionID) {
        log.info("Inside PUTRequest call");
        RequestSpecification requestSpecification = RestAssured.given().body(strJSON);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.header("cookie", "JSESSIONID=" + sessionID+"");
        Response response = requestSpecification.put(uRI);
        log.debug("PUT Request Specifications: "+requestSpecification.log().all());
        return response;
    }

    public static Response DELETERequest(String uRI, String strJSON) {
        log.info("Inside DELETERequest call");
        RequestSpecification requestSpecification = RestAssured.given().body(strJSON);
        requestSpecification.contentType(ContentType.JSON);
        Response response = requestSpecification.delete(uRI);
        log.debug(requestSpecification.log().all());
        return response;
    }

}
