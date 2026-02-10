package com.example.layeredarchitecture.dao.custom;

import com.example.layeredarchitecture.dao.CrudDAO;
import com.example.layeredarchitecture.dto.CustomerDTO;
import com.example.layeredarchitecture.enity.Customer;

import java.sql.SQLException;


public interface CustomerDAO extends CrudDAO<Customer> {
    void filterCustomerByName(String name) throws SQLException, ClassNotFoundException;
}
