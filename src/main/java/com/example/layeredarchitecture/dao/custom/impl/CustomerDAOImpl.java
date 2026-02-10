package com.example.layeredarchitecture.dao.custom.impl;

import com.example.layeredarchitecture.dao.CRUDUtil;
import com.example.layeredarchitecture.dao.custom.CustomerDAO;
import com.example.layeredarchitecture.dto.CustomerDTO;
import com.example.layeredarchitecture.enity.Customer;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CRUDUtil.execute("SELECT * FROM Customer");
        ArrayList<Customer> customers = new ArrayList<>();
        while (rst.next()) {
            String id = rst.getString("id");
            String name = rst.getString("name");
            String address = rst.getString("address");
            Customer entity = new Customer(id, name, address);
            customers.add(entity);
        }
        return customers;
    }
    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        return CRUDUtil.execute("INSERT INTO Customer (id,name, address) VALUES (?,?,?)", entity.getId(), entity.getName(), entity.getAddress());
    }
    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return CRUDUtil.execute("UPDATE Customer SET name=?,address=? WHERE id=?", entity.getName(), entity.getAddress(), entity.getId());
    }
    @Override
    public boolean exits(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst= CRUDUtil.execute("SELECT id FROM Customer WHERE id=?",id);
        return  rst.next();
    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CRUDUtil.execute("DELETE FROM Customer WHERE id=?", id);
    }
    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst =CRUDUtil.execute("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }
    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CRUDUtil.execute("SELECT * FROM Customer WHERE id=?", id);
        if (rst.next()) {
            return new Customer(rst.getString("id"), rst.getString("name"), rst.getString("address"));
        }
        return null;
    }
    @Override
    public void filterCustomerByName(String name) throws SQLException, ClassNotFoundException {

    }
}
