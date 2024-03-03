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

package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Dictionary service interface
 * 
 * @see DictServant
 */
public interface Dictionary extends Remote {
    /** Serial version */
    public static final long serialVersionUID = 1L;

    /**
     * Lookup an item in the dictionary
     * 
     * @param item item to lookup
     * @return meaning of the item
     * @throws RemoteException In case of an error in the RMI connection
     */
    String lookup(String item) throws RemoteException;

    /**
     * Add an item to the dictionary
     * 
     * @param item    item to add
     * @param meaning meaning of the item
     * @throws RemoteException In case of an error in the RMI connection
     */
    void addWord(String item, String meaning) throws RemoteException;

    /**
     * Remove an item from the dictionary
     * 
     * @param item item to remove
     * @return meaning of the item
     * @throws RemoteException In case of an error in the RMI connection
     */
    String removeWord(String item) throws RemoteException;

    /**
     * Get the dictionary
     * 
     * @return dictionary
     * @throws RemoteException In case of an error in the RMI connection
     */
    String getDictionary() throws RemoteException;

    /**
     * Write the dictionary to a file
     * 
     * @throws RemoteException In case of an error in the RMI connection
     */
    void writeToFile() throws RemoteException;

    /**
     * Read the dictionary from a file
     * 
     * @throws RemoteException In case of an error in the RMI connection
     */
    void readFromFile() throws RemoteException;
}
