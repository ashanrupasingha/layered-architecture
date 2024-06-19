package com.example.layeredarchitecture.dao.impl;

import com.example.layeredarchitecture.dao.OrderDAO;
import com.example.layeredarchitecture.dao.SqlUtil;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.OrderDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public void update(OrderDTO dto) throws SQLException, ClassNotFoundException {

    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public void delete(String id) throws SQLException, ClassNotFoundException {

    }

    @Override
    public ArrayList<OrderDTO> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public void save(OrderDTO dto) throws SQLException, ClassNotFoundException {

    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getDbConnection().getConnection();
//        Statement stm = connection.createStatement();
//        ResultSet rst = stm.executeQuery("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");
        ResultSet rst = SqlUtil.execute("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");

        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";

    }


    @Override
    public void getOrder(String orderId) throws SQLException, ClassNotFoundException {
//        Connection connection = null;
//        connection = DBConnection.getDbConnection().getConnection();
//        PreparedStatement stm = connection.prepareStatement("SELECT oid FROM `Orders` WHERE oid=?");
//        stm.setString(1, orderId);
        SqlUtil.execute("SELECT oid FROM `Orders` WHERE oid=?",orderId);
    }

    @Override
    public boolean findOrder(String orderId, List<OrderDetailDTO> orderDetails) {
        return false;
    }

    @Override
    public OrderDTO find(String orderId) throws SQLException, ClassNotFoundException {
//        String sql = "SELECT oid FROM Orders WHERE oid = ?";
//        PreparedStatement pstm = DBConnection.getDbConnection().getConnection().prepareStatement(sql);
//        pstm.setString(1, orderId);
//        return pstm.executeQuery().next();
       return SqlUtil.execute("SELECT oid FROM Orders WHERE oid = ?",orderId);
    }
    @Override
    public boolean save(String orderId, LocalDate orderDate, String customerId) throws SQLException, ClassNotFoundException {

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

