package net.conn;

import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;

public interface ICallBack {
    public void callBackOne(BufferedReader reader) throws IOException;
    public Element callBack(Element body) throws Exception;
}
