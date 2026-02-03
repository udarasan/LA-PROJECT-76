package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.dao.custom.ItemDAO;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException , ClassNotFoundException {
        ResultSet rst = CRUDUtil.execute("SELECT * FROM Item");
        ArrayList<ItemDTO> items = new ArrayList<>();
        while (rst.next()) {
            String code = rst.getString("code");
            String description = rst.getString("description");
            int quantity = rst.getInt("qtyOnHand");
            BigDecimal qtyOnHand = rst.getBigDecimal("unitPrice");

            ItemDTO itemDTO = new ItemDTO(code, description,qtyOnHand,quantity);
            items.add(itemDTO);

        }
        return items;
    }
    @Override
    public boolean saveItem(String code, String description, BigDecimal unitPrice, int qtyOnHand) throws SQLException, ClassNotFoundException {
        return CRUDUtil.execute("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)",code,description,unitPrice,qtyOnHand);
        }

        @Override
        public boolean updateItem( String description,BigDecimal unitPrice , int qtyOnHand ,String code) throws SQLException, ClassNotFoundException {
            return CRUDUtil.execute("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?",description,unitPrice,qtyOnHand,code);

        }
        @Override
        public boolean existItem(String code) throws SQLException, ClassNotFoundException {
            return CRUDUtil.execute("SELECT * FROM Item WHERE code=?",code);

        }
        @Override
        public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
            return CRUDUtil.execute("DELETE FROM Item WHERE code=?",code);
        }
        public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
            return  CRUDUtil.execute("SELECT * FROM Item WHERE code=?",code);
        }

    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CRUDUtil.execute("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("code");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }
}
