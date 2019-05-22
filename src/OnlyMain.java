import net.connurl.GoMain;
import net.test.TestOne;

public class OnlyMain {
    public static void main(String[] args){
        //test
        test_GoMain_go();

    }

    //测试java.net
    private static void test_TestOne_testOne(){
        new TestOne().testOne();
    }
    //测试goMain
    private static void test_GoMain_go(){
        new GoMain().go();
    }
}
