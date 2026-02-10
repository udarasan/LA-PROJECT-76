package com.example.layeredarchitecture.bo.custom.impl;

import com.example.layeredarchitecture.bo.custom.PlaceOrderBO;
import com.example.layeredarchitecture.dao.DAOFactory;
import com.example.layeredarchitecture.dao.custom.CustomerDAO;
import com.example.layeredarchitecture.dao.custom.ItemDAO;
import com.example.layeredarchitecture.dao.custom.OrderDAO;
import com.example.layeredarchitecture.dao.custom.OrderDetailDAO;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.dto.CustomerDTO;
import com.example.layeredarchitecture.dto.ItemDTO;
import com.example.layeredarchitecture.dto.OrderDTO;
import com.example.layeredarchitecture.dto.OrderDetailDTO;
import com.example.layeredarchitecture.enity.Customer;
import com.example.layeredarchitecture.enity.Item;
import com.example.layeredarchitecture.enity.Order;
import com.example.layeredarchitecture.enity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBoImpl implements PlaceOrderBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);
    OrderDetailDAO  orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER_DETAIL);
    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer= customerDAO.search(id);
        return new CustomerDTO(customer.getId(),customer.getName(),customer.getAddress());
    }

    @Override
    public ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException {
        Item item= itemDAO.search(id);
        return new ItemDTO(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());
    }

    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer>customers=customerDAO.getAll();
        ArrayList<CustomerDTO>customerDTOs=new ArrayList<>();
        for(Customer customer:customers){
            CustomerDTO customerDTO=new CustomerDTO(customer.getId(),customer.getName(),customer.getAddress());
            customerDTOs.add(customerDTO);
        }
        return customerDTOs;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> items= itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOs= new ArrayList<>();
        for(Item item:items){
            ItemDTO itemDTO= new ItemDTO(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());
            itemDTOs.add(itemDTO);
        }
        return itemDTOs;
    }

    @Override
    public boolean exitCustomers(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exits(id);
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exits(code);
    }

    @Override
    public boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        /*Transaction*/
        Connection connection = null;

        connection = DBConnection.getDbConnection().getConnection();
        boolean b1 = orderDAO.exits(orderId);
            /*if order id already exist*/
        if (b1) {
                return false;
        }
        connection.setAutoCommit(false);
        boolean b2 = orderDAO.save(new Order(orderId,orderDate,customerId));
        if (!b2) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetailDTO detail : orderDetails) {

            boolean b3 = orderDetailDAO.save(new OrderDetail(detail.getOid(),detail.getItemCode(),detail.getQty(),detail.getUnitPrice()));

            if (!b3) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
//                //Search & Update Item
            ItemDTO item = findItem(detail.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());


            boolean b4 = itemDAO.update(new Item(item.getCode(),item.getDescription(),item.getUnitPrice(), item.getQtyOnHand()));

            if (!b4) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }
    public ItemDTO findItem(String code) {
        try {
            Item item= itemDAO.search(code);
            return new ItemDTO(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}


