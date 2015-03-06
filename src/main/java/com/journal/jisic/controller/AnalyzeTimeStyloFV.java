/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journal.jisic.controller;

import com.journal.jisic.IOHandler.IOReadWrite;
import com.journal.jisic.model.Alias;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author amendrashrestha
 */
public class AnalyzeTimeStyloFV {

List tempDisplayInfo;
    List<Integer> rank = new ArrayList<>();
    int rankArray[] = {0, 0, 0, 0};
    int top1, top3, other;
    HashSet matchedUserSet = new HashSet();
    static HashMap<Integer, HashSet<Integer>> userResult = new HashMap<>();
    Set userBin = new HashSet();

    public void executeUserCompare(List<Alias> aliasList) throws SQLException, ParseException {
        compareAllPairsOfAliases(aliasList);
        displayUserRank();
    }

    public void compareAllPairsOfAliases(List<Alias> aliases) throws SQLException, ParseException {
        tempDisplayInfo = new ArrayList<>();

        for (int i = 1; i < aliases.size(); i++) {
            List tempList = new ArrayList();

            /**
             * calculating time vector for alias
             */
            double userMatch;

            /**
             * Main user
             */
            Alias mainUser = aliases.get(0);
            int user1 = mainUser.getUserID();
            Float[] mainUserTimeFV = IOReadWrite.getUserTimeProfile(mainUser);
            Float[] mainUserStyloFV = IOReadWrite.createPostFeatureVectorForUser(mainUser);
            
            Float[] mainUserFV = IOReadWrite.concatArrays(mainUserStyloFV,mainUserTimeFV);
            
            /**
             * Other user
             */
            Alias otherUsers = aliases.get(i);
            int user2 = otherUsers.getUserID();
            Float[] normUserTimeFV = IOReadWrite.getUserTimeProfile(otherUsers);
            Float[] normUserStyloFV = IOReadWrite.createPostFeatureVectorForUser(otherUsers);
            Float[] normUserFV = IOReadWrite.concatArrays(normUserStyloFV,normUserTimeFV);
            

            userMatch = calculateSimilarity(mainUserFV, normUserFV);

            tempList.add(user1);
            tempList.add(user2);
            tempList.add(userMatch);
            tempDisplayInfo.add(tempList);
        }
        getsorted(tempDisplayInfo);
        tempDisplayInfo.clear();
    }

    /**
     * Calculate Manhattan distance between two users
     *
     * @param sequence1
     * @param sequence2
     * @return
     */
    public float calculateManhattanDistance(Float[] sequence1, Float[] sequence2) {
        float manhattanDistance = 0.0f;
        for (int i = 0; i < sequence1.length; i++) {
            float firstElementsequence1 = sequence1[i];
            //System.out.println("First " + firstElementsequence1);
            float firstElementsequence2 = sequence2[i];
            //System.out.println("Second " + firstElementsequence2);
            manhattanDistance = manhattanDistance + Math.abs(firstElementsequence2 - firstElementsequence1);
            // System.out.println("Manha: " + manhattanDistance);
            //System.out.println("---------");
        }
        return manhattanDistance;
    }
    
    /**
     * Calculates cosine similarity between two real vectors
     *
     * @param value1
     * @param value2
     * @return
     */
    public double calculateSimilarity(Float[] value1, Float[] value2) {
        float sum = 0.0f;
        float sum1 = 0.0f;
        float sum2 = 0.0f;
        for (int i = 0; i < value1.length; i++) {
            float v1 = value1[i];
            float v2 = value2[i];
            if ((!Float.isNaN(v1)) && (!Float.isNaN(v2))) {
                sum += v2 * v1;
                sum1 += v1 * v1;
                sum2 += v2 * v2;
            }
        }
        if ((sum1 > 0) && (sum2 > 0)) {
            double result = sum / (Math.sqrt(sum1) * Math.sqrt(sum2));
            // result can be > 1 (or -1) due to rounding errors for equal vectors, 
            //but must be between -1 and 1
            return Math.min(Math.max(result, -1d), 1d);
            //return result;
        } else if (sum1 == 0 && sum2 == 0) {
            return 1d;
        } else {
            return 0d;
        }
    }

    /**
     * sort list of time
     *
     * @param info
     * @return
     */
    public void getsorted(List info) {
        List tempinfo = new ArrayList();
        tempinfo.addAll(info);
        //System.out.println("Time list: " + tempTimeinfo);

        Collections.sort(tempinfo, new Comparator<List>() {
            @Override
            public int compare(List o1, List o2) {
                Double firstNumber = Double.parseDouble(o1.get(2).toString());
                Double secondNumber = Double.parseDouble(o2.get(2).toString());
                return firstNumber.compareTo(secondNumber);
            }
        });
        Collections.reverse(tempinfo);
        System.out.println("After sorting: " + tempinfo);
        createRank(tempinfo);
    }

    /**
     * find similar user within the list
     *
     * @param tempdisplayInfo
     */
    public void createRank(List tempdisplayInfo) {
        int infoSize = tempdisplayInfo.size();
        int index = 0;

        for (int i = 0; i < infoSize; i++) {
            String row = tempdisplayInfo.get(i).toString();
            String[] strArray = row.split(",");
            String strfirstUser = strArray[0].substring(1, strArray[0].length()).trim();
            String strsecondUser = strArray[1].trim();
            System.out.println("Similar Users: " + strfirstUser + " with " + strsecondUser);
            int user1 = Integer.parseInt(strfirstUser);
            int user2 = Integer.parseInt(strsecondUser);

            if (user1 == user2) {
                rank.add(i + 1);
                index = i + 1;
                System.out.println("Matched At: " + (i + 1));
                addIndexUser(index, user1);
                break;
            }
        }
    }

    /**
     * add user and their rank in hash map
     *
     * @param key
     * @param value
     */
    private void addIndexUser(int key, int value) {
        HashSet tempList = null;
        if (userResult.containsKey(key)) {
            tempList = userResult.get(key);

            if (tempList == null) {
                tempList = new HashSet();
            }
            tempList.add(value);
        } else {
            tempList = new HashSet();
            tempList.add(value);
        }
        userResult.put(key, tempList);
    }

    private void displayUserRank() {
        System.out.println("\nResult: " + userResult);
        int top1Size = 0;
        for (Map.Entry<Integer, HashSet<Integer>> MapEntry : userResult.entrySet()) {
            Integer tempRank = MapEntry.getKey();
            if (tempRank.equals(1)) {
                HashSet top1Users = MapEntry.getValue();
                top1Size = top1Users.size();
                userBin.addAll(top1Users);
            } else if (tempRank.equals(2)) {
                HashSet top2Users = MapEntry.getValue();
                userBin.addAll(top2Users);
            } else if (tempRank.equals(3)) {
                HashSet top3Users = MapEntry.getValue();
                userBin.addAll(top3Users);
            }
        }
        System.out.println("Top 1: " + top1Size);
        System.out.println("Top 3: " + userBin.size());
        System.out.println("*********************");
    }
    
}
