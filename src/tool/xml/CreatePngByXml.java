package tool.xml;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreatePngByXml {
    public static void createPng(String xmlPath,String savePng){
        List<TaskNode> taskNodeList=new ArrayList<>();
        List<FlowLine> flowLineList=new ArrayList<>();
        TestXml.createDatas(xmlPath,taskNodeList,flowLineList);
        if(taskNodeList!=null&&taskNodeList.size()>0&&flowLineList!=null&&flowLineList.size()>0){
            Point pngBig=pngBig(taskNodeList);
            BufferedImage bufImg=new BufferedImage(pngBig.getX(),pngBig.getY(),BufferedImage.TYPE_4BYTE_ABGR);

            Graphics2D g2d=(Graphics2D)bufImg.getGraphics();//获取画笔
            //先将背景设置为红色
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0,0,pngBig.getX(),pngBig.getY());
          //  g2d.drawImage(bufImg,0,0,null);
           // Graphics g2d=bufImg.getGraphics();
            Stroke stroke = g2d.getStroke();
            g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
//           VALUE_ANTIALIAS_ON
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.BLACK);
            //画结点
            for(TaskNode taskNode:taskNodeList){
                TaskPoint taskPoint=taskNode.getTaskPoint();
                if("startEvent".equals(taskNode.getNodeName())){
                    paintStartEvent(g2d,taskPoint);
                }else if("userTask".equals(taskNode.getNodeName())){
                    paintUserTask(g2d,taskPoint);
                }else if("endEvent".equals(taskNode.getNodeName())){
                    paintEndEvent(g2d,taskPoint);
                }else{
                    paintQiTa(g2d,taskPoint);
                }
            }

            //画线

//            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
            //   g2d.drawLine(341,177,341,210);
           g2d.setStroke( stroke );

            for(FlowLine flowLine:flowLineList){
              List<Point> list= flowLine.getPoints();
              //(int x1, int y1, int x2, int y2)
                if(list.size()>=2) {
                    Point to = null;
                    Point end = null;
                    for (int i = 0; i < list.size() - 1; i++) {
                        to = list.get(i);
                        end = list.get(i + 1);
                        g2d.drawLine(to.getX(), to.getY(), end.getX(), end.getY());
                    }
                }
            }
            g2d.drawImage(bufImg,0,0,null);
            try {
                ImageIO.write(bufImg,"png",new File(savePng));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //计算图片大小
    private static Point pngBig(List<TaskNode> list){
        Point point=new Point(842,1195);
        int h=0;
        int wleft=list.get(0).getTaskPoint().getX();
        int w=0;
        for(TaskNode taskNode:list){
            TaskPoint taskPoint=taskNode.getTaskPoint();
            if((taskPoint.getY()+taskPoint.getH())>h){
                h=taskPoint.getY()+taskPoint.getH();
            }

            if((taskPoint.getX()+taskPoint.getW())>w){
                w=taskPoint.getX()+taskPoint.getW();
            }
            if(taskPoint.getX()<wleft){
                wleft=taskPoint.getX();
            }
        }
        point.setY(h+20);
        point.setX(w+wleft);
        return point;
    }


    //画startEvent
    private static void paintStartEvent(Graphics2D g2d,TaskPoint taskPoint){
        //(int x, int y, int width, int height);
       // g2d.setColor(Color.BLUE);
        g2d.setColor(new Color(133,235,133));
        g2d.fillOval(taskPoint.getX(),taskPoint.getY(),taskPoint.getW(),taskPoint.getH());
        g2d.setColor(Color.black);
        g2d.drawOval(taskPoint.getX(),taskPoint.getY(),taskPoint.getW(),taskPoint.getH());
    }

    //画userTask
    private static void paintUserTask(Graphics2D g2d,TaskPoint taskPoint){
        g2d.setColor(new Color(133,235,133));
        g2d.fillRoundRect(taskPoint.getX(),taskPoint.getY(),taskPoint.getW(),taskPoint.getH(),10,10);
        g2d.setColor(Color.black);
        g2d.drawRoundRect(taskPoint.getX(),taskPoint.getY(),taskPoint.getW(),taskPoint.getH(),10,10);
    }

    //画exclusiveGateway  parallelGateway
    //rotate（double arc,double x,double y）//第一个参数是旋转角度，后二个参数是旋转中心点的横纵坐标参数
    private static void paintQiTa(Graphics2D g2d,TaskPoint taskPoint){
        //旋转90度
//        AffineTransform old= g2d.getTransform();
//        AffineTransform transform=new AffineTransform();
//        transform.rotate(95.0F,taskPoint.getX()+taskPoint.getW()/2,taskPoint.getY()+taskPoint.getH()/2);
//        g2d.setTransform(transform);
        g2d.setColor(new Color(	226,124,124));
        g2d.fillRect(taskPoint.getX(),taskPoint.getY(),taskPoint.getW(),taskPoint.getH());
        g2d.setColor(Color.black);
        g2d.drawRect(taskPoint.getX(),taskPoint.getY(),taskPoint.getW(),taskPoint.getH());

        //g2d.setTransform(old);
        //设置回来
    }
    //画endEvent
    private static void paintEndEvent(Graphics2D g2d,TaskPoint taskPoint){
        g2d.setColor(new Color(234,0,0));
        g2d.fillOval(taskPoint.getX(),taskPoint.getY(),taskPoint.getW(),taskPoint.getH());
        g2d.setColor(Color.black);
        g2d.drawOval(taskPoint.getX(),taskPoint.getY(),taskPoint.getW(),taskPoint.getH());
    }
}
