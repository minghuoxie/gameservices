package unicode;

import java.io.*;

public class ChinaUtf {
    /***
     * UTF-8是一种变长字节编码方式。
     * 1个字节 0xxxxxxx
     * 2个字节 110xxxxx 10xxxxxx  中文
     * 3个字节 1110xxxx 10xxxxxx 10xxxxxx
     * 4个字节 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
     * 5个字节 111110xx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
     * 6个字节 1111110x 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
     *
     * UTF-8编码:
     * 00000000-0000007F的字符，用单个字节表示
     * 00000080-000007FF的字符，用两个字节表示 中文编码
     * 00000800-0000FFFF的字符，用3个字节表示
     *
     *GB2312标准共收录了6763个汉字，其中一级汉字3755个，二级汉字3008个   GBK是对gb2312的添加
     * GB2312中对所收汉字进行分区，每区含有94个汉字/符号。
     * 01-09 区为特殊字符
     * 10-15 区没有编码(88-94区也是没有编码)
     * 16-55 区为一级汉字 3755个
     * 56-87 区为二级汉字 3008个
     *
     * 每个汉字及符号以两个字节来表示。第一个字节称为"高位字节"，第二个字节称为"低位字节"
     * "高位字节"使用0xA1-0xF7(把01-87区的区号加上0xA0),"低位字节"使用0xA1-0xFE(把01-94位的位号加上0xA0)。
     * 由于一级汉字从16区开始，汉字区的高位字节的范围就是0xB0-0xF7,低位字节的范围是0xA1-0xFE,占用的码位是72*94=6768个，其中5个空位是D7FA-D7FE
     * 例如："啊"子在大多数程序中，会以两个字节，0xB0(高位字节)0xA1(低位字节)
     *
     * */


    //范围随机数()

    //int x=1+(int)(Math.random()*50)   int x=(int)(Math.random()*100)
    //产生随机汉字
    public static void randomChines(){
        byte[] buf = new byte[2];
        int hi=16+(int)(Math.random()*71);
        int lo=1+(int)(Math.random()*94);
        buf[0]=(byte)(0xA0+hi);
        buf[1]=(byte)(0xA0+lo);
        try {
            String randomStr=new String(buf,"GBK");
            System.out.println("随机数:"+randomStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void saveToFile(String saveFile){
        System.out.println("----start");
        StringBuffer bufStr=new StringBuffer();
            for(int quhao=1;quhao<=87;quhao++) {
                if(quhao>=10&&quhao<=15){
                    continue;
                }
                byte[] buf = new byte[2];
                buf[0] = (byte) (0xA0 + quhao);
                for (int i = 1; i <= 94; i++) {
                    buf[1] = (byte) (0xA0 + i);
                    try {
                        String str = new String(buf, "GBK");
                        String s = "[区号:" + quhao + "    高位字节:" + getToHex(Integer.toHexString(buf[0])) + "(" + getToBia(Integer.toBinaryString(buf[0])) + ")    低位字节:" + getToHex(Integer.toHexString(buf[1])) + "(" + getToBia(Integer.toBinaryString(buf[1])) + ")     " + i + ":" + str + "         ]";
                        bufStr.append(s+"\r\n");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        File file=new File(saveFile);
        try {
            file.createNewFile();
            BufferedWriter w=new BufferedWriter(new FileWriter(file));
            w.write(bufStr.toString());
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("----end");
    }


    //查字典
    public static void testFindStrs(String findStr){
        if(findStr==null||"".equals(findStr)){
            return;
        }
        for(int i=0;i<findStr.length();i++){
            testFindStr(findStr.charAt(i)+"");
        }
    }
    private static void testFindStr(String findStr){
        if(findStr.length()>1){
            return;
        }
        String reStr="";
        for(int i=16;i<=87;i++){
            reStr=testPrintt(i,findStr);
            if(!reStr.startsWith("false_")){
                break;
            }
        }
        System.out.println(reStr);
    }
    private static String testPrintt(int quhao,String findStr){
        if(quhao>=1&&quhao<=9||quhao>=16&&quhao<=87) {
            byte[] buf = new byte[2];
            buf[0] = (byte) (0xA0 + quhao);
            for (int i = 1; i <= 94; i++) {
                buf[1] = (byte) (0xA0 + i);
                try {
                    String str = new String(buf, "GBK");
                    if(findStr.equals(str)) {
                        return "[gbk-区号:" + quhao + "    高位字节:" + getToHex(Integer.toHexString(buf[0])) + "(" + getToBia(Integer.toBinaryString(buf[0])) + ")    低位字节:" + getToHex(Integer.toHexString(buf[1])) + "(" + getToBia(Integer.toBinaryString(buf[1])) + ")     " + i + ":" + str + "]";
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return "false_没有找到";
    }
    //String hexString = Integer.toHexString(ivalue)
    public static void testPrintt(int quhao){
        if(quhao>=1&&quhao<=9||quhao>=16&&quhao<=87) {
            byte[] buf = new byte[2];
            buf[0] = (byte) (0xA0 + quhao);
            for (int i = 1; i <= 94; i++) {
                buf[1] = (byte) (0xA0 + i);
                try {
                    String str = new String(buf, "GBK");
                    System.out.println("[区号:"+quhao+"    高位字节:"+getToHex(Integer.toHexString(buf[0]))+"("+getToBia(Integer.toBinaryString(buf[0]))+")    低位字节:"+getToHex(Integer.toHexString(buf[1]))+"("+getToBia(Integer.toBinaryString(buf[1]))+")     "+ i + ":" + str + "]");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("改区号没有编码");
        }
    }

    //打印对应区号的数字表
    public static void testPrint(int quhao){
        if(quhao>=1&&quhao<=9||quhao>=16&&quhao<=87) {
            byte[] buf = new byte[2];
            buf[0] = (byte) (0xA0 + quhao);
            for (int i = 1; i <= 94; i++) {
                buf[1] = (byte) (0xA0 + i);
                try {
                    String str = new String(buf, "GBK");
                    System.out.print("[" + i + ":" + str + "]");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (i % 10 == 0) {
                    System.out.println();
                }
            }
        }else{
            System.out.println("改区号没有编码");
        }
    }

    public static void testChine(){
       byte[] buf=new byte[2];
       byte[] b=new byte[1];
        buf[0]=(byte)0b11000001;
      // b[0]=121;
        b[0]=(byte)0b01111001;

        buf[0]=(byte)0xB0;
        buf[1]=(byte)0xA1;

        try {
            String str=new String(buf,"GBK");
            System.out.println(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static String getToBia(String str){
        if(str.length()<=8){
            return str;
        }
        return str.substring(str.length()-8);
    }
    private static String getToHex(String str){
        if(str.length()<=2){
            return "0x"+str;
        }
        return "0x"+str.substring(str.length()-2);
    }

    //计算二级制

}
