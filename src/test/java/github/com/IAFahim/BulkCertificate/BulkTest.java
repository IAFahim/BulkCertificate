package github.com.IAFahim.BulkCertificate;

import org.junit.jupiter.api.Test;


class BulkTest {

    @Test
    void read_CSV_Make_Image_Create_CSV() {
        Bulk bulk=new Bulk();
        bulk.readStyle("src/test/resources/Certificate List - Format.csv");
//        bulk.repairCSVForReadData(null,"src/test/resources/Certificate List - final.csv");
    }
}