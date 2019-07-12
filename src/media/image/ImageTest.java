package media.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageTest {
//    // https://cloud.tencent.com/developer/article/1169721
//    public static void main(String[] args){
//        File imageFile = new File("D:/Temp/test.png");
//        ITesseract instance = new Tesseract();
//        instance.setDatapath("D:\\down\\d\\Tess4J-3.4.8-src\\Tess4J\\tessdata");
//        try {
//            String result = instance.doOCR(imageFile);
//            System.out.println(result);
//        } catch (TesseractException e) {
//            System.err.println(e.getMessage());
//        }
//    }


    public static void readFile(String filePath){

        //读取的文件大小
        try {
            /**
            FileInputStream input=new FileInputStream(new File(filePath));
            byte buf[]=new byte[1024*10];
            int len=0;
            while((len=input.read(buf))!=-1){
                System.out.println("读取的长度:"+len);
                for(int i=0;i<len;i++){
                    System.out.print(buf[i]+" ");
                }
                buf=new byte[1024*10];
            }
            System.out.println("最后的长度:"+len);
            input.close();
             */

            BufferedImage bufImage=ImageIO.read(new File(filePath));
            int imageW=bufImage.getWidth();
            int imageH=bufImage.getHeight();
            int imageMinX=bufImage.getMinX();
            int imageMinY=bufImage.getMinY();
            System.out.println(imageMinX+":--图片的宽度:"+imageW);
            System.out.println(imageMinY+":--图片的宽度:"+imageH);
            for(int x=imageMinX;x<imageW;x++){
                for(int y=imageMinY;y<imageH/2;y++){
                    int rgb=bufImage.getRGB(x,y);
                    int nr=255-(rgb&0xFF0000)>>16;
                    int ng=255-(rgb&0xFF00)>>8;
                    int nb=255-rgb&0xFF;
                    int nrgb=nr<<16|ng<<8|nb;
                   // bufImage.setRGB(x,y,nrgb); //将最高字节设置为0，，设置背景为透明
                    bufImage.setRGB(x,y,rgb&0x00FFFFFF);
                }
            }
           // ImageIO.write(bufImage,"jpg",new File("D:\\Temp\\img\\newlxyone.jpg"));
            ImageIO.write(bufImage,"jpg",new File("D:\\Temp\\img\\tnewlxyone.jpg"));
//            int rgb=-15195862;
//            int b=rgb&0xFF;
//            int g=(rgb&0xFF00)>>8;
//            int r=(rgb&0xFF0000)>>16;
//            int t=(rgb&0xFF000000)>>24;
//            System.out.println("b:"+b+"   g::"+g+"     r:"+r+"   t:"+t);
//            int nrgb=(0<<24)+(24<<16)+(33<<8)+42;
//            System.out.println("newRgb"+nrgb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
