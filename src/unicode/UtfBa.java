package unicode;

import java.io.UnsupportedEncodingException;

public class UtfBa {
    /**
     * UTF-8编码
     *
     * */

    //一个字节编码 0xxxxxxx
    public static void testOneByte() throws UnsupportedEncodingException {
        byte[] buf=new byte[1];
        buf[0]=(byte)0b00100111;
        String s=new String(buf,"utf-8");
    }
}
