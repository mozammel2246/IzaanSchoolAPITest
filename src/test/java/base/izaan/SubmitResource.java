package base.izaan;

import com.google.gson.Gson;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.RandomIntNumbers;
import utils.Triplet;

import java.util.Map;
import java.util.Properties;

import static base.general.Base.completeURL;
import static base.general.Base.postRequest;
import static base.general.PayloadProcessor.getProcessedPayloadInJson;
import static base.general.PayloadProcessor.getValueFromPropertiesFile;
import static base.izaan.GetToken.getAVerifiedBearerToken;

public class SubmitResource {

    private static Logger log = LogManager.getLogger(SubmitResource.class.getName());

    public static Triplet<Response, String, Object> submitAResource(int age){
        log.info("Making a post call to submit a resource");
        try {
            /*Creating Gson object*/
            Gson gson = new Gson();
            /*Loading properties file*/
            Properties properties = getValueFromPropertiesFile();
            /*Getting a bearer token*/
            String token = getAVerifiedBearerToken();
            /*Creating complete url for this call*/
            String url = completeURL("url", "/test/submit");
            /*Mapping the payload with properties file. Mapping will make the payload easier to Alter*/
            Map<String, Object> payloadForSubmitResource = getProcessedPayloadInJson("submit.json", properties);
            /*Changing the payload value as per requirement.*/
            payloadForSubmitResource.put("age", age);
            /*Processing the payload back to string to pass as a request body*/
            String payloadInString = gson.toJson(payloadForSubmitResource);
            /*Making the post call*/
            Response responseBody = postRequest(url, payloadInString, token);
            /*Returning Response and Payload to verify the assertions with the help of Triplet class*/
            return new Triplet<>(responseBody, payloadInString);

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
