package github.com.IAFahim.BulkCertificate;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Create_Image_READ_Style_Data_GenImage {
    public static void main(String[] args) throws IOException {
//        GenerateImage.createImage(1462,1080,"C:\\Users\\Fahim\\IdeaProjects\\BulkCertificateACM\\src\\test\\resources\\TestDataGiven\\white.png");
        Bulk bulk = new Bulk(new File("C:\\Users\\Fahim\\IdeaProjects\\BulkCertificateACM\\src\\test\\resources\\TestDataGiven\\white.png"), "C:\\Users\\Fahim\\IdeaProjects\\BulkCertificateACM\\src\\test\\resources\\TestDataOutput", new Dimension(1462, 1080));
        var map = bulk.readStyle("C:\\Users\\Fahim\\IdeaProjects\\BulkCertificateACM\\src\\test\\resources\\TestDataGiven\\Certificate List - Format.csv");
        bulk.repairMainCSVAndCreateImage(map, "C:\\Users\\Fahim\\IdeaProjects\\BulkCertificateACM\\src\\test\\resources\\TestDataGiven\\Certificate List - final.csv");
    }
}
