package base.izaan;

import io.restassured.path.json.JsonPath;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static base.general.Base.getToken;
import static base.general.BaseAssertion.verifyNotNull;
import static base.general.BaseAssertion.verifyStatusCode;

public class GetToken {

    private static Logger log = LogManager.getLogger(GetToken.class.getName());

    public static String getAVerifiedBearerToken(){
        log.info("Get a bearer token for the user");
        verifyStatusCode(getToken(), 200);
        verifyNotNull(getToken(), "access_token");
        JsonPath responseBodyInJsonPath = new JsonPath(getToken().asString());
        String token = responseBodyInJsonPath.get("access_token");
        return "Bearer " + token;
    }

}
