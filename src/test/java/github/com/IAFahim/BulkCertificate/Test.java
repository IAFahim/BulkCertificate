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
        System.out.println(WriteImage(addTextToImage(createImage()),"png","Test/img.png"));
    }

    private static File WriteImage(BufferedImage bufferedImage, String path, String type){
        File file=null;
        try {
            ImageIO.write(bufferedImage,"png",file= new File("Test/img.png"));
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

    public static BufferedImage addTextToImage(BufferedImage bufferedImage){
        Style style=new Style();
        style.x=500;
        style.y=500;
        style.fontSize=100;
        Graphics2D canvas = (Graphics2D) bufferedImage.getGraphics();
        canvas.setFont(new Font(style.font,style.fontStyle,style.fontSize));
        canvas.setColor(style.fontColor);
        String text="Bulk Certificate";
        TextLayout textLayout=new TextLayout(text,canvas.getFont(),canvas.getFontRenderContext());
        Rectangle2D bounds= textLayout.getBounds();
        canvas.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        canvas.drawString(text,(int) (style.x - (bounds.getBounds().getWidth()* style.ax)), (int) (style.y + (bounds.getHeight()* style.ay)));
        canvas.setColor(Color.BLUE);

        canvas.dispose();
        return bufferedImage;
    }
}
