package unicode.myhuffmancode;
import java.util.LinkedList;
import java.util.List;

public class CreateHuffmanShu {
    public static MyHuffmanList createHufByNode(List<HufNode> list){
        if(list==null||list.size()<=0){
            return null;
        }
        List<MyHuffmanList> hufList=new LinkedList<>();
        for(HufNode node:list){
            MyHuffmanList hufl=new MyHuffmanList(node);
            hufList.add(hufl);
        }
        return createHufByList(hufList);
    }
    public static MyHuffmanList createHufByList(List<MyHuffmanList> list){
        if(list==null||list.size()<=0){
            return null;
        }
        int len=0;
        while((len=list.size())>1){
            /**
             * 找到集合中权值最小的两个
             *
             * */
            MyHuffmanList hufmanMinOne=list.get(0);
            for(MyHuffmanList lin:list){
                if(lin.getFirstNode().getQuan()<hufmanMinOne.getFirstNode().getQuan()){
                    hufmanMinOne=lin;
                }
            }
            MyHuffmanList hufmanMinTwo=list.get(len-1);
            if(hufmanMinTwo==hufmanMinOne){
                hufmanMinTwo=list.get(0);
            }
            for(MyHuffmanList lin:list){
                if(lin.getFirstNode().getQuan()<hufmanMinTwo.getFirstNode().getQuan()&&lin!=hufmanMinOne){
                    hufmanMinTwo=lin;
                }
            }
            // 将两个合并为一个新的
            MyHuffmanList newList=new MyHuffmanList();
            newList.addNode(hufmanMinOne.getFirstNode(),hufmanMinTwo.getFirstNode());
            //将集合中旧的移除，添加新的
//            for(MyHuffmanList lin:list){
//                if(lin==hufmanMinOne||lin==hufmanMinTwo){
//                    list.remove(lin);
//                }
//            }
            list.remove(hufmanMinOne);
            list.remove(hufmanMinTwo);
            list.add(newList);
        }
        return list.get(0);
    }

}
