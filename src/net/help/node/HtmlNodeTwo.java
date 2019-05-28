package net.help.node;

import net.help.BufRead;
import net.help.NodeTree;
import net.help.StateRobot;

public class HtmlNodeTwo {
    private NodeTree firstNode;//根节点
    private BufRead bufRead;
    public HtmlNodeTwo(StringBuffer buf){
        firstNode=new NodeTree();
        bufRead=new BufRead(buf);
    }

    public void createNode(){
        firstNode.setFlabel("<root>");
        firstNode.getChildNodes().add(setChildNode());
        System.out.println("--"+firstNode);
    }
    private NodeTree setChildNode(){
        NodeTree node=new NodeTree();
        StateRobot robot=new StateRobot();
        String str="";
        String linNodeName="";
        String key="";
        String val="";
        while(bufRead.hasNext()){
            char ch=bufRead.read();
            if(robot.getState()==StateRobot.ERRO){
                if(ch=='<'&&bufRead.isSet){
                    str="";
                }else if(ch=='>'&&bufRead.isSet){
                    if(StateRobot.isHas(str+">")){
                        node.setFlabel(str+">");
                        robot.setState(StateRobot.TENT);
                        str="";
                        continue;
                    }
                }else if(ch==' '&&bufRead.isSet){
                    if(StateRobot.isHas(str+">")){
                        node.setFlabel(str+">");
                        robot.setState(StateRobot.KEY);
                        str="";
                        continue;
                    }
                }
            }else if(robot.getState()==StateRobot.KEY){
                if(ch=='/'&&bufRead.isSet){
                    if(bufRead.hasNext()&&bufRead.read()=='>'){
                        return node;
                    }
                }else if(ch=='>'&&bufRead.isSet){
                    robot.setState(StateRobot.TENT);
                    str="";
                    continue;
                }else if(ch=='='&&bufRead.isSet){
                    key=str;
                    str="";
                    robot.setState(StateRobot.VAL);
                    continue;
                }else if(ch=='<'&&bufRead.isSet){
                    robot.setState(StateRobot.TENKAY);
                    continue;
                }
            }else if(robot.getState()==StateRobot.TENKAY){
                if(ch=='>'&&bufRead.isSet){
                    robot.setState(robot.getState()/10);
                }
                continue;
            }else if(robot.getState()==StateRobot.VAL){
                if(ch=='"'&&bufRead.isSet){
                    robot.setState(StateRobot.VALK);
                }
                continue;
            }else if(robot.getState()==StateRobot.VALK){
                if(ch=='"'&&bufRead.isSet){
                    robot.setState(StateRobot.KEY);
                    val=str;
                    node.getMap().put(key,val);
                    str="";
                    continue;
                }
            }else if(robot.getState()==StateRobot.TENT){
                if(ch=='<'&&bufRead.isSet){
                    robot.setState(StateRobot.TENTENT);
                    linNodeName="";
                    linNodeName+=ch;
                    continue;
                }
            }else if(robot.getState()==StateRobot.TENTENT){
                if((ch=='>'||ch==' ')&&bufRead.isSet){
                    linNodeName+='>';
                    if(StateRobot.isHas(linNodeName)){
                        //进入递归
                        robot.setState(robot.getState()/10);
                        bufRead.setIndex(-(linNodeName.length()));
                        node.getChildNodes().add(setChildNode());
                    }else if(StateRobot.isEnd(linNodeName)){
                        node.setContent(str);
                        return node;
                    }else if("</".equals(linNodeName.substring(0,2))){
                        robot.setState(robot.getState()/10);
                    }else{
                        robot.setState(StateRobot.TENTENTENT);
                    }
                    continue;
                }
                linNodeName+=ch;
                continue;
            }else if(robot.getState()==StateRobot.TENTENTENT){
                if(ch=='<'&&bufRead.isSet){
                    robot.setState(robot.getState()/10);
                    linNodeName=ch+"";
                }
                continue;
            }
            if(ch!=' ') {
                str += ch;
            }
        }
        return node;
    }
}
