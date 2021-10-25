package github.com.IAFahim.BulkCertificate;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bulk {

    public String currentPath;
    public String currentFolder;
    public String folderToPopulate;
    public BufferedImage bufferedImage;
    public String imgType;

    public Bulk(Boolean testMode, File file) throws IOException {
        int index = file.getName().lastIndexOf('.');
        if (index > 0) {
            imgType = file.getName().substring(0, index);
        } else {
            imgType = "png";
        }
        bufferedImage = ImageIO.read(file);
        currentPath = System.getProperty("user.dir");
        if (testMode) {
            currentFolder = "Test";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE d MMM yyyy HH-mm-ss aaa");
            currentFolder = dateFormat.format(new Date());
        }
        folderToPopulate = currentPath + "\\" + currentFolder + "\\";
        System.out.println(folderToPopulate);
        File folder = new File(folderToPopulate);
        if (!folder.mkdir()) {
            System.err.println("folder exists");
        }
    }

    public static void main(String[] args) {

    }


    public LinkedHashMap<String, Style> readStyle(String styleCSVPath) {
        Iterable<CSVRecord> csv_style = cSVReadAll(styleCSVPath);
        ArrayList<Pair> head = new ArrayList<>();
        LinkedHashMap<String, Style> data = new LinkedHashMap<>();
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(folderToPopulate + "StyleNew.csv");
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

    public void repairCSVForReadData(LinkedHashMap<String, Style> map, String dataCSVPath) {
        Iterable<CSVRecord> csv_data = cSVReadAll(dataCSVPath);
        int y = -1;
        ArrayList<StyleIndexAt> styles = null;
        ArrayList<IDIndexAtMap> ids = null;
        String[] store = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(folderToPopulate + "Data.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (fileOutputStream != null) {
            try (DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(fileOutputStream))) {
                ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                for (var d : csv_data) {
                    if (y == -1) {
                        styles = new ArrayList<>();
                        ids = new ArrayList<>();
                        store = new String[d.size()];
                        for (int i = 0; i < d.size(); i++) {
                            String str = d.get(i);
                            if (str.length() > 0) {
                                if (str.charAt(0) == '*') {
                                    str = str.substring(1);
                                    Style style = map.get(str);
                                    if (style == null) continue;
                                    ids.add(new IDIndexAtMap(new StyleIndexAt(style, i)));
                                } else {
                                    Style style = map.get(str);
                                    if (style == null) continue;
                                    styles.add(new StyleIndexAt(style, i));
                                }
                                store[i] = str;
                            }
                        }
                        cSVPrintArray(dataOutputStream, store);
                    } else {
                        PrintData printData = new PrintData();
                        printData.string = new String[store.length];
                        printData.style = new Style[store.length];
                        for (int i = 0; i < styles.size(); i++) {
                            int x = styles.get(i).x;
                            String str = d.get(x);
                            if (str.length() > 0) {
                                store[x] = str;
                            } else {
                                str = store[x];
                            }
                            printData.string[x] = str;
                        }
                        for (int i = 0; i < ids.size(); i++) {
                            int x = ids.get(i).styleIndexAt.x;
                            String str = d.get(x);
                            if (str.length() == 0) {
                                str = store[x];
                            } else {
                                store[x] = str;
                            }
                            Integer count = ids.get(i).map.get(str);
                            if (count != null) {
                                str = String.format(str, ++count);
                            } else {
                                int startVal = 1;
                                ids.get(i).map.put(str, startVal);
                                str = String.format(str, startVal);
                            }
                            printData.string[x] = str;
                            printData.fileName = printData.string[x];
                            GenerateImage generateImage = new GenerateImage(bufferedImage, printData, imgType, folderToPopulate);
                            service.execute(generateImage);
                        }
                        cSVPrintArray(dataOutputStream, printData.string);
                    }
                    y++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void cSVPrintArray(DataOutputStream dataOutputStream, String[] strings) {
        int n = strings.length;
        for (int i = 0; i < n; i++) {
            try {
                dataOutputStream.writeBytes(strings[i] + ((i + 1 != n) ? "," : "\n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void cSVPrintArray(DataOutputStream dataOutputStream, CSVRecord rec) {
        int n = rec.size();
        for (int i = 0; i < n; i++) {
            try {
                dataOutputStream.writeBytes(rec.get(i) + ((i + 1 != n) ? "," : "\n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        return csv_data;
    }
}


