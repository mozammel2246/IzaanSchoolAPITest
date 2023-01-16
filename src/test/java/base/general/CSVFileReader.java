package base.general;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVFileReader {

    public static List readCSVFile(int rowNumber, String fileName){
        List<List<String>> records = new ArrayList<List<String>>();
        try (CSVReader csvReader = new CSVReader(new FileReader(System.getProperty("user.dir")+"/data_files/"+fileName))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
            return records.get(rowNumber);
    }

    public static void main(String[] args){
        System.out.println(readCSVFile(1, "sales.csv").get(1));

    }
}
