package com.example.layeredarchitecture.controller;

import com.example.layeredarchitecture.bo.ItemBoimpl;
import com.example.layeredarchitecture.dao.SqlUtil;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.view.tdm.ItemTM;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;


public class ManageItemsFormController {
    public AnchorPane root;
    public TextField txtCode;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public JFXButton btnDelete;
    public JFXButton btnSave;
    public TableView<ItemTM> tblItems;
    public TextField txtUnitPrice;
    public JFXButton btnAddNewItem;

    ItemBoimpl itemBoimpl = new ItemBoimpl();

    public ArrayList<ItemDTO> getAll() throws SQLException, ClassNotFoundException {
        itemBoimpl.getAllItem();
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

    public void deleteitem(String code) throws SQLException, ClassNotFoundException {
        itemBoimpl.deleteItem(code);
        SqlUtil.execute("DELETE FROM Item WHERE code=?",code);
    }
    public void saveitem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        itemBoimpl.saveItem(itemDTO);
        SqlUtil.execute("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)",itemDTO.getCode(),itemDTO.getDescription(),itemDTO.getUnitPrice(),itemDTO.getQtyOnHand());

    }

    public void updateitem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        itemBoimpl.updateItem(itemDTO);
        SqlUtil.execute("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?",itemDTO.getDescription(),itemDTO.getUnitPrice(),itemDTO.getQtyOnHand(),itemDTO.getCode());
    }

    public boolean existitem(String code) throws SQLException, ClassNotFoundException {
        itemBoimpl.existItem(code);
        ResultSet resultSet = SqlUtil.execute("SELECT code FROM Item WHERE code=?",code);
        return resultSet.next();
    }

    public String generateId() throws SQLException, ClassNotFoundException {
        itemBoimpl.generateItemId();
        ResultSet rst=SqlUtil.execute("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
        if (rst.next()){
            return rst.getString("code");
        }
        return null;
    }

    public ItemDTO finditem(String newItemCode) throws SQLException, ClassNotFoundException {
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
