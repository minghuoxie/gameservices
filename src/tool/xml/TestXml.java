package tool.xml;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestXml {
    private static Node imgattr;
    private static Node pro;

    public static void createDatas(String xmlFilePath,List<TaskNode> taskNodes,List<FlowLine> flowLines){
        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder dBuilder = dbfactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(xmlFilePath));
            Element root = doc.getDocumentElement();
            pro=root.getElementsByTagName("process").item(0);
            imgattr=root.getElementsByTagName("bpmndi:BPMNPlane").item(0);

            createListtaskNodes(taskNodes);
            createflowLines(flowLines);

            System.out.println("-----------createDatasend-------------");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //获取List<FlowLine> flowLines
    private static void createflowLines(List<FlowLine> flowLines){
        NodeList proChildres= pro.getChildNodes();
        for(int i=0;i<proChildres.getLength();i++){
            Node linPro=proChildres.item(i);
            if(!"#text".equals(linPro.getNodeName())&&"sequenceFlow".equals(linPro.getNodeName())){
                List<Point> list=flowPoint(linPro.getAttributes().getNamedItem("id").getNodeValue());
                FlowLine flowLine=new FlowLine();
                flowLine.setId(linPro.getAttributes().getNamedItem("id").getNodeValue());
                flowLine.setSourceRef(linPro.getAttributes().getNamedItem("sourceRef").getNodeValue());
                flowLine.setTargetRef(linPro.getAttributes().getNamedItem("targetRef").getNodeValue());
                if(list!=null&&list.size()>0){
                    flowLine.setPoints(list);
                }
                flowLines.add(flowLine);
            }
        }
    }

    //获取List<TaskNode> taskNodes
    private static void createListtaskNodes(List<TaskNode> taskNodes){
        NodeList proChildres= pro.getChildNodes();
        for(int i=0;i<proChildres.getLength();i++){
            Node linPro=proChildres.item(i);
            if(!"#text".equals(linPro.getNodeName())&&!"sequenceFlow".equals(linPro.getNodeName())){
                char c='r';
                if("startEvent".equals(linPro.getNodeName())||"endEvent".equals(linPro.getNodeName())){
                    c='o';
                }
                TaskNode taskNode=new TaskNode();
                taskNode.setId(linPro.getAttributes().getNamedItem("id").getNodeValue());
                taskNode.setName(linPro.getAttributes().getNamedItem("name").getNodeValue());
                taskNode.setNodeName(linPro.getNodeName());
                taskNode.setType(c);
                TaskPoint taskPoint=taskFind(linPro.getAttributes().getNamedItem("id").getNodeValue());
                if(taskPoint!=null){
//                    System.out.println("nodeName:"+linPro.getNodeName()+"  id:"+linPro.getAttributes().getNamedItem("id").getNodeValue()+
//                            "  name:"+linPro.getAttributes().getNamedItem("name").getNodeValue()+"  type:"+c+"  x:"+taskPoint.getX()+"  y:"+taskPoint.getY()+
//                            "  w:"+taskPoint.getW()+"  h:"+taskPoint.getH());
                    taskNode.setTaskPoint(taskPoint);
                }
                taskNodes.add(taskNode);
            }
        }
    }

    public static void text(String xmlFilePath){
        //创建DocumentBuilderFactory工厂实例。
        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
        //通过文档创建工厂创建文档创建器
        try {
            DocumentBuilder dBuilder = dbfactory.newDocumentBuilder();

            //然后加载XML文档（Document）  : 通过文档创建器DocumentBuilder的parse方法解析参数URL指定的XML文档，并返回一个Document 对象
            Document doc = dBuilder.parse(new File(xmlFilePath));
            //找到根节点
            Element root = doc.getDocumentElement();

            //
            NodeList process=root.getElementsByTagName("process");
            imgattr=root.getElementsByTagName("bpmndi:BPMNPlane").item(0);
            System.out.println("---process:"+process.getLength());
            if(process.getLength()>0){
                pro=process.item(0);
               NodeList proChildres= pro.getChildNodes();
               for(int i=0;i<proChildres.getLength();i++){
                   Node linPro=proChildres.item(i);
                   if(!"#text".equals(linPro.getNodeName())){
                       if("sequenceFlow".equals(linPro.getNodeName())){
                           List<Point> list=flowPoint(linPro.getAttributes().getNamedItem("id").getNodeValue());
                           if(list!=null&&list.size()>0){
                               System.out.println("id:"+linPro.getAttributes().getNamedItem("id").getNodeValue()+"  sourceRef:"+linPro.getAttributes().getNamedItem("sourceRef").getNodeValue()+
                                       "  targetRef:"+linPro.getAttributes().getNamedItem("targetRef").getNodeValue());
                               for(Point point:list){
                                   System.out.println("--------------------x:"+point.getX()+"     y:"+point.getY());
                               }
                           }else{
                               System.out.println("id:"+linPro.getAttributes().getNamedItem("id").getNodeValue()+"  sourceRef:"+linPro.getAttributes().getNamedItem("sourceRef").getNodeValue()+
                                       "  targetRef:"+linPro.getAttributes().getNamedItem("targetRef").getNodeValue());
                           }

                       }else{
                           char c='o';
                           if("userTask".equals(linPro.getNodeName())){
                               c='r';
                           }
                           TaskPoint taskPoint=taskFind(linPro.getAttributes().getNamedItem("id").getNodeValue());
                           if(taskPoint!=null){
                               System.out.println("nodeName:"+linPro.getNodeName()+"  id:"+linPro.getAttributes().getNamedItem("id").getNodeValue()+
                                       "  name:"+linPro.getAttributes().getNamedItem("name").getNodeValue()+"  type:"+c+"  x:"+taskPoint.getX()+"  y:"+taskPoint.getY()+
                                       "  w:"+taskPoint.getW()+"  h:"+taskPoint.getH());
                           }else{
                               System.out.println("nodeName:"+linPro.getNodeName()+"  id:"+linPro.getAttributes().getNamedItem("id").getNodeValue()+
                                       "  name:"+linPro.getAttributes().getNamedItem("name").getNodeValue()+"  type:"+c+"  ");
                           }

                       }

                   }
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static List<Point> flowPoint(String id){
        NodeList imgs=imgattr.getChildNodes();
        for(int i=0;i<imgs.getLength();i++){
            Node linNode=imgs.item(i);
            if(!"#text".equals(linNode.getNodeName())){
                if("bpmndi:BPMNEdge".equals(linNode.getNodeName())){
                   // System.out.println("BPMNEdge-bpmnElement:"+linNode.getAttributes().getNamedItem("bpmnElement").getNodeValue()+"  ");
                    if(id.equals(linNode.getAttributes().getNamedItem("bpmnElement").getNodeValue())) {
                        List<Point> list=new ArrayList<>();
                        NodeList twoNodeList = linNode.getChildNodes();
                        for (int j = 0; j < twoNodeList.getLength(); j++) {
                            Node twoNode = twoNodeList.item(j);
                            if (!"#text".equals(twoNode.getNodeName()) && "omgdi:waypoint".equals(twoNode.getNodeName())) {
                                Point point=new Point();
                               // System.out.println("----------x:" + twoNode.getAttributes().getNamedItem("x").getNodeValue() + "  y:" + twoNode.getAttributes().getNamedItem("y").getNodeValue());
                                point.setX((int)(Double.parseDouble(twoNode.getAttributes().getNamedItem("x").getNodeValue())));
                                point.setY((int)(Double.parseDouble(twoNode.getAttributes().getNamedItem("y").getNodeValue())));
                                list.add(point);
                            }
                        }

                        return list;
                    }
                }
            }
        }
        return null;
    }
    private static TaskPoint taskFind(String id){
            NodeList imgs=imgattr.getChildNodes();
            for(int i=0;i<imgs.getLength();i++){
                Node linNode=imgs.item(i);
                if(!"#text".equals(linNode.getNodeName())){
                    if("bpmndi:BPMNShape".equals(linNode.getNodeName())){
                       // System.out.print("BPMNShape-bpmnElement:"+linNode.getAttributes().getNamedItem("bpmnElement").getNodeValue()+"  ");
                        if(id.equals(linNode.getAttributes().getNamedItem("bpmnElement").getNodeValue())) {
                            NodeList twoNodeList = linNode.getChildNodes();
                            for (int j = 0; j < twoNodeList.getLength(); j++) {
                                Node twoNode = twoNodeList.item(j);
                                if (!"#text".equals(twoNode.getNodeName())) {
                                    TaskPoint taskPoint=new TaskPoint();
                                    taskPoint.setX((int)(Double.parseDouble(twoNode.getAttributes().getNamedItem("x").getNodeValue())));
                                    taskPoint.setY((int)(Double.parseDouble(twoNode.getAttributes().getNamedItem("y").getNodeValue())));
                                    taskPoint.setW((int)(Double.parseDouble(twoNode.getAttributes().getNamedItem("width").getNodeValue())));
                                    taskPoint.setH((int)(Double.parseDouble(twoNode.getAttributes().getNamedItem("height").getNodeValue())));
                                    //taskPoint.setH(40);
//                                    System.out.println("height:" + twoNode.getAttributes().getNamedItem("height").getNodeValue() + "  width:" + twoNode.getAttributes().getNamedItem("width").getNodeValue() +
//                                            "  x:" + twoNode.getAttributes().getNamedItem("x").getNodeValue() + "  y:" + twoNode.getAttributes().getNamedItem("y").getNodeValue());
                                    return taskPoint;
                                }
                            }
                        }
                    }
//                    else{
//                        System.out.println("BPMNEdge-bpmnElement:"+linNode.getAttributes().getNamedItem("bpmnElement").getNodeValue()+"  ");
//                        NodeList twoNodeList=linNode.getChildNodes();
//                        for(int j=0;j<twoNodeList.getLength();j++){
//                            Node twoNode=twoNodeList.item(j);
//                            if(!"#text".equals(twoNode.getNodeName())&&"omgdi:waypoint".equals(twoNode.getNodeName())){
//                                System.out.println("----------x:"+twoNode.getAttributes().getNamedItem("x").getNodeValue()+"  y:"+twoNode.getAttributes().getNamedItem("y").getNodeValue());
//                            }
//                        }
//                    }


                }
            }
        return null;
    }
}
