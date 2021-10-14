package github.com.IAFahim.BulkCertificate;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.function.Predicate;

public class Bulk {

    public static void main(String[] args) {
        Bulk bulk = new Bulk();
        var map = bulk.readStyle("src/test/resources/Certificate List - Format.csv");
        bulk.repairCSVForReadData(map, "src/test/resources/Certificate List - final.csv");
    }

    class Tuple {
        String key;
        String pairedWith;
    }

    public LinkedHashMap<String, Style> readStyle(String styleCSVPath) {
        Iterable<CSVRecord> csv_style = cSVReadAll(styleCSVPath);
        ArrayList<Pair> head = new ArrayList<>();
        LinkedHashMap<String, Style> data = new LinkedHashMap<>();
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("StyleNew.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fileOutputStream != null) {
            try (DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(fileOutputStream))) {
                int y = -1;
                for (var d : csv_style) {
                    if (y == -1) {
                        for (int i = 0; i < d.size(); i++) {
                            String str = d.get(i);
                            head.add(new Pair(str, null));
                        }
                        dataOutputStream.writeBytes(Style.getTitle());
                    } else {
                        String key = d.get(0);
                        Style style = new Style();
                        for (int i = 1; i < d.size(); i++) {
                            String val = d.get(i);
                            if (val.length() > 0) {
                                style.set(head.get(i).head, val);
                                head.get(i).store = val;
                            } else {
                                Pair pair = head.get(i);
                                style.set(pair.head, pair.store);
                                head.get(i).store = pair.store;
                            }
                        }
                        dataOutputStream.writeBytes(key+","+style.toString());
                        data.put(key, style);
                    }

                    y++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public void cSVPrintArray(DataOutputStream dataOutputStream, CSVRecord rec) {
        int n = rec.size();
        for (int i = 0; i < n; i++) {
            try {
                dataOutputStream.writeBytes(rec.get(i) + ((i + 1 != n) ? "," : "\n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void repairCSVForReadData(LinkedHashMap<String, Style> set, String dataCSVPath) {
        Iterable<CSVRecord> csv_data = cSVReadAll(dataCSVPath);
        int y = 0;
        for (var d : csv_data) {
            if (y == 0) {
                for (int i = 0, j = 0; i < d.size(); i++) {
                    String str = formatOrStrip(d.get(i));
                    if (set.get(str) != null) {

                    }
                }
            }
            for (int x = 0; x < d.size(); x++) {
                System.out.println(d.get(x));
            }
            y++;
        }
    }

    private HashMap<String, Style> stylesSupported;

    private boolean[] specialChar;

    private void specialCharacter() {
        specialChar = new boolean[128];
        specialChar['^'] = true;
        specialChar['*'] = true;
        specialChar['?'] = true;
    }

    private String formatOrStrip(String str) {
        if (str.length() > 0) {
            if (specialChar[str.charAt(0)]) {
                return str.substring(1).toUpperCase();
            }
        }
        return str;
    }

    Bulk() {
        specialCharacter();
    }

    private Iterable<CSVRecord> cSVReadAll(String dataCSVPath) {
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
//        try {
//            if (reader != null) {
//                reader.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return csv_data;
    }
}


