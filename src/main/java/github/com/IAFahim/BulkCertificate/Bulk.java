package github.com.IAFahim.BulkCertificate;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;

public class Bulk {

    public void Read_CSV_Make_Image_Create_CSV(String dataCSVPath) {
        Iterable<CSVRecord> csv_data = CSVReadAll(dataCSVPath);
        for (var d : csv_data) {
            for (int i = 0; i < d.size(); i++) {
                System.out.println(d.get(i));
            }
        }

    }

    private Iterable<CSVRecord> CSVReadAll(String dataCSVPath) {
        Reader reader = null;
        try {
            reader = new FileReader(dataCSVPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Iterable<CSVRecord> csv_data = null;
        try {
            if (reader != null) {
                csv_data = CSVFormat.EXCEL.parse(reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csv_data;
    }
}


