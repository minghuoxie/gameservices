package media.image;

import com.gargoylesoftware.htmlunit.util.StringUtils;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;

public class CmykImage {

    private static boolean isRgbOrCmyk(String fileName) throws IOException {
        File file = new File(fileName);
        boolean isRgb=true;//true是Rgb否则是Cmyk
        //创建输入流
        ImageInputStream input = ImageIO.createImageInputStream(file);
        Iterator readers = ImageIO.getImageReaders(input);
        if (readers == null || !readers.hasNext()) {
            throw new RuntimeException("No ImageReaders found");
        }
        ImageReader reader = (ImageReader) readers.next();
        reader.setInput(input);
        //获取文件格式
       // BufferedImage image;
        try {
            // 尝试读取图片 (包括颜色的转换).
            reader.read(0); // RGB
            isRgb=true;
        } catch (IIOException e) {
            // 读取Raster (没有颜色的转换).
            reader.readRaster(0, null);// CMYK
            isRgb=false;
        }
        input.close();
        return  isRgb;
    }

    public static void imgHandle(String orgPath,String savePath) throws IOException {
        if(isRgbOrCmyk(orgPath)){
            System.out.println("------------------RGB模式的图像");
            commpressPicForScale(orgPath, savePath, 60, 0.8);
        }else{
            System.out.println("-------------------CMYK模式的图像");
        }
    }

    //减少图像的质量
    public static void resize(File originalFile, File resizedFile,
                              int newWidth, float quality) throws IOException {

        if (quality > 1) {
            throw new IllegalArgumentException(
                    "Quality has to be between 0 and 1");
        }

        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
        Image i = ii.getImage();
        Image resizedImage = null;
        newWidth=i.getWidth(null);
        int iHeight = i.getHeight(null);
        resizedImage = i.getScaledInstance(newWidth, iHeight, Image.SCALE_SMOOTH);
//        int iWidth = i.getWidth(null);
//        int iHeight = i.getHeight(null);
//
//        if(iWidth < newWidth){
//            newWidth = iWidth;
//        }
//        if (iWidth > iHeight) {
//
//        } else {
//            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight,
//                    newWidth, Image.SCALE_SMOOTH);
//        }

        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();

        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
                temp.getHeight(null), BufferedImage.TYPE_INT_RGB);

        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();

        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();

        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray = { 0, softenFactor, 0, softenFactor,
                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);

        // Write the jpeg to a file.
        FileOutputStream out = new FileOutputStream(resizedFile);

        // Encodes image as a JPEG data stream
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

        JPEGEncodeParam param = encoder
                .getDefaultJPEGEncodeParam(bufferedImage);

        param.setQuality(quality, true);

        encoder.setJPEGEncodeParam(param);
        encoder.encode(bufferedImage);
    }

    /**
     * 1B=8byte   字符
     * 1KB ==1024B
     * 1MB ==1024KB
     *
     * 1MB=1024*1024*8=
     *
     * */
    //计算图片的大小
    public static void fileSize(File file){
        long size=file.length();
        System.out.println("---------fileSize:"+size/1024/1000.0+"MB");
    }



    public static String commpressPicForScale(String srcPath, String desPath,long desFileSize, double accuracy) {
        if (!new File(srcPath).exists()) {
            return null;
        }
        try {
            File srcFile = new File(desPath);
            long srcFileSize = srcFile.length();
            System.out.println("源图片：" + srcPath + "，大小：" + srcFileSize / 1024+ "kb");

            // 1、先转换成jpg
            //Thumbnails.of(srcPath).scale(1f).toFile(desPath);
            // 递归压缩，直到目标文件大小小于desFileSize
           commpressPicCycle(desPath, desFileSize, accuracy);

            File desFile = new File(desPath);
            System.out.println("目标图片：" + desPath + "，大小" + desFile.length()/ 1024 + "kb");
            System.out.println("图片压缩完成！");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return desPath;
    }

    private static void commpressPicCycle(String desPath, long desFileSize,double accuracy) throws IOException {
        File srcFileJPG = new File(desPath);
        long srcFileSizeJPG = srcFileJPG.length();
        // 2、判断大小，如果小于500kb，不压缩；如果大于等于500kb，压缩
//         if (srcFileSizeJPG <= desFileSize * 1024) {
//             return;
//         }
         // 计算宽高
         BufferedImage bim = ImageIO.read(srcFileJPG);
         int srcWdith = bim.getWidth();
         int srcHeigth = bim.getHeight();
         int desWidth = new BigDecimal(srcWdith).multiply(new BigDecimal(accuracy)).intValue();
         int desHeight = new BigDecimal(srcHeigth).multiply(new BigDecimal(accuracy)).intValue();

         Thumbnails.of(desPath).size(desWidth, desHeight).outputQuality(accuracy).toFile(desPath);
        // commpressPicCycle(desPath, desFileSize, accuracy);
        //return;
    }
}
