package com.solutions.pos.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class StockIDGenerator {

    private static int uniqueID;
    private static ArrayList<Integer> stockIDs;
    public static  int idSize = 0;

//    public static void main(String... arguments) {
//        stockIDs = new ArrayList<>();
//        stockIDs.add(4);
//        setSeedSize(5);
//        setSetStockIDs(stockIDs);
//        uniqueID = generateUniqueID();
//        System.out.println("STOCK IDNO:: " + uniqueID);
//    }

    public static void setSetStockIDs(ArrayList<Integer> availableStockIDs) {
        stockIDs = availableStockIDs;
    }

    public static int getUniqueID() {
        return generateUniqueID();
    }
    
    public static int generateUniqueID(){
        int uniqueStockID = 0;
        try {
            SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
            uniqueStockID = prng.nextInt(seedSize(idSize));
            if (!isSizeOk(uniqueStockID) || !isUnique(uniqueStockID)) {
                System.out.println("DEBUG: ID small than size! :" + uniqueStockID);
                uniqueStockID = prng.nextInt(seedSize(idSize));
                System.out.println("DEBUG: NEW GENERATED: " + uniqueStockID);
            }
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(StockIDGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return uniqueStockID;
    }

    public static int seedSize(int size) {
        StringBuilder sb = new StringBuilder();
        sb.append("1");
        for (int i = 0; i < size - 1; i++) {
            sb.append("0");
        }
        return Integer.parseInt(sb.toString());
    }

    public static Boolean isSizeOk(int id) {
        int lengthID = (int) (Math.log10(id) + 1);
        System.out.println("DEBUG: LenghtID" + lengthID + " Size: " + idSize);
        if (lengthID != idSize - 1) {
            System.out.println("DEBUG: Not equal...regenerate");
            return false;
        }
        return true;
    }

    public static Boolean isUnique(int generatedID) {
        for (int id : stockIDs) {
            if (generatedID == id) {
                return false;
            }
        }
        return true;
    }
}
