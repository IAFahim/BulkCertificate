package github.com.IAFahim.BulkCertificate;

import java.awt.*;
import java.util.Locale;

public class Style {

    public int x;
    public int y;
    public double ax = .5;
    public double ay = .5;
    public String font = "Arial";
    public int fontStyle;
    public int fontSize = 11;
    public String fontColor;
    public double fontOpacity = 1;

    public Dimension picture;

    public void set(String key, String val) {
        key = key.toLowerCase();
        switch (key) {
            case "x" -> this.x = setPosition(val);
            case "y" -> this.y = setPosition(val);
            case "ax" -> this.ax = Double.parseDouble(val);
            case "ay" -> this.ay = Double.parseDouble(val);
            case "font" -> this.font = val;
            case "font style" -> this.fontStyle = setFontStyle(val);
            case "font size" -> this.fontSize = setFontSize(val);
            case "font color" -> this.fontColor = setFontColor(val);
            case "font opacity" -> this.fontOpacity = setFontOpacity(val);
        }
    }

    public static String getTitle(){
        return "Data,x,y,ax,ay,font,font style,font size,font color,font opacity\n";
    }

    public  String toString(){
        return x+","+y+","+ax+","+ay+","+font+","+fontStyle+","+fontSize+","+fontColor+","+fontOpacity+"\n";
    }

    private int setPosition(String val) {
        double x = Double.parseDouble(val);
        if (x < 1 && 0 < 1 -x) {
            return (int) (picture.width * x);
        }
        return (int) x;
    }


    private int setFontSize(String val) {
        double x = Double.parseDouble(val);
        return (int) x;
    }

    private int setFontStyle(String val) {
        val = val.toUpperCase();
        boolean bold = val.contains("BOLD");
        boolean italic = val.contains("ITALIC");
        int font = 0;
        if (bold & italic) {
            font = Font.BOLD | Font.ITALIC;
        } else {
            if (bold) {
                font = Font.BOLD;
            } else if (italic) {
                font = Font.ITALIC;
            }
        }
        return font;
    }

    private String setFontColor(String val) {
        if (val.charAt(0) != '#') {
            return "#" + val;
        }
        return val;
    }

    private int setFontOpacity(String val) {
        double x = Double.parseDouble(val);
        return (int) x;
    }

}
