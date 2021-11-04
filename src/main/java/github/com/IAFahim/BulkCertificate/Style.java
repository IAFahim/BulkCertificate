package github.com.IAFahim.BulkCertificate;

import java.awt.*;

public class Style {

    public int x;
    public int y;
    public double ax = .5;
    public double ay = .5;
    public String font = "Arial";
    public int fontStyleNumber;
    public String fontStyle = "Regular";
    public int fontSize = 16;
    public Color fontColor = Color.BLACK;
    public double fontOpacity = 1;
    public double stretchedFontFromIllustrator;

    public DimensionDouble measuredDimension;

    public void set(String key, String val) {
        key = key.toLowerCase();
        switch (key) {
            case "x" -> this.x = setPosition(val,measuredDimension.widthMultiple);
            case "y" -> this.y = setPosition(val,measuredDimension.heightMultiple);
            case "ax" -> this.ax = Double.parseDouble(val);
            case "ay" -> this.ay = Double.parseDouble(val);
            case "font" -> this.font = val;
            case "font style" -> {
                this.fontStyleNumber = setFontStyle(val);
                fontStyle = val;
            }

            case "font size" -> this.fontSize = setFontSize(val);
            case "font color" -> this.fontColor = setFontColor(val);
            case "font opacity" -> this.fontOpacity = setFontOpacity(val);
            case "stretched font" -> this.stretchedFontFromIllustrator = (val.equals("1")?1:0);
        }
    }

    public static String getTitle() {
        return "Data,x,y,ax,ay,font,font style,font size,font color,font opacity,stretched font\n";
    }

    public String toString() {
        return x + "," + y + "," + ax + "," + ay + "," + font + "," + fontStyle + "," + fontSize + "," + Integer.toHexString(fontColor.getRGB()) + "," + fontOpacity + "," + stretchedFontFromIllustrator + "\n";
    }

    private int setPosition(String val, double heightOrWidth) {
        double x = Double.parseDouble(val)* heightOrWidth;
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

    private Color setFontColor(String val) {
        if (val.charAt(0) != '#') {
            return Color.decode("#" + val);
        }
        return Color.decode(val);
    }

    private int setFontOpacity(String val) {
        double x = Double.parseDouble(val);
        fontColor = new Color(fontColor.getRed(), fontColor.getGreen(), fontColor.getBlue(), (int) (x * 0xff));
        return (int) x;
    }

}
