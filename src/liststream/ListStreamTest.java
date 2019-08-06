package liststream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListStreamTest {
    private static List<TeaListStreamTest> initDatas(){
        List<TeaListStreamTest> list=new ArrayList<>();
        TeaListStreamTest one=new TeaListStreamTest(1,"one",25);
        TeaListStreamTest er=new TeaListStreamTest(2,"er",23);
        TeaListStreamTest san=new TeaListStreamTest(3,"san",22);
        TeaListStreamTest si=new TeaListStreamTest(4,"si",21);
        TeaListStreamTest wu=new TeaListStreamTest(5,"wu",26);
        TeaListStreamTest liu=new TeaListStreamTest(6,"liu",25);
        list.add(one);
        list.add(er);
        list.add(san);
        list.add(si);
        list.add(wu);
        list.add(liu);
        return list;
    }

    private static  List<Sno> initDatast(){
        List<Sno> list=new ArrayList<>();
        Sno no=new Sno(0,1,"1");
        Sno no1=new Sno(1,2,"2");
        Sno no2=new Sno(1,3,"3");
        Sno no3=new Sno(2,4,"4");
        Sno no4=new Sno(99,5,"5");
        Sno no5=new Sno(3,6,"6");
        Sno no6=new Sno(2,7,"7");
        Sno no7=new Sno(6,8,"8");
        Sno no8=new Sno(6,9,"9");
        Sno no0=new Sno(8,10,"10");
        list.add(no);
        list.add(no1);
        list.add(no2);
        list.add(no3);
        list.add(no4);
        list.add(no5);
        list.add(no6);
        list.add(no7);
        list.add(no8);
        list.add(no0);
        return list;
    }

    private static void test_find(int pid,List<Sno> list,List<Sno> rmList){
       for(Sno sno:list){
           if(sno.getPid()==pid){
               test_find(sno.getId(),list,rmList);
               rmList.add(sno);
           }
       }
    }

    public static void testFind(){
        List<Sno> list=initDatast();
        List<Sno> rmList=new ArrayList<>();
        test_find(6,list,rmList);

        System.out.println("----------");
    }

    public static void testStream(){
        List<TeaListStreamTest> list=initDatas();
        List<TeaListStreamTest> newList=list.stream()
                .filter(tea->tea.getId()>=4)
                .map((tea)->{
                    tea.setAge(tea.getAge()-1);
                    tea.setName(tea.getName()+"_up");
                    return tea;
                })
                .collect(Collectors.toList());

        newList.forEach(newTea->{
            System.out.println(newTea);
        });
    }
}
