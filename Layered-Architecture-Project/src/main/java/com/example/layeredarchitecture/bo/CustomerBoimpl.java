package com.example.layeredarchitecture.bo;

import com.example.layeredarchitecture.dao.CustomerDAO;
import com.example.layeredarchitecture.dao.SqlUtil;
import com.example.layeredarchitecture.dao.impl.CustomerDAOImpl;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBoimpl {
    static CustomerDAO customerDAO=new CustomerDAOImpl();
   public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {

       return customerDAO.getAll();
   }
    public  void updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {

       customerDAO.update(customerDTO);
    }

    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {

       return customerDAO.exist(id);
    }

    public void deleteCustomer(String id) throws SQLException, ClassNotFoundException {

        customerDAO.delete(id);

    }



    public void saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {

        customerDAO.save(customerDTO);
    }

    public String generateId() throws SQLException, ClassNotFoundException {

        return customerDAO.generateId();
    }

    public CustomerDTO findCustomer(String newValue) throws SQLException, ClassNotFoundException {

       return customerDAO.find(newValue);
    }
}
