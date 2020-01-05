/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thriftexample;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author cpu11290-local
 */
public class Server {
    private static int port;
    private static VendorHandler vendorHandler;
    private static AdvertiserHandler advertiserHandler;
    private static TMultiplexedProcessor processor;
    
    public static void main(String[] args){
        //http server
        try{
            port = 8090;
            vendorHandler = new VendorHandler();
            advertiserHandler = new AdvertiserHandler();
            processor = new TMultiplexedProcessor();
            
            processor.registerProcessor("Vendor", new Vendor.Processor(vendorHandler));
            processor.registerProcessor("Advertiser", new Advertiser.Processor(advertiserHandler));
            
            TServerSocket socket = new TServerSocket(port);
            TThreadPoolServer server = 
                new TThreadPoolServer(
                       new TThreadPoolServer.Args(socket).processor(processor));
            
            server.serve();
        }
        catch(TTransportException e){
        }
        // thrift server
    }
    
}
