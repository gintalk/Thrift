/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thriftexample;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
/**
 *
 * @author cpu11290-local
 */
public class JSONServer {
    private static int port;
    private static VendorHandler vendorHandler;
    private static AdvertiserHandler advertiserHandler;
    private static TMultiplexedProcessor processor;
    
    public static void main(String[] args) throws UnknownHostException{
        try{            
            port = 8090;
            vendorHandler = new VendorHandler();
            advertiserHandler = new AdvertiserHandler();
            processor = new TMultiplexedProcessor();
            
            TServerTransport socket = new TServerSocket(port);
            
//            TSSLTransportFactory.TSSLTransportParameters params =
//                    new TSSLTransportFactory.TSSLTransportParameters();
//            params.setKeyStore("src/main/resources/keystore.jks", "password");
//            
//            TServerSocket socket = TSSLTransportFactory.getServerSocket(
//                    port, 30000, InetAddress.getByName("localhost"), params);
            
            processor.registerProcessor(
                    "Vendor", new Vendor.Processor(vendorHandler));
            processor.registerProcessor(
                    "Advertiser", new Advertiser.Processor(advertiserHandler));
            
            TThreadPoolServer server = 
                new TThreadPoolServer(
                       new TThreadPoolServer.Args(socket).processor(processor)
                            .protocolFactory(new TJSONProtocol.Factory()));
            
            server.serve();
        }
        catch(TTransportException e){
            e.printStackTrace();
        }
    }
    
}
