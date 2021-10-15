package github.com.IAFahim.BulkCertificate;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Bulk implements Runnable {

    public static void main(String[] args) {
        new Bulk(true).run();
    }


    public LinkedHashMap<String, Style> readStyle(String styleCSVPath) {
        Iterable<CSVRecord> csv_style = cSVReadAll(styleCSVPath);
        ArrayList<Pair> head = new ArrayList<>();
        LinkedHashMap<String, Style> data = new LinkedHashMap<>();
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(pathToPopulate + "StyleNew.csv");
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
                        dataOutputStream.writeBytes(key + "," + style.toString());
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

    @Override
    public void run() {

    }


    public void repairCSVForReadData(LinkedHashMap<String, Style> map, String dataCSVPath) {
        Iterable<CSVRecord> csv_data = cSVReadAll(dataCSVPath);
        int y = -1;
        ArrayList<StyleIndexAt> styles = null;
        ArrayList<IDIndexAtMap> ids = null;
        String[] arr=null;
        for (var d : csv_data) {
            if (y == -1) {
                styles = new ArrayList<>();
                ids=new ArrayList<>();
                for (int i = 0; i < d.size(); i++) {
                    String str = d.get(i);
                    if (str.length() > 0) {
                        if(str.charAt(0)=='*'){
                            str=str.substring(1);
                            ids.add(new IDIndexAtMap(i,1));
                        }
                        Style style = map.get(str);
                        styles.add(new StyleIndexAt(style, i, 1));
                    }
                }
            }else{
                arr=new String[styles.size()+ ids.size()];
                for (int i = 0; i < styles.size(); i++) {
                    int index=styles.get(i).index;
                    arr[index]=d.get(index);
                    System.out.println(styles.get(i).style.toString()+" "+arr[index]);
                }
            }
            y++;
        }
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

    public String currentPath;
    public String currentFolder;
    public String pathToPopulate;
    public BufferedImage bufferedImage;

    public Bulk(Boolean testMode) {
        currentPath = System.getProperty("user.dir");
        if (testMode){
            currentFolder = "Test";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE d MMM yyyy HH-mm-ss aaa");
            currentFolder = dateFormat.format(new Date());
        }
        pathToPopulate = currentPath + "/" + currentFolder + "/";
        System.out.println(pathToPopulate);
        File file = new File(pathToPopulate);
        if (!file.mkdir()) {
            System.err.println("folder exists");
        }
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


