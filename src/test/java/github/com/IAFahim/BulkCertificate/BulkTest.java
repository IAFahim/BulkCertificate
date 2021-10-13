package github.com.IAFahim.BulkCertificate;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class BulkTest {

    @Test
    void read_CSV_Make_Image_Create_CSV() {
        Bulk bulk=new Bulk();
        bulk.Read_CSV_Make_Image_Create_CSV("C:\\Users\\Fahim\\IdeaProjects\\BulkCertificateACM\\src\\test\\resources\\Certificate List - final.csv");
    }
}