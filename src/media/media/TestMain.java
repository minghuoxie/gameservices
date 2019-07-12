package media.media;



public class TestMain {
    /**LZ77算法：one
     *
     * 设置滑动窗口为 5
     * 设置带编码区域 3
     * 找到滑动窗口和带编码区域的最大子串，并且带编码区域首字母包含在其中
     * */
    private final static int MAX_S=200;
    private static StringBuffer search=new StringBuffer();
    private static String str;
    private static String ptrStr;

    private static int offset=0;
    private static int len=0;
    public static void testLZone(String s){
        //填充头
        //"AABCBBABC"
        str=s;
        while(str.length()>0){
            if(len+1>str.length()) {
                ptrStr+=" ";
            }else{
                ptrStr = str.substring(0, len + 1);
            }
            if(isFind(ptrStr)){

            }else{
                if(ptrStr.length()>1){
                    ptrStr=ptrStr.substring(0,ptrStr.length()-1);
                    pFind(offset,len,ptrStr);
                    len=len-1;
                }else{
                    pFind(offset,len,ptrStr);
                }
                str=str.substring(len+1);
                offset=0;
                len=0;
            }
        }
//        if(isFind(s.charAt(position)+"")){
//
//        }else{
//
//            pFind(0,0,s.charAt(position)+"");
//        }
    }
    private static void pFind(int off,int length,String s){
        if(search.length()+s.length()<=MAX_S){
            search.append(s);
        }else if(search.length()==s.length()){
            search.replace(0,search.length(),s);
        }else{
            String lin=search.substring(s.length())+s;
            search.replace(0,search.length(),lin);
        }
        System.out.println("("+off+","+length+","+s+")");
    }
    private static boolean isFind(String s){
        for(int i=0;i<search.length();i++){
            if(s.charAt(0)==search.charAt(i)&&(i+s.length()<=search.length())&&s.equals(search.substring(i,i+s.length()))){
                offset=search.length()-i;
                len=s.length();
                return true;
            }
        }
        return false;
    }

}
