/*
 *  Copyright 2024 Henrique Almeida
 * 
 * This file is part of RMI Dictionary.
 * 
 * RMI Dictionary is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * RMI Dictionary is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU
 * General Public License along with RMI Dictionary. If not, see
 * <https:
*/

package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import service.Dictionary;
import service.DictServant;

/**
 * Server class that starts the RMI registry and binds the Dictionary service
 */
public class Server {
    /**
     * Main method
     * 
     * @param args input arguments
     * @throws RemoteException In case of an error in the RMI connection
     */
    public static void main(String[] args) {
        try {
            // Startup task: create RMI registry and bind DictionaryService
            LocateRegistry.createRegistry(1099); // Default port for RMI registry
            Dictionary service = new DictServant();
            Naming.rebind("DictionaryService", service); // Service name registered in RMI
            service.readFromFile(); // Startup task: read dictionary from file
            System.out.println("Server is running...");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> { // Shutdown task: write dictionary to file
                try {
                    service.writeToFile();
                    System.out.println("Server is shutting down...");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }));
        } catch (MalformedURLException e) {
            System.out.println("Error: Malformed URL");
        } catch (RemoteException e) {
            System.out.println("Error: Could not bind service to registry");
            e.printStackTrace();
        }
    };
}
