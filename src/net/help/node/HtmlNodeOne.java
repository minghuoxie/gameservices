package net.help.node;

import net.help.BufRead;
import net.help.NodeTree;
import net.help.StateRobot;

public class HtmlNodeOne {
    private NodeTree firstNode;//根节点
    private BufRead bufRead;
    public HtmlNodeOne(StringBuffer buf){
        firstNode=new NodeTree();
        bufRead=new BufRead(buf);
    }
    public NodeTree getFirstNode(){
        return this.firstNode;
    }

    public void setNode(){
        firstNode.setFlabel("<root>");
        firstNode.getChildNodes().add(setChildNode());
        System.out.println("--"+firstNode);
    }

    private NodeTree setChildNode(){
        NodeTree node=new NodeTree();
        StateRobot robot=new StateRobot();
        boolean isAdd=false;
        String str="";
        String content="";
        String key="";
        String val="";
        while(bufRead.hasNext()){
            char ch=bufRead.read();
            if(robot.getState()==StateRobot.ERRO){
                if(ch=='<'){
                    isAdd=true;
                }else if(ch=='>'&&isAdd){
                    if(StateRobot.isHas(str+">")){
                        node.setFlabel(str+">");
                        robot.setState(StateRobot.TENT);
                    }
                    str="";
                    isAdd=false;
                }else if(ch==' '&&isAdd){
                    if(StateRobot.isHas(str+">")){
                        node.setFlabel(str+">");
                        robot.setState(StateRobot.KEY);
                    }
                    str="";
                    isAdd=false;
                }
                if(isAdd){
                    str+=ch;
                }
            }else if(robot.getState()==StateRobot.KEY){
                if(ch=='>'){
                    robot.setState(StateRobot.TENT);
                    str="";
                }else if(ch=='='){
                    key=str;
                    str="";
                    robot.setState(StateRobot.VAL);
                }else if(ch!=' '){
                    str+=ch;
                }
            }else if(robot.getState()==StateRobot.VAL){
                if(ch=='"'){
                    robot.setState(StateRobot.VALK);
                }
            }else if(robot.getState()==StateRobot.VALK){
                if(ch=='"'){
                    robot.setState(StateRobot.KEY);
                    val=str;
                    node.getMap().put(key,val);
                    str="";
                }
                str+=ch;
            }else if(robot.getState()==StateRobot.TENT){
                if(ch=='<'){
                    content+=str;
                    str="";
                    isAdd=true;
                }else if(ch!=' '){
                    str+=ch;
                }else if((ch==' '||ch=='>')&&isAdd){
                    if(StateRobot.isHas(str+">")){
                        //进入递归
                        bufRead.setIndex(-str.length());
                        node.getChildNodes().add(setChildNode());
                    }else if(StateRobot.isEnd(str+">")){
                        //结束
                        return node;
                    }
                    str="";
                    isAdd=false;
                }

                if(isAdd){
                    str+=ch;
                }
            }
        }
        return node;
    }
}
