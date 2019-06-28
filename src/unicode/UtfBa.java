package unicode;

import java.io.*;

public class UtfBa {
    /**
     * https://www.cnblogs.com/chenwenbiao/archive/2011/08/11/2134503.html
     * UTF-8编码
     *      UTF-8是一种变长字节编码方式。
     *      * 1个字节 0xxxxxxx
     *      * 2个字节 110xxxxx 10xxxxxx  中文
     *      * 3个字节 1110xxxx 10xxxxxx 10xxxxxx
     *      * 4个字节 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
     *      * 5个字节 111110xx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
     *      * 6个字节 1111110x 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
     *
     *UTF-8是unicode的一种实现方式
     * UTF-8的编码规则：
     * 1.对于单字节的符号，字节的第一位设置位0，后面7位为这个符号的unicode码，对应英文，UTF-8的编码和ASCII编码相同。
     * 2.对应n字节的符号(n>1),第一个字节的前n个都为1，第n+1位设置位0，后面的字节前两位一律设为10。剩下的没有提及的二进制位，全部为这个符号的unicode编码
     *
     * Unicode符号的范围（十六进制）  UTF-8编码方式(二进制)
     *                  0000-007F       0xxxxxxx  1个字节编码
     *                  0080-07FF       110xxxxx 10xxxxxx 2个字节编码
     *                  0800-FFFF       1110xxxx 10xxxxxx 10xxxxxx 3个字节编码
     *
     *                  0-127           0xxxxxxx  127
     *                  128-2047
     *                  2048-65535
     *
     *                  4E25 20005 100|111000|100101(100111000100101)   严要使用3个字节表示  从严的最后一个二进制位开始，依次从后向前填入格式中的x，多出的位补0
     *
     *                  11100100 10111000 10100101
     *
     *                  00(00000000)-007F(01111111)
     *                  0080(10|000000)-07FF(11111|111111)
     *                  0800(|100000|000000)-FFFF(1111|111111|111111)
     *
     *                  1个字节分区表  00000000-01111111
     *                  2个字节分区表  11000010 10000000-11011111 10111111
     *                  3个字节分区表  11100000 10100000 10000000-11101111 10111111 10111111
     *
     *
     * */

    //一个字节编码 0xxxxxxx
    public static void testOneByte() throws UnsupportedEncodingException {
        //E4B8A5  严
        byte[] buf=new byte[3];
//        buf[0]=(byte)0xE4;
//        buf[1]=(byte)0xB8;
//        buf[2]=(byte)0xA5;
//        String s=new String(buf,"utf-8");
//        System.out.println("---"+s);

        buf[0]=(byte)0b11100100;
        buf[1]=(byte)0b10111000;
        buf[2]=(byte)0b10100101;

        String s=new String(buf,"utf-8");
        System.out.println("--"+s);
    }

    //打印utf-8编码表
    public static void testPrintUTFONE(String filePath) throws UnsupportedEncodingException {
        System.out.println("--------start");
        byte[] buf=new byte[1];
        StringBuffer writeBuf=new StringBuffer();
        writeBuf.append("-------------------------------1个字节分区表------------------------------------------\r\n");
        for(int x=0b00000000;x<=0b01111111;x++){
            buf[0]=(byte)x;
            String s=new String(buf,"utf-8");
            String line="[一个字节分区表     编号:"+x+"     编码:"+s+"                                ]";
            //System.out.println(line);
            writeBuf.append(line);
            writeBuf.append("\r\n");
        }
        saveFile(filePath,writeBuf);
        System.out.println("--------end");
    }
    private static void saveFile(String filePath,StringBuffer writeBuf){
        File file=new File(filePath);
        try {
            file.createNewFile();
            BufferedWriter w=new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(file,true)));
            w.write(writeBuf.toString());
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //打印utf-82个字节分区表
    //11000010 10000000-11011111 10111111
    public static void testPrintUTFTWO(String filePath)throws UnsupportedEncodingException{
        System.out.println("--------start");
        StringBuffer writeBufLin=new StringBuffer();
        writeBufLin.append("-------------------------------2个字节分区表------------------------------------------\r\n");
        saveFile(filePath,writeBufLin);
        byte[] buf=new byte[2];
        for(int n=0b11000010;n<=0b11011111;n++){
            StringBuffer writeBuf=new StringBuffer();
            for(int x=0b10000000;x<=0b10111111;x++){
                buf[0]=(byte)n;
                buf[1]=(byte)x;
                String s=new String(buf,"utf-8");
                String line="[2个字节分区表     区号:"+n+"    编号:"+x+"     编码:"+s+"                       ]";
                //System.out.println(line);
                writeBuf.append(line);
                writeBuf.append("\r\n");
            }
            saveFile(filePath,writeBuf);
        }
        System.out.println("--------end");
    }

