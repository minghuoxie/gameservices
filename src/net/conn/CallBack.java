package net.conn;

import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class CallBack implements ICallBack{
    @Override
    public void callBackOne(BufferedReader reader) throws IOException {

    }

    @Override
    public Element callBack(Element body) throws Exception{
        return body;
    }
}
