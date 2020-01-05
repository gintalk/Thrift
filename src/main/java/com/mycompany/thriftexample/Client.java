/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thriftexample;

import java.util.Map;
import java.util.Scanner;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSocket;
/**
 *
 * @author cpu11290-local
 */
public class Client {
    public static void main(String[] args){
        try{
            TSSLTransportFactory.TSSLTransportParameters params =
                    new TSSLTransportFactory.TSSLTransportParameters();
            params.setTrustStore("src/main/resources/truststore.jks", "password");
            
            try (TSocket socket = TSSLTransportFactory.getClientSocket(
                    "localhost", 8090, 30000, params)) {
                TBinaryProtocol proto = new TBinaryProtocol(socket);
                
                TMultiplexedProtocol vendorProto = new TMultiplexedProtocol(proto, "Vendor");
                Vendor.Client vendorClient = new Vendor.Client(vendorProto);
                
                TMultiplexedProtocol advertiserProto = new TMultiplexedProtocol(proto, "Advertiser");
                Advertiser.Client advertiserClient = new Advertiser.Client(advertiserProto);
                
//                socket.open();
                
                System.out.println("connected");
                perform(vendorClient, advertiserClient);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void perform(Vendor.Client vClient, Advertiser.Client aClient) throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Name: ");
        String name = scanner.nextLine();
        
        System.out.println(vClient.greet(name));
        
        char input;
        
        while(true){
            System.out.print("Option: ");
            input = scanner.nextLine().charAt(0);
            
            if(input == 'q'){
                break;
            }
            else if(input == 'a'){
                System.out.print("Item name: ");
                String itemName = scanner.nextLine();
                
                System.out.print("Item price: ");
                int itemPrice = scanner.nextInt();
                scanner.nextLine();
                
                vClient.addItem(itemName, itemPrice);
                System.out.println("Added");
            }
            else if(input == 'm'){
                Map<Integer, Item> menu = vClient.getMenu();
                
                if(menu.isEmpty()){
                    System.out.println("Nothing");
                }
                else{
                    Item item;
                    
                    for(Map.Entry<Integer, Item> entry: menu.entrySet()){
                        item = entry.getValue();
                        System.out.println(item.toString());
                    }
                }
            }
            else if(input == 'i'){
                System.out.print("Item ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                
                Item item = vClient.getItem(id);
                System.out.println(item.toString());
            }
            else if(input == 'c'){
                vClient.clearMenu();
            }
            else if(input == 'd'){
                System.out.println(aClient.advertise());
            }
        }
        
        System.out.printf("Goodbye, %s\n", name);
    }
}
