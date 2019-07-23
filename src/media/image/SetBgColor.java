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
    public static void changeImageBig(String filePath,String saveFile){
        try{
            BufferedImage image=ImageIO.read(new File(filePath));
            int min=image.getWidth()>image.getHeight()?image.getHeight():image.getWidth();
            BufferedImage newImage=new BufferedImage(min,min,BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D g2d=(Graphics2D)newImage.getGraphics();
            for(int y=image.getMinY();y<min;y++){
                for(int x=image.getMinX();x<min;x++){
                    //int rgb=(255<<16)|(255<<8)|255;
                    int rgb=image.getRGB(x,y);
                    newImage.setRGB(x,y,rgb);
                }
            }
            g2d.drawImage(newImage, 0, 0, null);
            ImageIO.write(newImage,"jpg",new File(saveFile));
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


    //https://www.cnblogs.com/leaven/archive/2010/04/06/1705846.html
    //http://blog.sina.com.cn/s/blog_7445c2940102wcdl.html   DCT?????????

    //https://blog.csdn.net/luoweifu/article/details/8214959
    /**
     * RGB颜色空间模型和YCbCr空间模型的转换
     *
     * R=Y+1.402(Cr-128)
     * G=Y-0.34414(Cb-128)-0.71414(Cr-128)
     * B=Y+1.772(Cb-128)
     *
     *
     * */
    public static void yuvToRGB(String yueFilePath,String rgbFilePath){
        try{
            BufferedImage image=ImageIO.read(new File(yueFilePath));
            for(int y=image.getMinY();y<image.getHeight();y++){
                for(int x=image.getMinX();x<image.getWidth();x++){
                    int yuv=image.getRGB(x,y);
                    int Y=(yuv&0xFF0000)>>16;
                    int Cb=(yuv&0x00FF00)>>8;
                    int Cr=yuv&0x0000FF;

                    int R=(int)(Y+1.402*(Cr-128));
                    int G=(int)(Y-0.34414*(Cb-128)-0.71414*(Cr-128));
                    int B=(int)(Y+1.772*(Cb-128));
                    int newRgb=(R<<16)|(G<<8)|B;
                    image.setRGB(x,y,newRgb);
                }
            }
            ImageIO.write(image,"jpg",new File(rgbFilePath));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Y=0.299R+0.587G+0.114B
     * Cb=-0.1687R-0.3313G+0.5B+128
     * Cr=0.5R-0.4187G-0.0813B+128
     *
     *
     * */
    public static void rgbToYUV(String rgbFilePath,String yuvFilePath){
        try{
            BufferedImage image=ImageIO.read(new File(rgbFilePath));
            for(int y=image.getMinY();y<image.getHeight();y++){
                for(int x=image.getMinX();x<image.getWidth();x++){
                    int rgb=image.getRGB(x,y);
                    int r=(rgb&0xFF0000)>>16;
                    int g=(rgb&0x00FF00)>>8;
                    int b=rgb&0x0000FF;

                    int Y=(int)(0.299*r+0.587*g+0.114*b);
                    int Cb=(int)(-0.1687*r-0.3313*g+0.5*b+128);
                    int Cr=(int)(0.5*r-0.4187*g-0.0813*b+128);
                    int newRgb=(Y<<16)|(Cb<<8)|Cr;
                    image.setRGB(x,y,newRgb);
                }
            }
            ImageIO.write(image,"jpg",new File(yuvFilePath));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static int imgMin=0;
    public static void dctSaveImg(String filePath,String savePath) throws IOException {
        int[] rgbs=dctEach(filePath,'n','y');
        BufferedImage img=new BufferedImage(imgMin,imgMin,BufferedImage.TYPE_3BYTE_BGR);
        for(int y=0;y<imgMin;y++){
            for(int x=0;x<imgMin;x++){
                img.setRGB(x,y,rgbs[y*imgMin+x]);
            }
        }
        ImageIO.write(img,"jpg",new File(savePath));
    }
    /**
     * 求图片的DCT变换
     *
     * */
    public static int[] dctEach(String filePath,char type,char save) throws IOException {
        int[] rgbs=getRGBS(filePath);
        if(save=='y'){
            txtSave(rgbs,"D:/Temp/img/img20190717/changeOnelxyone.txt");
        }
        double[][] iMatrix=new double[imgMin][imgMin];
        //将原图的一维矩阵转换为二维矩阵
        for(int i=0;i<imgMin;i++){
            for(int j=0;j<imgMin;j++){
                iMatrix[i][j]=(double)(rgbs[i*imgMin+j]);
            }
        }
        for(int i=0;i<rgbs.length;i++){
            System.out.print(rgbs[i]+" ");
            if(i%imgMin==0&&i!=0){
                System.out.println();
            }
        }
        System.out.println("\r\n-------------------------------------------------------------------");
        double[][] quotient=coefficient();//求系数矩阵
        double[][] quotientt=transposingMatrix(quotient);//转置矩阵
        double[][] temp=matrixMultiply(quotient,iMatrix);
        iMatrix=matrixMultiply(temp,quotientt);
        int[] newpix=new int[imgMin*imgMin];
        if(type=='y'){
            temp=matrixMultiply(quotientt,iMatrix);
            iMatrix=matrixMultiply(temp,quotient);
        }
        for(int i=0;i<imgMin;i++){
            for(int j=0;j<imgMin;j++){
                newpix[i*imgMin+j]=(int)iMatrix[i][j];
            }
        }
        for(int i=0;i<newpix.length;i++){
            System.out.print(newpix[i]+" ");
            if(i%imgMin==0&&i!=0){
                System.out.println();
            }
        }
        if(save=='y'){
            txtSave(newpix,"D:/Temp/img/img20190717/dctlxyone.txt");
        }
        return newpix;

    }

    //矩阵相乘
    private static double[][] matrixMultiply(double[][] A,double[][] B){
        double[][] nMatrix=new double[imgMin][imgMin];
        double t=0.0;
        for(int i=0;i<imgMin;i++){
            for(int j=0;j<imgMin;j++){
                t=0;
                for(int k=0;k<imgMin;k++){
                    t+=A[i][k]*B[k][j];
                }
                nMatrix[i][j]=t;
            }
        }
        return nMatrix;
    }

    //转置矩阵
    private static double[][] transposingMatrix(double[][] matrix){
        double[][] nMatrix=new double[imgMin][imgMin];
        for(int i=0;i<imgMin;i++){
            for(int j=0;j<imgMin;j++){
                nMatrix[i][j]=matrix[j][i];
            }
        }
        return nMatrix;
    }
    //求离散余弦变换的系数矩阵
    private static double[][] coefficient(){
        double[][] coeff=new double[imgMin][imgMin];
        double sqrt=1.0/Math.sqrt(imgMin);
        for(int i=0;i<imgMin;i++){
            coeff[0][i]=sqrt;
        }
        for(int i=1;i<imgMin;i++){
            for(int j=0;j<imgMin;j++){
                coeff[i][j]=Math.sqrt(2.0/imgMin)*Math.cos(i*Math.PI*(j+0.5)/(double)imgMin);
            }
        }
        return coeff;
    }
    private static int[] getRGBS(String filePath) throws IOException {
        BufferedImage image=ImageIO.read(new File(filePath));
        imgMin=image.getWidth()>image.getHeight()?image.getHeight():image.getWidth();
        int[] rgbs=new int[imgMin*imgMin];
        for(int y=image.getMinY();y<imgMin;y++){
            for(int x=image.getMinX();x<imgMin;x++){
                rgbs[y*imgMin+x]=image.getRGB(x,y);
            }
        }
//        for(int i=0;i<rgbs.length;i++){
//            System.out.print(rgbs[i]+" ");
//            if(i%imgMin==0&&i!=0){
//                System.out.println();
//            }
//        }
        return rgbs;
    }

    private static void txtSave(int[] arr,String saveFile) throws IOException {
        StringBuffer buffer=new StringBuffer();
        for(int i=0;i<arr.length;i++){
            buffer.append(arr[i]+" ");
        }
        BufferedWriter writer=new BufferedWriter(new FileWriter(saveFile));
        writer.write(buffer.toString());
        writer.close();
    }


    //--------------------------------------------------------//
    public static void imgEblock(String filePath,String saveFile) throws IOException {
        int prx=240;
        BufferedImage image=ImageIO.read(new File(filePath));
        BufferedImage nImage=new BufferedImage(prx,prx,BufferedImage.TYPE_3BYTE_BGR);
        for(int y=0;y<prx;y++){
            for(int x=0;x<prx;x++){
                nImage.setRGB(x,y,image.getRGB(x,y));
            }
        }
        ImageIO.write(nImage,"jpg",new File(saveFile));
    }

    //将图像按照8像素划分
    public static void imgEblock(String filePath)throws IOException{
        BufferedImage image=ImageIO.read(new File(filePath));
        BufferedImage writerImage=new BufferedImage(8,8,BufferedImage.TYPE_3BYTE_BGR);
        int inx=0;
        int iny=0;
        boolean is=true;
        d:while(true) {
            is=true;
            q:for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    if((inx*8+x)>=image.getWidth()){
                        iny++;
                        inx=0;
                        is=false;
                        break q;
                    }
                    if(((inx*8+x)*(iny*8+y))>=image.getWidth()*image.getHeight()){
                        break d;
                    }
                    writerImage.setRGB(x, y, image.getRGB(inx*8+x,iny*8+y));
                }
            }
            if(is) {
                ImageIO.write(writerImage, "jpg", new File("D:/Temp/img/img20190717/dct/elxyone" + inx+iny + ".jpg"));
                inx++;
            }
        }
    }

    //---------------------------jpeg文件格式---------------------------------------------------------------------------//
    public static void jpegStruct(String filePath) throws Exception {
        InputStream input=new FileInputStream(filePath);
        int len=0;
        byte[] buf=new byte[input.available()];
        input.read(buf);
        for(int i=0;i<buf.length;i++){
            System.out.print("0x"+Integer.toHexString(buf[i]));
        }
        /**
         * 0xffffffff0xffffffd8  0XFFD8 SOI 图像开始
         * 0xffffffff0xffffffe0  APP0 该标记之后包含9个具体的字段
         * 1.0x00x10 两个字节  用来表示1--9的9个字段的总长度。 10
         * 2.0x4a0x460x490x460x0 5个字节，固定值0X4A6494600，表示了字符串“JFIF0”。
         * 3.0x10x2     版本号，表示“JFIF0”的版本号为1.2
         * 4.0x0	    X,Y方向的密度单位：1个字节，只有三个值可选，0：无单位；1：点数每英寸；2：点数每厘米；  无单位
         * 5.0x00x1	    X方向像素密度
         * 6.0x00x1	    Y方向像素密度
         * 7.0x0	    缩略图水平像素数目
         * 8.0x0	    缩略图垂直像素数目   缩略图水平像素数目 为0和缩略图垂直像素数目则没有缩略图RGB位图
         *
         * 0xffffffff0xffffffdb 	DQT 定义量化表；标记代码为固定值0XFFDB；包含9个具体字段
         *
         * */
    }
}
