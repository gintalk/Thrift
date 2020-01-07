/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thriftexample;

import java.util.HashMap;
/**
 *
 * @author cpu11290-local
 */
public class VendorHandler implements Vendor.Iface{
    private static int count;
    private static Item nullItem;
    private HashMap<Integer, Item> menu;
    
    public VendorHandler(){
        count = 0;
        nullItem = new Item(-1);
        menu = new HashMap<>();
    }
    
    @Override
    public String greet(String name){
        return "Welcome, " + name;
    }
    
    @Override
    public HashMap<Integer, Item> getMenu(){
        return menu;
    }
    
    @Override
    public Item getItem(int itemId){
        if(menu.containsKey(itemId)){
            return menu.get(itemId);
        }
        else{
            return nullItem;
        }
    }
    
    @Override
    public void addItem(String itemName, int itemPrice){
        Item newItem = new Item(count);
        newItem.setFieldValue(newItem.fieldForId(2), itemName);
        newItem.setFieldValue(newItem.fieldForId(3), itemPrice);
        menu.put(count++, newItem);
    }
    
    @Override
    public void clearMenu(){
        menu.clear();
    }
}
