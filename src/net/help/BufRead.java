package net.help;

public class BufRead {
    private StringBuffer buf;
    private int index;//buf读取的下标
    private String reTest;//返回的字符串
    private char ch;
    public boolean isSet=true;

    public BufRead(StringBuffer buf){
        this.buf=buf;
        index=0;
        reTest="";
    }

    public String read(int num){
        if(index+num>buf.length()){
            return null;
        }
        reTest=buf.substring(index,index+num);
        index=index+num;
        return reTest;
    }
    public boolean hasNext(){
        return index<buf.length();
    }
    public char read(){
        isSet=true;
        ch=buf.charAt(index);
        index=index+1;
        if(ch=='\\'){
            isSet=false;
            ch=readNext(1);
        }
        return ch;
    }
    //返回后面的第n个字符
    public char readNext(int n){
        char nextCh=ch;
        if(index+n<buf.length()){
            nextCh=buf.charAt(index+n);
        }
        index=index+n;
        return nextCh;
    }

    public int getIndex(){
        return this.index;
    }

    public int setIndex(int n){
        this.index=this.index+n;
        return this.index;
    }
    public int getLen(){
        return buf.length();
    }
}
