package net.help;

public class StateRobot {
    private int state=0;
    public final static int ERRO=0;
    public final static int LABEL=11;
    public final static int KEY=13;
    public final static int VAL=14;
    public final static int VALK=141;
    public final static int TENT=15;
    public int getState(){
        return state;
    }
    public void setState(int sa){
        state=sa;
    }

    private static String[] arrs={"<html>","<meta>","<title>","<head>","<body>",
        "<div>","<a>","<ul>","<li>","<img>","<p>","<form>","<input>","<ol>","<span>",
        "<h1>","<h2>","<h3>","<h4>","<h5>","<h6>","<table>","<tr>","<th>"};

    private static String[] endArrs={"</html>","</meta>","</title>","</head>","</body>",
            "</div>","</a>","</ul>","</li>","</img>","</p>","</form>","</input>","</ol>","</span>",
            "</h1>","</h2>","</h3>","</h4>","</h5>","</h6>","</table>","</tr>","</th>"};

    public static boolean isHas(String nodeName){
        for(String s:arrs){
            if(s.equals(nodeName)){
                return true;
            }
        }
        return false;
    }
    public static boolean isEnd(String endName){
        for(String s:endArrs){
            if(s.equals(endName)){
                return true;
            }
        }
        return false;
    }

    /**状态变化说明：
     * 当ch==<并且state=0  state=13保存fnode 和enode
     * 当ch===并且state=13 state=14 保存key
     * 当ch=="并且state=14 state=141
     * 当ch=="并且state=141 state=13 保存val
     * 当ch==>并且state=11 || 13 || 14 || 141  state=15
     *
     * 注：state=11 || 13 || 14 || 141 不添加空格
     *     state=15添加空格
     *
     *
     *
     * */


    /**
     *
     *
     * <ol></>
     * <span></>
     * <h1></>
     * <h2></>
     * <h3></>
     * <h4></>
     * <h5></>
     * <h6></>
     * <table></>
     * <tr></>
     * <th></>
     *
     *
     * <a></>
     *      * <ul></>
     *      * <li></>
     *      * <img></>
     *      * <p></>
     *      * <form></>
     *      * <input></>
     *
     * <html></>
     *      * <meta></> />  没有enode
     *      * <title></>
     *      * <head></>
     *      * <body></>
     *      * <div></>
     * **/
}
