/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thriftexample;

/**
 *
 * @author cpu11290-local
 */
public class AdvertiserHandler implements Advertiser.Iface{
    @Override
    public String advertise(){
        return "Visit us!";
    }
}
