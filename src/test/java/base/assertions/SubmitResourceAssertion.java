package base.assertions;

import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;

import static base.general.BaseAssertion.*;
import static base.general.TestUtils.getAKeyValueFromAResponseAsString;
import static base.general.TestUtils.getASpecificKeyValueAsInteger;

public class SubmitResourceAssertion {

    private static Logger log = LogManager.getLogger(SubmitResourceAssertion.class.getName());

    public static void verifySubmitResourceTest(String payload, Response response){
        log.info("Assertions to verify if the resource has been submitted");
        verifyStatusCode(response, 200);
        int ageInPayload = getASpecificKeyValueAsInteger(payload, "age");
        String expectedMessage = null;
        if (ageInPayload>9 && ageInPayload<18){
            expectedMessage = "You are Minor.";
        }
        else if (ageInPayload>17 && ageInPayload<65){
            expectedMessage = "You are an Adult";
        }
        else {
            System.out.println("check your json file");
        }
        Assert.assertEquals(getAKeyValueFromAResponseAsString(response, "message"), expectedMessage);
    }
}