    //3个字节分区表  11100000 10100000 10000000-11101111 10111111 10111111
    public static void testPringUTFSAN(String filePath)throws UnsupportedEncodingException{
        System.out.println("--------start");
        StringBuffer writeBufLin=new StringBuffer();
        writeBufLin.append("-------------------------------3个字节分区表------------------------------------------\r\n");
        saveFile(filePath,writeBufLin);
        byte[] buf=new byte[3];
        for(int x1=0b11100000;x1<=0b11101111;x1++){
            for(int x2=0b10100000;x2<=0b10111111;x2++){
                StringBuffer writeBuf=new StringBuffer();
                for(int x3=0b10000000;x3<=0b10111111;x3++){
                    buf[0]=(byte)x1;
                    buf[1]=(byte)x2;
                    buf[2]=(byte)x3;
                    String s=new String(buf,"utf-8");
                    String line="[3个字节分区表     区号:"+x1+"    编号-one:"+x2+" two:"+x3+"     编码:"+s+"               ]";
                    //System.out.println(line);
                    writeBuf.append(line);
                    writeBuf.append("\r\n");
                }
                saveFile(filePath,writeBuf);
            }
        }

        System.out.println("--------end");
    }


    //根据编码转找到符号
    public static String testFindStr(String code)throws UnsupportedEncodingException{
        if(code.length()%2==0&&code.length()!=0&&code.length()<=6){
            for(int i=0;i<code.length();i++){
                char c=code.charAt(i);
                if(c>='0'&&c<='9'||c>='A'||c<='F'){
                    continue;
                }else{
                    return "";
                }
            }
            if(code.length()==2){
                byte[] byteOne=new byte[1];
                byteOne[0]=(byte)(Integer.parseInt(code,16));
                return new String(byteOne,"utf-8");
            }else if(code.length()==4){
                byte[] byteTwo=new byte[2];
                byteTwo[0]=(byte)(Integer.parseInt(code.substring(0,2),16));
                byteTwo[1]=(byte)(Integer.parseInt(code.substring(2),16));
                return new String(byteTwo,"utf-8");
            }else if(code.length()==6){
                byte[] byteSan=new byte[3];
                byteSan[0]=(byte)(Integer.parseInt(code.substring(0,2),16));
                byteSan[1]=(byte)(Integer.parseInt(code.substring(2,4),16));
                byteSan[2]=(byte)(Integer.parseInt(code.substring(4),16));
                return new String(byteSan,"utf-8");
            }
        }
        return "";
    }

    //查字典  根据中文查找utf-8编码
    public static String testFindCode(String chine)throws UnsupportedEncodingException{
        byte[] bufone=new byte[1];
        for(int x=0b00000000;x<=0b01111111;x++){
            bufone[0]=(byte)x;
            String s=new String(bufone,"utf-8");
           if(s.equals(chine)){
               return Each.stoo(""+x,16).replaceAll("0x","");
           }
        }
        byte[] bufTwo=new byte[2];
        for(int n=0b11000010;n<=0b11011111;n++){
            StringBuffer writeBuf=new StringBuffer();
            for(int x=0b10000000;x<=0b10111111;x++){
                bufTwo[0]=(byte)n;
                bufTwo[1]=(byte)x;
                String s=new String(bufTwo,"utf-8");
                if(s.equals(chine)){
                    return (Each.stoo(""+n,16)+Each.stoo(""+x,16)).replaceAll("0x","");
                }
            }
        }
        byte[] buf=new byte[3];
        for(int x1=0b11100000;x1<=0b11101111;x1++){
            for(int x2=0b10100000;x2<=0b10111111;x2++){
                StringBuffer writeBuf=new StringBuffer();
                for(int x3=0b10000000;x3<=0b10111111;x3++){
                    buf[0]=(byte)x1;
                    buf[1]=(byte)x2;
                    buf[2]=(byte)x3;
                    String s=new String(buf,"utf-8");
                    if(s.equals(chine)){
                        return (Each.stoo(""+x1,16)+Each.stoo(""+x2,16)+Each.stoo(""+x3,16)).replaceAll("0x","");
                    }
                }
            }
        }
        return "";
    }
}
