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
 * <https://www.gnu.org/licenses/>.
*/

package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import service.Dictionary;

/**
 * Client class that interacts with the Dictionary service
 */
public class Client {
    /** Dictionary service */
    private static Dictionary service;

    /** This class cannot be instantiated */
    private Client() { throw new InstantiationError("This class cannot be instantiated"); };

    /**
     * Read a string from the console
     * 
     * @param msg message to display
     * @return string read from the console
     */
    public static String readStr(String msg) {
        try {
            String input = System.console().readLine(msg).trim();
            return input.isEmpty() ? readStr("ERROR: Invalid value. Type something: ") : input;
        } catch (NullPointerException e) {
            System.err.println("ERROR: Console error. Exiting...");
            System.exit(1);
        }
        return null;
    };

    /**
     * Read an integer from the console
     * 
     * @param msg message to display
     * @return integer read from the console
     */
    public static Integer readInt(String msg) {
        try {
            return Integer.parseInt(Client.readStr(msg));
        } catch (NumberFormatException e) {
            return readInt("ERROR: Invalid value. Type an integer: ");
        }
    };

    /**
     * Display the menu
     * 
     * @return choice
     */
    public static int displayMenu() {
        return readInt("\n1. Look up a word\n2. Add a word\n3. Remove a word\n4. Display dictionary\nEnter your choice: ");
    };

    /**
     * Application logic
     * 
     * @throws RemoteException In case of an error in the RMI connection
     */
    public static void app() throws RemoteException {
        switch (displayMenu()) {
            case 1:
                System.out.println(service.lookup(Client.readStr("Enter the word to look up: ")));
                break;
            case 2:
                service.addWord(Client.readStr("Enter the word to add: "), Client.readStr("Enter the meaning of the word: "));
                break;
            case 3:
                System.out.println(service.removeWord(Client.readStr("Enter the word to remove: ")));
                break;
            case 4:
                System.out.println(service.getDictionary());
                break;
            default:
                System.out.println("Invalid choice");
        }
    };

    /**
     * Main method
     * 
     * @param args input arguments
     * @throws Exception In case of an error in the RMI connection
     */
    public static void main(String[] args) throws Exception {
        while (service == null) {
            try {
                service = (Dictionary) Naming.lookup(Client.readStr("\nEnter the URL for the Dictionary service.\nExample: //127.0.0.1/DictionaryService\n\nURL: "));
                System.out.println("Connection established.");
            } catch (MalformedURLException e) {
                System.out.println("Error: Malformed URL");
            } catch (NotBoundException e) {
                System.out.println("Error: Service not found");
            } catch (RemoteException e) {
                System.out.println("Error: Connection refused. Is the Server running?");
            }
            try {
                while (service != null)
                    Client.app();
            } catch (RemoteException e) {
                System.out.println("Error: Connection lost. Restarting...");
                service = null;
            }
        }
    };
}
