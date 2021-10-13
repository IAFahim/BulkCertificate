package github.com.IAFahim.BulkCertificate;

import java.awt.*;
import java.util.Locale;

public class Style {

    public int x;
    public int y;
    public int ax;
    public int ay;
    public String font;
    public int fontStyle;
    public int fontSize;
    public String fontColor;
    public double fontOpacity;

    public Dimension picture;

    public void set(String key, String val) {
        key = key.toLowerCase();
        switch (key) {
            case "x" -> this.x = setPosition(val);
            case "y" -> this.y = setPosition(val);
            case "ax" -> this.ax = setAnchor(val);
            case "ay" -> this.ay = setAnchor(val);
            case "font" -> this.font = key;
            case "font style" -> this.fontStyle = setFontStyle(val);
            case "font size" -> this.font = key;
            case "font color" -> this.font = key;
            case "font opacity" -> this.font = key;
        }
    }


    private int setPosition(String val) {
        double x=Double.parseDouble(val);
        if (x < 1) {
            return (int) (picture.width * x);
        }
        return (int) x;
    }

    private int setAnchor(String val) {
        double x=Double.parseDouble(val);
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
}
