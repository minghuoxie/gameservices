package net.help;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
    public static String getTime(String forMat){
        Date date=new Date();
        DateFormat format = new SimpleDateFormat(forMat);
        return format.format(date);
    }

    public static String addChar(String str,int index,char ch){
        String newStr="";
        boolean isAdd=true;
        if(index<=0){
            newStr+=ch;
            newStr+=str;
            return newStr;
        }else if(index>=str.length()){
            newStr+=str;
            newStr+=ch;
            return newStr;
        }
        for(int i=0;i<str.length();i++){
            if(index==i&&isAdd){
                newStr+=ch;
                isAdd=false;
                i--;
            }else{
                newStr+=str.charAt(i);
            }
        }
        return newStr;
    }
}
