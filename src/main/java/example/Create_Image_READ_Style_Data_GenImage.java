package example;

import github.com.IAFahim.BulkCertificate.Bulk;

import java.io.File;
import java.io.IOException;

public class Create_Image_READ_Style_Data_GenImage {
    public static void main(String[] args) throws IOException {
        Bulk bulk = new Bulk(true,new File("C:\\Users\\Fahim\\IdeaProjects\\BulkCertificateACM\\Test\\img.png"));
        var map = bulk.readStyle("C:\\Users\\Fahim\\IdeaProjects\\BulkCertificateACM\\src\\test\\resources\\Certificate List - Format.csv");
        bulk.repairCSVForReadData(map, "src/test/resources/Certificate List - final.csv");
    }
}
