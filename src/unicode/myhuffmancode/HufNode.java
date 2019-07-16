package unicode.myhuffmancode;

public class HufNode {
    private HufNode leftNode;
    private HufNode rightNode;
    private String code;
    private float quan;

    private int codeTypeLeft;
    private int codeTypeRight;
    private HufNode preNode;//父结点
    public HufNode(){
        codeTypeLeft=0;
        codeTypeRight=0;
    }
    public HufNode(HufNode leftNode,HufNode rightNode,String code, float quan){
        this.leftNode=leftNode;
        this.rightNode=rightNode;
        this.code=code;
        this.quan=quan;
        codeTypeLeft=0;
        codeTypeRight=0;
    }
    public void setPreNode(HufNode preNode){
        this.preNode=preNode;
    }
    public void setQuan(float quan){
        this.quan=quan;
    }
    public float getQuan(){
        return this.quan;
    }
    public void setLeftNode(HufNode leftNode){
        this.leftNode=leftNode;
    }
    public void setRightNode(HufNode rightNode){
        this.rightNode=rightNode;
    }
    public HufNode getLeftNode(){
        return this.leftNode;
    }
    public HufNode getRightNode(){
        return this.rightNode;
    }
    public String getCode(){
        return this.code;
    }

    public HufNode getPreNode(){
        return this.preNode;
    }

    public void setCodeTypeLeft(int ty){
        this.codeTypeLeft=ty;
    }
    public void setCodeTypeRight(int ty){
        this.codeTypeRight=ty;
    }
    public int getCodeTypeLeft(){
        return this.codeTypeLeft;
    }
    public int getCodeTypeRight(){
        return this.codeTypeRight;
    }
}
