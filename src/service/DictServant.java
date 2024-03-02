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

package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class DictServant extends UnicastRemoteObject implements Dictionary {
    private static final String FILE_PATH = "data/dictionary.ser";
    private Map<String, String> dictionary;
    
    public DictServant() throws RemoteException {
        super();
        dictionary = new HashMap<>();
    };

    @Override
    public String lookup(String word) throws RemoteException {
        String result = dictionary.get(word);
        return result != null ? result : "Word not found";
    };

    @Override
    public void addWord(String word, String meaning) throws RemoteException {
        if (dictionary.put(word, meaning) != null)
            System.out.println("Word already exists. Overwriting meaning...");
    };

    @Override
    public String removeWord(String word) throws RemoteException {
        return dictionary.remove(word);
    };

    @Override
    public String getDictionary() throws RemoteException {
        return dictionary.entrySet().stream() 
                .sorted(Map.Entry.comparingByKey()) 
                .map(e -> String.format("%s: %s", e.getKey(), e.getValue())) 
                .reduce((a, b) -> a + "\n" + b) 
                .orElse("Dictionary is empty."); 
    };

    @Override
    public void writeToFile() throws RemoteException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            outputStream.writeObject(dictionary);
            System.out.println("Dictionary has been written to file.");
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    };

    @Override
    @SuppressWarnings("unchecked")
    public void readFromFile() throws RemoteException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            dictionary = (Map<String, String>) inputStream.readObject();
            System.out.println("\nDictionary has been read from file.");
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found. Creating new save dir...");
            File dataDir = new File("data");
            if (!dataDir.exists())
                dataDir.mkdir();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file");
            e.printStackTrace();
        }
    };
}
