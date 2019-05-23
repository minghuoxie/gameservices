package net.dbconnect;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;

public interface DbCallBack {
    public void callBack(ResultSet reSet, ResultSetMetaData reData);
}
