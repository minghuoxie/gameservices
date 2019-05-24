package net.help;

import java.util.List;

public class FirstNodeTree {

    private NodeTree firstNode;//根节点
    private BufRead bufRead;

    private String str="";
    private String preStr="";
    private StringBuffer buf;
    public FirstNodeTree(StringBuffer buf){
        firstNode=new NodeTree();
        bufRead=new BufRead(buf);
        this.buf=buf;
    }
    public NodeTree getFirstNode(){
        return this.firstNode;
    }

    public void setNode(){
        firstNode.setFlabel("<root>");
        firstNode.getChildNodes().add(setNodeChild());
        System.out.println("--"+firstNode);
    }
    private NodeTree setNodeChild(){
        NodeTree nodeTree=new NodeTree();
        StateRobot robot=new StateRobot();
        String key="";
        String val="";
        for(int i=bufRead.getIndex();i<bufRead.getLen();i++){
            char ch=bufRead.read();
            if(robot.getState()==StateRobot.ERRO){
                if(ch=='<') {
                    bufRead.setIndex(-1);
                    String node = yudu(robot);
                    if (StateRobot.isHas(node)) {
                        nodeTree.setFlabel(node);
                        robot.setState(StateRobot.KEY);
                    }
                }
            }else if(robot.getState()==StateRobot.KEY){
                if(ch=='>'){
                    robot.setState(StateRobot.TENT);
                    str="";
                }else if(ch=='='){
                    robot.setState(StateRobot.VAL);
                    key=str;
                    str="";
                }else if(ch!=' '){
                    str+=ch;
                }
            }else if(robot.getState()==StateRobot.VAL){
                if(ch=='"'){
                    robot.setState(StateRobot.VALK);
                }
            }else if(robot.getState()==StateRobot.VALK){
                if(ch=='"'){
                    val=str;
                    str="";
                    nodeTree.getMap().put(key,val);
                    robot.setState(StateRobot.KEY);
                }else if(ch!=' '){
                    str+=ch;
                }
            }else if(robot.getState()==StateRobot.TENT){
                //  递归判断和退出判断
                if(ch=='<'){
                    int biaojiIndex=bufRead.setIndex(-1);
                    String nodeNames=yudu(robot);
                    if(StateRobot.isHas(nodeNames)){
                        //进入递归
                        bufRead.setIndex(biaojiIndex-bufRead.getIndex());
                        nodeTree.getChildNodes().add(setNodeChild());
                    }else if(StateRobot.isEnd(nodeNames)){
                        //结束
                        nodeTree.setContent(str);
                        str="";
                        return nodeTree;
                    }
                }else if(ch!=' '){
                    str+=ch;
                }
            }
        }
        return nodeTree;
    }

    private String yudu(StateRobot robot){
        String linStr="";
        for(int i=bufRead.getIndex();i<bufRead.getLen();i++){
            char ch=bufRead.read();
            if(ch==' '){
                linStr+=">";
                break;
            }else  if(ch=='>'){
                linStr+=">";
                robot.setState(StateRobot.TENT);
                break;
            }
            linStr+=ch;
        }
        return linStr;
    }
}
