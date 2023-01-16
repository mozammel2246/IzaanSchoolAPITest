package base.general;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class TestUtils {

    private static Logger log = LogManager.getLogger(TestUtils.class.getName());

    public static String getResposeString(Response response){
        log.info("Converting Response to String");
        String strResponse = response.getBody().asString();
        log.debug("String Response"+ strResponse);
        return strResponse;
    }

    public static JsonPath jsonParser(String response){
        log.info("Parsing String Response to JSON");
        JsonPath jsonResponse = new JsonPath(response);
        log.debug("JSON Response: "+jsonResponse);
        return jsonResponse;
    }

    public static XmlPath xmlParser(String response){
        log.info("Parsing String Response to XML");
        XmlPath xmlResponse = new XmlPath(response);
        log.debug("XML Response: "+xmlResponse);
        return xmlResponse;
    }

    public static int getStatusCode (Response response){
        log.info("Getting Response Code");
        int statusCode = response.getStatusCode();
        log.info("Status Code: "+statusCode);
        return statusCode;
    }

    public static String getStatusMessage(Response response){
        log.info("Getting Response Message");
        String responseMessage = response.getStatusLine();
        log.info("Response Message: "+responseMessage);
        return responseMessage;
    }

    public static String getResponseBody(Response response){

        // Retrieve the body of the Response
        ResponseBody body = response.getBody();

        // By using the ResponseBody.asString() method, we can convert the  body
        // into the string representation.
        String responseBody = body.asString();
        log.info("Response Body is: " + responseBody);
        return new String();
    }
    public static String getAKeyValueFromAResponseAsString(Response response, String keyName){
        log.info("Getting an attribute value from Response body");
        JsonPath jsonResponse = new JsonPath(response.asString());
        String keyValue = jsonResponse.getString(keyName);
        log.debug(keyName+": "+keyValue);
        return keyValue;
    }
    public static String getAKeyValueFromAStringResponse(String response, String keyName){
        log.info("Getting an attribute value from Response body");
        JsonPath jsonResponse = new JsonPath(response);
        String keyValue = jsonResponse.getString(keyName);
        log.debug(keyName+": "+keyValue);
        return keyValue;
    }
    public static String getASpecificKeyValueAsString(String jsonFile, String keyName){
        log.info("Getting an attribute value from a JSON file");
        JsonPath jsonObject = new JsonPath(jsonFile);
        String keyValue = jsonObject.getString(keyName);
        log.debug(keyName+": "+keyValue);
        return keyValue;
    }
    public static int getASpecificKeyValueAsInteger(String jsonFile, String keyName){
        log.info("Getting an attribute value from a JSON file");
        JsonPath jsonObject = new JsonPath(jsonFile);
        String keyValue = jsonObject.getString(keyName);
        int value = Integer.parseInt(keyValue);
        log.debug(keyName+": "+keyValue);
        return value;
    }
    public static boolean checkThisKeyPresentInThePayload(Map<String, Object> payload, String keyName){
        log.info("Validating a specific key is available in the given payload");
        try {
            Assert.assertThat(payload, hasKey(keyName));
            return true;
        }
        catch (AssertionError error){
            error.printStackTrace();
            return false;
        }
    }

}
