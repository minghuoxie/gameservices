package media.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SetBgColor {

    /**
     * 生成图片
     * /


    /**
     * 将白色的背景设置为透明
     * */
    public static int colorRange=0;
    public static void createImg(String saveFilePath){
        int width=1200;
        int hei=1195;
        BufferedImage bufImg=new BufferedImage(width,hei,BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d=(Graphics2D)bufImg.getGraphics();//获取画笔
       // Font font = new Font("宋体", 12, Font.PLAIN);

        g2d.setColor(Color.RED);
        g2d.fillOval(340,10,32,32);
        //
        g2d.fillRect(320,85,108,40);

        g2d.setColor(Color.BLUE);
        g2d.fillRect(325,85+20-6,12,12);
       // g2d.drawString("好啊哥哥官方官",320,(85+12+20-8)); 14*6+12+10+2
        g2d.drawString("jingershengbao",338,(85+12+20-8));

        g2d.setColor(Color.RED);
        g2d.fillOval(345,175,32,32);


        //"55.0" width="85.0" x="100.0" y="290.0"/>
        g2d.fillRect(100,290,85,55);

        //height="55.0" width="85.0" x="305.0" y="290.0"/>
        g2d.fillRect(305,290,85,55);

        //height="55.0" width="85.0" x="465.0" y="295.0"/>
        g2d.fillRect(465,295,480,55);
        g2d.setColor(Color.BLUE);
        g2d.drawString("ahskjdsdfdfgdfgfghsdfsdffgdfgasdfasdfdgfdfgsdfasdfasdsdfsdfasdasfdfsdfasdasdjksd",470,310);

        g2d.setColor(Color.RED);

        //height="32.0" width="32.0" x="330.0" y="445.0"/>
        g2d.fillOval(330,445,32,32);


        g2d.drawImage(bufImg,0,0,null);
        try {
            ImageIO.write(bufImg,"png",new File(saveFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setBgColor(int scolorRange,String filePath,String newFilePath){
        colorRange=scolorRange;
        try {
            BufferedImage bufImage= ImageIO.read(new File(filePath));
            Graphics2D g2d=(Graphics2D)bufImage.getGraphics();//获取画笔
            for(int x=bufImage.getMinX();x<bufImage.getWidth();x++){
                for(int y=bufImage.getMinY();y<bufImage.getHeight();y++){
                    int rgb=bufImage.getRGB(x,y);
                    if(colorInRange(rgb)){
                        System.out.println(colorRange+"=="+rgb);
                        bufImage.setRGB(x,y,rgb&0x00FFFFFF);
                    }
                }
            }
            g2d.drawImage(bufImage,0,0,null);
            ImageIO.write(bufImage,"png",new File(newFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setImage(String filePath){
        Random rand = new Random();
        int apa=255;
        try{
            int w=400;
            int h=300;
            int arr[]=new int[3];
            BufferedImage bufferedImage=new BufferedImage(w,h,BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2d=(Graphics2D)bufferedImage.getGraphics();
            for(int y=0;y<bufferedImage.getHeight();y++){
                for(int x=0;x< bufferedImage.getWidth();x++){
                    if(x>=100&&x<=300&&y>=100&&y<=200){
                        arr[0]=200;
                        arr[1]=200;
                        arr[2]=300;
                    }else{
                        arr[0]=rand.nextInt(255);//[0-255)
                        arr[1]=rand.nextInt(255);
                        arr[2]=rand.nextInt(255);
                    }
                    int rgb=(apa<<24)|(arr[0]<<16)+(arr[1]<<8)+arr[2];
                    bufferedImage.setRGB(x,y,rgb);
                }
            }
            g2d.drawImage(bufferedImage,0,0,null);
            ImageIO.write(bufferedImage,"jpg",new File(filePath));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void setBgColort(int scolorRange,String filePath,String newFilePath){
        colorRange=scolorRange;
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            // 高度和宽度
            int height = image.getHeight();
            int width = image.getWidth();

            // 生产背景透明和内容透明的图片
            ImageIcon imageIcon = new ImageIcon(image);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics(); // 获取画笔
            g2D.drawImage(imageIcon.getImage(), 0, 0, null); // 绘制Image的图片
            int alpha = 0; // 图片透明度
            // 外层遍历是Y轴的像素
            for (int y = bufferedImage.getMinY(); y < bufferedImage.getHeight(); y++) {
                // 内层遍历是X轴的像素
                for (int x = bufferedImage.getMinX(); x < bufferedImage.getWidth(); x++) {
                    int rgb = bufferedImage.getRGB(x, y);
                    // 对当前颜色判断是否在指定区间内
                    if (colorInRange(rgb)){
                        alpha = 0;
                    }else{
                        // 设置为不透明
                        alpha = 255;
                    }
                    // #AARRGGBB 最前两位为透明度
                    rgb = (alpha << 24) | (rgb & 0x00ffffff);
                    bufferedImage.setRGB(x, y, rgb);
                }
            }

            // 绘制设置了RGB的新图片
            g2D.drawImage(bufferedImage, 0, 0, null);
            ImageIO.write(bufferedImage, "png", new File(newFilePath));
           System.out.println("完成画图");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static boolean colorInRange(int color){
        int red=(color&0xFF0000)>>16;
        int green=(color&0x00FF00)>>8;
        int blue=(color&0x0000FF);
        if(red>=colorRange&&green>=colorRange&&blue>=colorRange){
            return true;
        }
        return false;
    }
}
