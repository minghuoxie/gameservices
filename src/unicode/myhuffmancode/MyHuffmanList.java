package unicode.myhuffmancode;

public class MyHuffmanList {
    private HufNode firstNode;
    private int count;
    private HufNode nextNode;
    public MyHuffmanList(){
        count=0;
    }
    public MyHuffmanList(HufNode node){
        count=0;
        this.firstNode=node;
        this.nextNode=this.firstNode;
    }
    public void addNode(HufNode node1,HufNode node2){
        HufNode newNode=new HufNode();
        newNode.setQuan(node1.getQuan()+node2.getQuan());
        newNode.setLeftNode(node1);
        newNode.setRightNode(node2);
        firstNode=newNode;
        node1.setPreNode(newNode);
        node2.setPreNode(newNode);
        this.nextNode=this.firstNode;
    }

    public HufNode getFirstNode(){
        return this.firstNode;
    }
    public void setNextNode(){
        this.nextNode=this.firstNode;
    }
    public HufNode getNextNode(char type){
        if(type=='0'){
            this.nextNode=this.nextNode.getLeftNode();
        }else if(type=='1'){
            this.nextNode=this.nextNode.getRightNode();
        }
        return this.nextNode;
    }

    //解码
    public String getCode(String codeStr){
        StringBuffer strBuf=new StringBuffer();
        for(int i=0;i<codeStr.length();i++){
            char ch=codeStr.charAt(i);
            HufNode linNode=this.getNextNode(ch);
            if(linNode.getCode()!=null&&!linNode.getCode().equals("")){
                strBuf.append(linNode.getCode());
                this.setNextNode();
            }
        }
        return strBuf.toString();
    }
    private void setCodeType(HufNode node){
        node.setCodeTypeLeft(0);
        node.setCodeTypeRight(0);
    }
    //编码  100010000100111000111001110000111010110111
    public String setCode(String codeStr){
        StringBuffer reBuffer=new StringBuffer();
        for(int i=0;i<codeStr.length();i++){
            String c=codeStr.charAt(i)+"";
            String linCode="";
            HufNode jiNode=this.firstNode;
            setCodeType(jiNode);
            while(true){
                if(jiNode==this.firstNode&&jiNode.getCodeTypeLeft()==1&&jiNode.getCodeTypeRight()==1){
                    break;
                }else if(c.equals(jiNode.getCode())){
                    reBuffer.append(linCode);
                    break;
                }else if(jiNode.getLeftNode()!=null&&jiNode.getCodeTypeLeft()==0){
                    jiNode=jiNode.getLeftNode();
                    linCode+="0";
                    setCodeType(jiNode);
                    continue;
                }else if(jiNode.getRightNode()!=null&&jiNode.getCodeTypeRight()==0){
                    jiNode=jiNode.getRightNode();
                    linCode+="1";
                    setCodeType(jiNode);
                    continue;
                }else{
                    //跳入父级
                    if(jiNode.getPreNode()!=null){
                        jiNode=jiNode.getPreNode();
                        if("0".equals(""+linCode.charAt(linCode.length()-1))){
                            jiNode.setCodeTypeLeft(1);
                        }else{
                            jiNode.setCodeTypeRight(1);
                        }
                        linCode=linCode.substring(0,linCode.length()-1);
                    }else{
                        break;
                    }
                }
            }
        }
        return reBuffer.toString();
    }
}
