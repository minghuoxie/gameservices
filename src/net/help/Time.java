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
}
