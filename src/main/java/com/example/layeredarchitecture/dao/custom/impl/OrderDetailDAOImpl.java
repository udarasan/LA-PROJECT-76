package com.example.layeredarchitecture.dao.custom.impl;

import com.example.layeredarchitecture.dao.CRUDUtil;
import com.example.layeredarchitecture.dao.custom.OrderDetailDAO;
import com.example.layeredarchitecture.dto.OrderDetailDTO;
import com.example.layeredarchitecture.enity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return CRUDUtil.execute("INSERT INTO OrderDetails (oid, itemCode, unitPrice, qty) VALUES (?,?,?,?)",entity.getOid(),entity.getItemCode(),entity.getUnitPrice(),entity.getQty());
    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exits(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public OrderDetail search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
