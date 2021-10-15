package example;

import github.com.IAFahim.BulkCertificate.Bulk;

public class Create_Image_READ_Style_Data_GenImage {
    public static void main(String[] args) {
        Bulk bulk = new Bulk(true);
        var map = bulk.readStyle("src/test/resources/Certificate List - Format.csv");
        bulk.repairCSVForReadData(map, "src/test/resources/Certificate List - final.csv");
    }
}
