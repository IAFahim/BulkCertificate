package github.com.IAFahim.BulkCertificate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {

    }

    private static File WriteImage(BufferedImage bufferedImage, String path, String type){
        File file=null;
        try {
            ImageIO.write(bufferedImage,type,file= new File(path+"/img.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private static BufferedImage createImage(){
        BufferedImage bufferedImage=new BufferedImage(1000,1000, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                bufferedImage.setRGB(x,y,0xff_ff_ff_ff);
            }
        }
        return bufferedImage;
    }

    public BufferedImage bufferedImage;
    public Graphics2D canvas;
    public Print print;
    public void create(){
        canvas = (Graphics2D) bufferedImage.getGraphics();
        for (int i = 0; i < print.string.length; i++) {
            addTextToImage(i,print);
        }
        canvas.dispose();
        WriteImage(bufferedImage,"/sda","png");
    }

    public void addTextToImage(int i,Print print){
        canvas.setFont(new Font(print.style[i].font,print.style[i].fontStyle,print.style[i].fontSize));
        canvas.setColor(print.style[i].fontColor);
        TextLayout textLayout=new TextLayout(print.string[i],canvas.getFont(),canvas.getFontRenderContext());
        Rectangle2D bounds= textLayout.getBounds();
        canvas.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        canvas.drawString(print.string[i],(int) (print.style[i].x - (bounds.getBounds().getWidth()* print.style[i].ax)), (int) (print.style[i].y + (bounds.getHeight()* print.style[i].ay)));
        canvas.setColor(Color.BLUE);
    }
}
