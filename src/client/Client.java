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

import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import service.Dictionary;

public class Client {
    private static Dictionary service;
    
    public static String readStr(String msg) {
        String input = System.console().readLine(msg).trim();
        return input.isEmpty() ? readStr("ERROR: Invalid value. Type something: ") : input;
    };
    
    public static Integer readInt(String msg) {
        try {
            return Integer.parseInt(Client.readStr(msg));
        } catch (NumberFormatException e) {
            return readInt("ERROR: Invalid value. Type an integer: ");
        }
    };
    
    public static int displayMenu() {
        return readInt("\n1. Look up a word\n2. Add a word\n3. Remove a word\n4. Display dictionary\nEnter your choice: ");
    };
    
    public static void app() throws RemoteException {
        switch (displayMenu()) {
            case 1:
                System.out.printf("Meaning: %s\n", service.lookup(Client.readStr("Enter the word to look up: ")));
                break;
            case 2:
                service.addWord(Client.readStr("Enter the word to add: "), Client.readStr("Enter the meaning of the word: "));
                break;
            case 3:
                service.removeWord(Client.readStr("Enter the word to remove: "));
                break;
            case 4:
                System.out.println(service.getDictionary());
                break;
            default:
                System.out.println("Invalid choice");
        }
    };
    
    public static void main(String[] args) throws Exception {
        try {
            service = (Dictionary) Naming.lookup("//localhost/DictionaryService");
            while (true)
                Client.app();
        } catch (ConnectException e) {
            System.out.println("Error: Connection refused. Is the Server running?");
        }
    };
}
