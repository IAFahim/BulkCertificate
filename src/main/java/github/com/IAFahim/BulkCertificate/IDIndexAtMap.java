package github.com.IAFahim.BulkCertificate;

import java.util.HashMap;

public class IDIndexAtMap {
    public int index;
    public int at;
    public HashMap<String, Integer> map;

    public IDIndexAtMap(int index, int at) {
        this.index = index;
        this.at = at;
        map=new HashMap<>();
    }
}
