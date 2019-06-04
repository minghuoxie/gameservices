package net.connurl;

public class GoMain {
    /**
     * 要爬的主网页
     * */
    public void go(){
        try {
            //惠水住房
           // new HuiShui("http://www.huishui.ccoo.cn","utf-8").huishuihomechuzhu();
            //惠水招聘
            // new HuiShui("http://www.huishui.ccoo.cn","gb2312").zhaopin();


            //58住房
           // new WuBa().zhuFan();


            //都匀招聘
            //new DouYun().zhaopin("http://www.0854job.com/job/","utf-8");


            //贵州163
           // new YaoLiuSan().zhaoPin("http://www.163gz.com/gzzp8/zkxx/","gb2312");

            //师大
          //  new ShiDa().shidachuangye("http://zjc.gznu.edu.cn:888/article/","utf-8"); *
            //-------------lin
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
