package github.com.IAFahim.BulkCertificate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class GenerateImage implements Runnable{
    private BufferedImage bufferedImage;
    private Graphics2D canvas;
    private final PrintData printData;
    private final String folderToPopulate;
    private final String type;

    public GenerateImage(BufferedImage bufferedImage, PrintData printData, String type, String folderToPopulate){
        this.bufferedImage=bufferedImage;
        this.printData =printData;
        this.type=type;
        this.folderToPopulate=folderToPopulate;
        if(printData.fileName==null){
            printData.fileName= printData.string[0];
        }
        printData.fileName+='.'+type;
    }

    private static void writeImage(BufferedImage bufferedImage, String path, String type){
        try {
            ImageIO.write(bufferedImage,type,new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage createImage(){
        BufferedImage bufferedImage=new BufferedImage(1000,1000, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                bufferedImage.setRGB(x,y,0xff_ff_ff_ff);
            }
        }
        return bufferedImage;
    }

    private void addTextToImage(int i, PrintData print){
        canvas.setFont(new Font(print.style[i].font,print.style[i].fontStyleNumber,print.style[i].fontSize));
        canvas.setColor(print.style[i].fontColor);
        TextLayout textLayout=new TextLayout(print.string[i],canvas.getFont(),canvas.getFontRenderContext());
        Rectangle2D bounds= textLayout.getBounds();
        canvas.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        canvas.drawString(print.string[i],(int) (print.style[i].x - (bounds.getBounds().getWidth()* print.style[i].ax)), (int) (print.style[i].y ));
        canvas.setColor(Color.BLUE);
    }

    public static BufferedImage copyImage(BufferedImage source) {
        BufferedImage bi = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        byte[] sourceData = ((DataBufferByte) source.getRaster().getDataBuffer()).getData();
        byte[] biData = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        System.arraycopy(sourceData, 0, biData, 0, sourceData.length);
        return bi;
    }

    @Override
    public void run() {
        this.bufferedImage=copyImage(bufferedImage);
        canvas = (Graphics2D) bufferedImage.getGraphics();
        for (int i = 0; i < printData.string.length; i++) {
            addTextToImage(i, printData);
        }
        canvas.dispose();

        writeImage(bufferedImage, folderToPopulate+ printData.fileName, type);
    }
}
