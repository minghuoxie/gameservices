package media.media;

import java.io.File;

public class XmlAndBpmn {
    //将xml文件转换为bpmn文件

    public static void eachXmlAndBpmn(String filePath,String newFile){
        File file=new File(filePath);
        file.renameTo(new File(newFile));
    }
}
