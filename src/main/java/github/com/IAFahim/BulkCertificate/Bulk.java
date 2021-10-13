package github.com.IAFahim.BulkCertificate;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;

public class Bulk {

    public LinkedHashMap<String,Style> Read_style(String styleCSVPath){
        Iterable<CSVRecord> csv_style= cSVReadAll(styleCSVPath);
        LinkedHashMap<String,Style> data = new LinkedHashMap<>();
        int[] arr=null;
        int y=0;
        for(var d: csv_style){
            if(y==0){
                arr=new int[d.size()];
            }else {

            }
            y++;
        }
        return data;
    }

    public void repairedCSVForReadData(LinkedHashSet<String> set, String dataCSVPath) {
        Iterable<CSVRecord> csv_data = cSVReadAll(dataCSVPath);
        int[] arr=null,loop=null,capitalize=null;
        int y=0;
        for (var d : csv_data) {
            if(y==0){
                arr=new int[d.size()];
                capitalize=new int[d.size()];
                loop=new int[set.size()];
                for (int i = 0,j=0; i < d.size(); i++) {
                    String str= formatOrStrip(d.get(i));
                    if(set.contains(str)){
                        loop[j++]=i;
                        arr[i]=1;
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
    private void specialCharacter(){
        specialChar=new boolean[128];
        specialChar['^']=true;
        specialChar['*']=true;
        specialChar['?']=true;
    }

    private String formatOrStrip(String str){
        if(str.length()>0){
            if(specialChar[str.charAt(0)]){
                return str.substring(1).toUpperCase();
            }
        }
        return str;
    }

    Bulk(){
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
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csv_data;
    }
}


