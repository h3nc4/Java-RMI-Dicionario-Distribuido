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

public interface Dictionary extends Remote {
    public static final long serialVersionUID = 1L;

    String lookup(String word) throws RemoteException;

    void addWord(String word, String meaning) throws RemoteException;

    String removeWord(String word) throws RemoteException;

    String getDictionary() throws RemoteException;

    void writeToFile() throws RemoteException;

    void readFromFile() throws RemoteException;
}
