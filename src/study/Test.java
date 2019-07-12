package study;

import java.util.List;

public class Test {

    public void testist(List<? extends Basebean> list){
        System.out.println(list.get(0).getName());
    }
}
