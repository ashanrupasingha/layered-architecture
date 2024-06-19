package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.OrderDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface OrderDAO extends  CrudDAO<OrderDTO> {
    void getOrder(String orderId) throws SQLException, ClassNotFoundException;
    public boolean findOrder(String orderId, List<OrderDetailDTO> orderDetails);
    public boolean save(String orderId, LocalDate orderDate, String customerId) throws SQLException, ClassNotFoundException;

}