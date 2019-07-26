package media.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImgLog {
    private static int hi=40;
    public static void paint(String save){
        BufferedImage img=new BufferedImage(800,1000,BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d=(Graphics2D)img.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintSanjiao(new Point(100,100),"left",g2d);
        paintSanjiao(new Point(100,100+hi),"right",g2d);
        paintSanjiao(new Point(100,100+2*hi),"top",g2d);
        paintSanjiao(new Point(100,100+3*hi),"bom",g2d);

        paintSanjiao(new Point(200,100),"left",g2d);
        paintSanjiao(new Point(200,100),"right",g2d);
        paintSanjiao(new Point(200,100),"top",g2d);
        paintSanjiao(new Point(200,100),"bom",g2d);

        paintArgc(new Point(297,106),g2d);
        paintPeo(new Point(300,100),g2d);
        g2d.drawImage(img,0,0,null);
        g2d.dispose();
        try {
            ImageIO.write(img,"png",new File(save));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //画圆弧
    private static void paintArgc(Point point,Graphics2D g2d){
        g2d.setColor(Color.YELLOW);
        //(int x, int y, int width, int height,int startAngle, int arcAngle)
        g2d.fillArc(point.getX(),point.getY(),12,12,0,180);
    }

    //画人
    private static void paintPeo(Point poine,Graphics2D g2d){
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(poine.getX(),poine.getY(),6,6);

    }

    //三角形
    private static void paintSanjiao(Point point,String type,Graphics2D g2d){
        g2d.setColor(Color.black);
        if("left".equals(type)) {
            int[] x = {point.getX(), point.getX() + 10, point.getX() + 10};
            int[] y = {point.getY(), point.getY() - 3, point.getY() + 3};
            g2d.fillPolygon(x, y, 3);
        }else if("right".equals(type)){
            int[] x = {point.getX(), point.getX() - 10, point.getX() - 10};
            int[] y = {point.getY(), point.getY() - 3, point.getY() + 3};
            g2d.fillPolygon(x, y, 3);
        }else if("top".equals(type)){
            int[] x = {point.getX(), point.getX() + 3, point.getX() - 3};
            int[] y = {point.getY(), point.getY() + 10, point.getY() + 10};
            g2d.fillPolygon(x, y, 3);
        }else if("bom".equals(type)){
            int[] x = {point.getX(), point.getX() + 3, point.getX() - 3};
            int[] y = {point.getY(), point.getY() - 10, point.getY() - 10};
            g2d.fillPolygon(x, y, 3);
        }

    }
}
