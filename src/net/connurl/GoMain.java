package net.connurl;

public class GoMain {
    /**
     * 要爬的主网页
     * */
    public void go(){
        try {
            //惠水住房
//            new HuiShui("http://www.huishui.ccoo.cn","utf-8").huishuihomechuzhu();
//            System.out.println("--------1");
            //惠水招聘
//             new HuiShui("http://www.huishui.ccoo.cn","gb2312").zhaopin();
//            System.out.println("--------2");

            //58住房
//            new WuBa().zhuFan();
//            System.out.println("--------3");

          //  new WuBa().zhaopin("https://gy.58.com/ruanjiangong/?PGTID=0d202408-007d-fe8e-a7fc-34f350106fc5&ClickID=2","utf-8");
            new WuBa().qiancheng("https://www.51job.com","gb2312");


            //都匀招聘
//            new DouYun().zhaopin("http://www.0854job.com/job/","utf-8");
//            System.out.println("--------4");


            //贵州163
//            new YaoLiuSan().zhaoPin("http://www.163gz.com/gzzp8/zkxx/","gb2312");
//            System.out.println("--------5");

            //师大
          //  new ShiDa().shidachuangye("http://zjc.gznu.edu.cn:888/article/","utf-8"); *
           // System.out.println("--------6");
            //-------------lin
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
