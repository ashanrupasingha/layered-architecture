package com.example.layeredarchitecture.bo;

import com.example.layeredarchitecture.dao.OrderDAO;
import com.example.layeredarchitecture.dao.SqlUtil;
import com.example.layeredarchitecture.dao.impl.OrderDAOImpl;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDTO;

import java.sql.*;
import java.util.ArrayList;

public class ItemBoimpl {
    ItemBoimpl itemBoimpl=new ItemBoimpl();
    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Item");
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        while (rst.next()){
            itemDTOS.add(new ItemDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getBigDecimal(3),
                    rst.getInt(4)
            ));
        }
        return itemDTOS;
    }
    public void deleteItem(String code) throws SQLException, ClassNotFoundException {
        itemBoimpl.deleteItem(code);
        SqlUtil.execute("DELETE FROM Item WHERE code=?",code);
    }
    public void saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        itemBoimpl.saveItem(itemDTO);
        SqlUtil.execute("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)",itemDTO.getCode(),itemDTO.getDescription(),itemDTO.getUnitPrice(),itemDTO.getQtyOnHand());

    }
    public void updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        itemBoimpl.updateItem(itemDTO);
        SqlUtil.execute("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?",itemDTO.getDescription(),itemDTO.getUnitPrice(),itemDTO.getQtyOnHand(),itemDTO.getCode());
    }
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        itemBoimpl.existItem(code);
        ResultSet resultSet = SqlUtil.execute("SELECT code FROM Item WHERE code=?",code);
        return resultSet.next();
    }
    public String generateItemId() throws SQLException, ClassNotFoundException {
        itemBoimpl.generateItemId();
        ResultSet rst=SqlUtil.execute("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
        if (rst.next()){
            return rst.getString("code");
        }
        return null;
    }
    public ItemDTO findItem(String newItemCode) throws SQLException, ClassNotFoundException {
        itemBoimpl.findItem(newItemCode);
        PreparedStatement pstm=SqlUtil.execute("SELECT * FROM Item WHERE code=?");
        pstm.setString(1, newItemCode + "");
        ResultSet rst = pstm.executeQuery();
        rst.next();
        ItemDTO item = new ItemDTO(newItemCode + "", rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));
        return item;
    }
    public boolean updateItemAfterPlaceOrder(ItemDTO item) throws SQLException, ClassNotFoundException {
       itemBoimpl.updateItemAfterPlaceOrder(item);
        Connection connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(false);
        PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
        pstm.setString(1, item.getDescription());
        pstm.setBigDecimal(2, item.getUnitPrice());
        pstm.setInt(3, item.getQtyOnHand());
        pstm.setString(4, item.getCode());

        if (!(pstm.executeUpdate() > 0)) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }
}
