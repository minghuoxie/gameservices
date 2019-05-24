package net.help;

public class FirstNodeTree {

    private NodeTree firstNode;//根节点
    private BufRead bufRead;
    private int len;

    private String str="";
    private String preStr="";
    public FirstNodeTree(StringBuffer buf){
        firstNode=new NodeTree();
        len=buf.length();
        bufRead=new BufRead(buf);
    }
    public NodeTree getFirstNode(){
        return this.firstNode;
    }
    //将html转换为数
    public void firstNode(){
        for(int i=0;i<len;i++){
            char ch=bufRead.read();
            if(ch!=' '){
                str+=ch;
            }else if(StateRobot.getState()==StateRobot.LABEL){
                preStr=str+">";
                str="";
                firstNode.setFlabel(preStr);
            }
            if(StateRobot.getState()==StateRobot.LAST){
                break;
            }

        }
        System.out.println(firstNode.getFlabel());
    }

}
