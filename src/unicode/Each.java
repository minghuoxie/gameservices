package unicode;

import java.util.HashMap;
import java.util.Map;

public class Each {
    private static Map<String,String> map;
    static {
        map=new HashMap<>();
        map.put("0","0000");
        map.put("1","0001");
        map.put("2","0010");
        map.put("3","0011");
        map.put("4","0100");
        map.put("5","0101");
        map.put("6","0110");
        map.put("7","0111");
        map.put("8","1000");
        map.put("9","1001");
        map.put("A","1010");
        map.put("B","1011");
        map.put("C","1100");
        map.put("D","1101");
        map.put("E","1110");
        map.put("F","1111");
    }
    /**
     * 十六进制转其他进制
     * */
    public static String ltoo(String s,int type){
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if((c>='0'&&c<='9')||(c>='A'&&c<='F')){
                continue;
            }else{
                return "十进制数错误";
            }
        }
        if(type==2){
            String er="";
            int index=0;
            for(int i=0;i<s.length();i++){
                er=er+map.get(s.charAt(i)+"");
            }
            for(;index<er.length();index++){
                if(er.charAt(index)!='0'){
                    break;
                }
            }
            return er.substring(index);
        }else if(type==10){
            return etoo(ltoo(s,2),10);
        }
        return "类型错误";
    }

    public static String stoo(String s,int type){
        /**
         * 十进制转换位其他进制
         * */
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(c<'0'||c>'9'){
                return "十进制字符串错误";
            }else if(i==0&&c=='0'){
                return "十进制字符串错误";
            }
        }
        if(type==2){
            String er="";
            int num=Integer.parseInt(s);
            while(num>1){
                int y=num%2;
                num=num/2;
                er=y+er;
            }
            if(num!=0){
                er=num+er;
            }
            return er;
        }else if(type==16){
            return etoo(stoo(s,2),16);
        }
        return "转换类型错误";
    }

    //带符号转换  补码=原码-->反码+1
    /**
     *1100 0011---表示正数195 也可以表示负数 195-256=-61
     *11000011的补码是多少
     * 11111111 11000011 00111100
     *
     * 11000011变为-61的过程
     * 11000011-1---11000010---00111101
     *
     * */
    public static String etoo(String stre){
        //int类型
        for(int i=0;i<stre.length();i++){
            if(stre.charAt(i)!='0'&&stre.charAt(i)!='1'){
                return "二级制字符串有误";
            }
        }
        if(stre.charAt(0)=='0'){
            return etoo(stre,10);
        }else{
            return "-"+(256-Integer.parseInt(etoo(stre,10)));
        }
    }
    //无符号转换
    public static String etoo(String stre,int type){
        /**
         * 二级制转其他进制  type=10  转10禁止  type=16  转十六进制
         * */
        for(int i=0;i<stre.length();i++){
            if(stre.charAt(i)!='0'&&stre.charAt(i)!='1'){
                return "二级制字符串有误";
            }
        }
        if(type==10){
            int sum=0;
            for(int i=stre.length()-1,j=0;i>=0;i--,j++){
                char c=stre.charAt(j);
                sum+=Math.pow(2,i)*Integer.parseInt(c+"");
            }
            return sum+"";
        }else if(type==16){
            String shiliu="";
            while(stre.length()>0){
                //当stre的长度小于4  在前面加0
                if(stre.length()<4){
                    String linStr="";
                    for(int i=0;i<4-stre.length();i++){
                        linStr+="0";
                    }
                    stre=linStr+stre;
                }
                //截取后面4个
                String s=stre.substring(stre.length()-4);
                stre=stre.substring(0,stre.length()-4);
                shiliu=map(etoo(s,10))+shiliu;
            }
            if(shiliu.length()%2!=0){
                shiliu="0x0"+shiliu;
            }else{
                shiliu="0x"+shiliu;
            }
            return shiliu;
        }else{
            return "转换类型有误";
        }
    }

    private static String map(String num){
        String re=num;
        switch (num){
            case "10": re="A"; break;
            case "11": re="B"; break;
            case "12": re="C"; break;
            case "13": re="D"; break;
            case "14": re="E"; break;
            case "15": re="F"; break;
        }
        return re;
    }

}
