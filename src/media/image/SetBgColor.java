package media.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class SetBgColor {
    /**
     * 将白色的背景设置为透明
     * */
    public static int colorRange=0;
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
    private static String readTxtFile(String filePath){
        try {
            BufferedReader reader=new BufferedReader(new FileReader(filePath));
            StringBuffer buffer=new StringBuffer();
            String line="";
            while((line=reader.readLine())!=null){
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    private static List<String> readTextFiles(String filePath){
        String str=readTxtFile(filePath).trim();
        if(str.length()>0){
            List<String> list=new ArrayList<>();
            String linStr="";
            for(int i=0;i<str.length();i++){
                char ch=str.charAt(i);
                if(ch==' '){
                    list.add(linStr);
                    linStr="";
                }
                linStr+=ch;
            }
            return list;
        }
        return null;
    }
    public static void setImage(String testImageFile,String fileImage,String saveFilePath){
        Random rand = new Random();
        try{
            List<String> ListStr=readTextFiles(fileImage);
            if(ListStr!=null&&ListStr.size()>0) {
                int index=0;
                BufferedImage image = ImageIO.read(new File(testImageFile));
                int width = image.getWidth();
                int height = image.getHeight();
                int arr[] = new int[3];
                // BufferedImage.TYPE_3BYTE_BGR
                BufferedImage writeImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D g2d = (Graphics2D) writeImage.getGraphics();
                for (int y = 0; y < writeImage.getHeight(); y++) {
                    for (int x = 0; x < writeImage.getWidth(); x++,index++) {
//                        if (x >= 100 && x <= 300 && y >= 100 && y <= 200) {
//                            arr[0] = 200;
//                            arr[1] = 200;
//                            arr[2] = 300;
//                        } else {
//                            arr[0] = rand.nextInt(255);//[0-255)
//                            arr[1] = rand.nextInt(255);
//                            arr[2] = rand.nextInt(255);
//                        }
                        if(index<ListStr.size()) {
                            //int rgb = (255 << 24) | (arr[0] << 16) + (arr[1] << 8) + arr[2];
                            int rgb=(Integer.parseInt(ListStr.get(index).trim()));
                            writeImage.setRGB(x, y, rgb);
                        }
                    }
                }
                g2d.drawImage(writeImage, 0, 0, null);
                ImageIO.write(writeImage, "jpg", new File(saveFilePath));
            }
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
    public static void readImage(String filePath){
        try{
            BufferedImage image=ImageIO.read(new File(filePath));
            StringBuffer buffer=new StringBuffer();
            for(int y=image.getMinY();y<image.getHeight();y++){
                for(int x=image.getMinX();x<image.getWidth();x++){
                    int rgb=image.getRGB(x,y);
                    buffer.append(rgb+" ");
                }
            }
            BufferedWriter writer=new BufferedWriter(new FileWriter("D:/Temp/img/lxyonecolor.txt"));
            writer.write(buffer.toString());
            writer.close();
        }catch (Exception e){
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
