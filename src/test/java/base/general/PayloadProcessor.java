package base.general;

import com.google.gson.Gson;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

public class PayloadProcessor {
    private static Logger log = LogManager.getLogger(TestUtils.class.getName());
    public static Properties getValueFromPropertiesFile(){
        Properties properties = new Properties();
        // Use env specific properties file
        String env = System.getProperty("env", "stage");
        if (env.equalsIgnoreCase("stage")) {
            try (InputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/profiles/" + "izaanstage.properties")) {
                properties.load(inputStream);
                log.info("Env Name: " + env);
            } catch (Exception e) {
                e.printStackTrace();
            }
//        String fieldValue = properties.getProperty(fieldKey);
            return properties;
        }
        else if (env.equalsIgnoreCase("prod")) {
            try (InputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/profiles/" + "izaanprod.properties")) {
                properties.load(inputStream);
                log.info("Env Name: " + env);

            } catch (Exception e) {
                e.printStackTrace();
            }
//        String fieldValue = properties.getProperty(fieldKey);
            return properties;
        }
        return properties;
    }


    public static Map<String, Object> getProcessedPayloadInJson(String jsonFile, Properties properties, String... params) throws IOException{
        Gson gson = new Gson();
        // create a reader
        Reader reader = Files.newBufferedReader(Paths.get(System.getProperty("user.dir")+ "/payloads/"+jsonFile));

        // convert JSON file to map
//        Map<String, Object> map = gson.fromJson(reader, Map.class);
        Map<String, Object> map = gson.fromJson(reader, Map.class);
       /* LinkedTreeMap connexPayTransaction = (LinkedTreeMap) map.get("ConnexPayTransaction");
        connexPayTransaction.put("ExpectedPayments", "10");
        System.out.println(connexPayTransaction.get("ExpectedPayments"));*/

        if (params != null) {
            for (String param:params) {
                map.put(param, properties.getProperty(param));
            }
        }
//        String payloadToJSON = gson.toJson(map);
        return map;
    }

    /*public static Sale getProcessedPayloadInPojo(String jsonFile, Properties properties, String... params) throws IOException{
        Gson gson = new Gson();
        // create a reader
        Reader reader = Files.newBufferedReader(Paths.get(System.getProperty("user.dir")+ "/payloads/"+jsonFile));

        // convert JSON file to map
//        Map<String, Object> map = gson.fromJson(reader, Map.class);
        Sale sale = gson.fromJson(reader, Sale.class);
       *//* LinkedTreeMap connexPayTransaction = (LinkedTreeMap) map.get("ConnexPayTransaction");
        connexPayTransaction.put("ExpectedPayments", "10");
        System.out.println(connexPayTransaction.get("ExpectedPayments"));*//*


        if (params != null) {
            for (String param:params) {
//                map.put(param, properties.getProperty(param));
                try {
                    FieldUtils.writeDeclaredField(sale, param, properties.getProperty(param));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

//        String payloadToJSON = gson.toJson(map);
//        return map;
        return sale;
    }*/

    /*public static void main(String[] args) throws IOException{
        System.out.println(PayloadProcessor.getProcessedPayloadInJson("create-ach-sales.json"));
        System.out.println(getValueFromPropertiesFile("deviceGuid"));
    }*/
}
