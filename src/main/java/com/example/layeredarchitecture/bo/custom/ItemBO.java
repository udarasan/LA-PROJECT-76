package com.example.layeredarchitecture.bo.custom;

import com.example.layeredarchitecture.dao.CRUDUtil;
import com.example.layeredarchitecture.model.ItemDTO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO {
    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException ;
    public boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException ;
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;
    public boolean exitsItem(String code) throws SQLException, ClassNotFoundException;
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException;
    public String generateNewID() throws SQLException, ClassNotFoundException ;
}
