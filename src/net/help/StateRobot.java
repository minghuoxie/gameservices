package net.help;

public class StateRobot {
    private static int state=0;
    public final static int ERRO=0;
    public final static int LABEL=11;
    public final static int KEY=13;
    public final static int VAL=14;
    public final static int TENT=15;
    public static int getState(){
        return state;
    }
    public static void setState(int sa){
        state=sa;
    }
    /**状态变化说明：
     * 当ch==<并且state=0  state=11
     * 当ch== 并且state=11 state=13 保存fnode 和enode
     * 当ch===并且state=13 state=14 保存key
     * 当ch=="并且state=14 state=141
     * 当ch=="并且state=141 state=13 保存val
     * 当ch==>并且state=11 || 13 || 14 || 141  state=15
     *
     * 注：state=11 || 13 || 14 || 141 不添加空格
     *     state=15添加空格
     *
     * */
}
