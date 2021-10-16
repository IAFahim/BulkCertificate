package github.com.IAFahim.BulkCertificate;

import java.util.HashMap;

public class IDIndexAtMap {
    StyleIndexAt styleIndexAt;
    public HashMap<String, Integer> map;

    public IDIndexAtMap(StyleIndexAt styleIndexAt) {
        this.styleIndexAt=styleIndexAt;
        map=new HashMap<>();
    }
}
