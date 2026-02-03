package com.example.layeredarchitecture.dao.custom;

import com.example.layeredarchitecture.dao.CrudDAO;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.SQLException;


public interface CustomerDAO extends CrudDAO<CustomerDTO> {
    void filterCustomerByName(String name) throws SQLException, ClassNotFoundException;
}
