package com.example.layeredarchitecture.bo;

import com.example.layeredarchitecture.dao.SqlUtil;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.OrderDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class OrderBoimpl {
OrderBoimpl orderBoimpl=new OrderBoimpl();
    public String generateId() throws SQLException, ClassNotFoundException {
        orderBoimpl.generateId();
        ResultSet rst = SqlUtil.execute("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");

        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";

    }



    public void getOrder(String orderId) throws SQLException, ClassNotFoundException {
        orderBoimpl.getOrder(orderId);
        SqlUtil.execute("SELECT oid FROM `Orders` WHERE oid=?",orderId);
    }


    public OrderDTO findorder(String orderId) throws SQLException, ClassNotFoundException {
        orderBoimpl.findorder(orderId);
        return SqlUtil.execute("SELECT oid FROM Orders WHERE oid = ?",orderId);
    }

    public boolean saveorder(String orderId, LocalDate orderDate, String customerId) throws SQLException, ClassNotFoundException {
        orderBoimpl.saveorder(orderId,orderDate,customerId);
        Connection connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(false);
        PreparedStatement stm = connection.prepareStatement("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)");
        stm.setString(1, orderId);
        stm.setDate(2, Date.valueOf(orderDate));
        stm.setString(3, customerId);

        if (stm.executeUpdate() != 1) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        return true;
    }
}
