package net.help;

public class BufRead {
    private StringBuffer buf;
    private int index;//buf读取的下标
    private String reTest;//返回的字符串

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

    public char read(){
        char ch=buf.charAt(index);
        index=index+1;
        if(ch=='<'){
            StateRobot.setState(StateRobot.LABEL);
        }else if(ch=='>'&& StateRobot.getState()==StateRobot.LABEL){
            StateRobot.setState(StateRobot.LAST);
        }
        return ch;
    }
}
