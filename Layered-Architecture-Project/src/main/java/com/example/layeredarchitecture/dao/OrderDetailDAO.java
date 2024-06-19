package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface OrderDetailDAO extends  CrudDAO<OrderDetailDTO> {
    public boolean saveDetail(String orderId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException;
}
