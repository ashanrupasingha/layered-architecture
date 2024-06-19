package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;



public interface CrudDAO<T> {

    public void update(T dto) throws SQLException, ClassNotFoundException ;

    public boolean exist(String id) throws SQLException, ClassNotFoundException ;

    public void delete(String id) throws SQLException, ClassNotFoundException ;

    public ArrayList<T> getAll() throws SQLException, ClassNotFoundException ;

    public void save(T dto) throws SQLException, ClassNotFoundException ;

    public String generateId() throws SQLException, ClassNotFoundException ;

    public T find(String newValue) throws SQLException, ClassNotFoundException ;
}
