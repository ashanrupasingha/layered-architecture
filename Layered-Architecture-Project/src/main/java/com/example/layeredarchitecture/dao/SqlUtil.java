package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlUtil {

    public static <T> T execute(String sql,Object... object) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm= connection.prepareStatement(sql);
        for (int i = 0; i <object.length; i++) {
        pstm.setObject(i+1,object[i]);
        }
        if (sql.startsWith("SELECT")){
return (T)pstm.executeQuery();
        }else{
return (T)(Boolean)(pstm.executeUpdate()>0);
        }
    }

}
