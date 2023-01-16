package apitest.acceptancetests;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import utils.RandomIntNumbers;
import utils.Triplet;
import static base.assertions.SubmitResourceAssertion.verifySubmitResourceTest;
import static base.izaan.SubmitResource.submitAResource;

public class SubmitResourceTest {

    private static Logger log = LogManager.getLogger(SubmitResourceTest.class.getName());

    @Test (groups = "acceptance")
    public void resourceSubmitTest(){

        int age = RandomIntNumbers.randInt(10,64);
        Triplet<Response, String, Object> submitResourceObj = submitAResource(age);
        Response responseBody = submitResourceObj.getValue1();
        String payloadInStr = submitResourceObj.getValue2();

        verifySubmitResourceTest(payloadInStr, responseBody);
    }

    @Test (groups = "regression")
    public void minorResourceSubmitTest(){

        int age = RandomIntNumbers.randInt(10,17);
        Triplet<Response, String, Object> submitResourceObj = submitAResource(age);
        Response responseBody = submitResourceObj.getValue1();
        String payloadInStr = submitResourceObj.getValue2();

        verifySubmitResourceTest(payloadInStr, responseBody);
    }

    @Test (groups = "regression")
    public void adultResourceSubmitTest(){
        int age = RandomIntNumbers.randInt(18,64);
        Triplet<Response, String, Object> submitResourceObj = submitAResource(age);
        Response responseBody = submitResourceObj.getValue1();
        String payloadInStr = submitResourceObj.getValue2();

        verifySubmitResourceTest(payloadInStr, responseBody);
    }
    /*We can also try to do this test using data provider*/

}
