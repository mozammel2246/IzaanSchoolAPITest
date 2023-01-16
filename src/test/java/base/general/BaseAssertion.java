package base.general;

import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import utils.DateMethods;

import java.util.Map;

public class BaseAssertion {
    private static Logger log = LogManager.getLogger(BaseAssertion.class.getName());



    public static void verifyTrue(boolean flag){
        Assert.assertTrue(flag);
    }

    public static void verifyFalse(boolean flag){
        Assert.assertFalse(flag);
    }

    public static void verifyStatusCode(Response response, int status){
        Assert.assertEquals(TestUtils.getStatusCode(response), status, "Correct Status Code Returned");
    }
    public static void verifyStatusMessage(Response response, String status){
        Assert.assertEquals(TestUtils.getStatusMessage(response), status, "Correct Status message returned");
    }
    public static void verifyNotNull(Response response, String keyName){
        Assert.assertNotNull(TestUtils.getAKeyValueFromAResponseAsString(response, keyName), "Ensure that "+keyName+" is not null");
    }
    public static void verifyNull(Response response, String keyName){
        Assert.assertNull(TestUtils.getAKeyValueFromAResponseAsString(response, keyName), "Ensure that "+keyName+" is null");
    }
    public static void verifyFieldValuesAreEqual(Response response, String keyName, String expFieldValue){
        Assert.assertEquals(TestUtils.getAKeyValueFromAResponseAsString(response, keyName), expFieldValue, "Ensure that the actual and expected value of "+keyName+" is same");
    }
    public static void verifyResponseBody(Response response, String responseBody){
        Assert.assertEquals(TestUtils.getStatusMessage(response), responseBody);
    }
    public static void verifyAFieldNotPresentInTheResponseBody(Response response, String keyName, String printMessage){
        Gson gson = new Gson();
        Map<String, Object> responseInMap = gson.fromJson(response.asString(), Map.class);
        Assert.assertFalse(responseInMap.containsKey(keyName));
        System.out.println(printMessage);
    }
    public static boolean verifyTwoDatesAreEqual(String dateFromPayload, String dateFromResponse){
        int dateDifference = DateMethods.compareTwoDates(dateFromPayload, dateFromResponse);
        if (dateDifference == 0){
            return true;
        }
        else {
            return false;
        }
    }
    public static void verifyResonseBodyByJsonPath(Response response, String jsonPath, String expectedKeyValue){
        // First get the JsonPath object instance from the Response interface
        JsonPath jsonPathEvaluator = response.jsonPath();

        // Then simply query the JsonPath object to get a String value of the node
        // specified by JsonPath: City (Note: You should not put $. in the Java code)
        String actualKeyValue  = jsonPathEvaluator.get(jsonPath);

        // Let us print the city variable to see what we got
        log.info("Actual Key Value received from Response:  " + actualKeyValue);

        // Validate the response
        Assert.assertEquals(actualKeyValue, expectedKeyValue, "Correct value received in the Response");

        log.info("Response Assertion Successful");

    }
    public static void verifyResponseHeader(Response response, String headerKey, String headerValue){

        log.info(response.header(headerKey).toString());

        Assert.assertEquals(response.header(headerKey).matches(headerValue), true);

        log.info("Header "+ headerKey + " = " + headerValue +" available");
    }
    public static void verifyInvalidRequest(Response response, String keyName){
        Assert.assertEquals(TestUtils.getAKeyValueFromAResponseAsString(response, keyName), "The request is invalid.");
    }

}
