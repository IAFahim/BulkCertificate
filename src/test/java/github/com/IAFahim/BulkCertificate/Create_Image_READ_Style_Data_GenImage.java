package github.com.IAFahim.BulkCertificate;

import github.com.IAFahim.BulkCertificate.Bulk;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Create_Image_READ_Style_Data_GenImage {
    public static void main(String[] args) throws IOException {
        Bulk bulk = new Bulk(new File("C:\\Users\\Fahim\\IdeaProjects\\BulkCertificateACM\\src\\test\\resources\\TestDataGiven\\img.png"),"test",new Dimension(1462,1080));
        var map = bulk.readStyle("C:\\Users\\Fahim\\IdeaProjects\\BulkCertificateACM\\src\\test\\resources\\TestDataGiven\\Certificate List - Format.csv");
        bulk.repairCSVForReadData(map, "C:\\Users\\Fahim\\IdeaProjects\\BulkCertificateACM\\src\\test\\resources\\TestDataGiven\\Certificate List - final.csv");
    }
}
