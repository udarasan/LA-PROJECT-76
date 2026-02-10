package com.example.layeredarchitecture.bo;

import com.example.layeredarchitecture.bo.custom.impl.CustomerBOImpl;
import com.example.layeredarchitecture.bo.custom.impl.ItemBOImpl;
import com.example.layeredarchitecture.bo.custom.impl.PlaceOrderBoImpl;


public class BOFactory {
    public static BOFactory instance;
    private BOFactory() {

    }
    public static BOFactory getInstance() {
        return instance ==null?new BOFactory():instance;
    }
    public enum BOType{
        CUSTOMER,ITEM,PLACE_ORDER
    }
    public SuperBO getBO(BOType type){
        switch(type){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case PLACE_ORDER:
                return new PlaceOrderBoImpl();
            default:
                return null;
        }
    }
}
