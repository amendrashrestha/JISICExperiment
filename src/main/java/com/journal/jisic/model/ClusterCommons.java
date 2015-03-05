/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journal.jisic.model;

import java.util.HashMap;


/**
 *
 * @author ITE
 */
public class ClusterCommons {
    
    public boolean checkIfUserHasCommonTimeFrame(int[] matchedTimeFrame) {
        boolean flag = false;
        for (int i = 0; i < matchedTimeFrame.length; i++) {
            if (matchedTimeFrame[i] == 1) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public int[] getMatchedTimeFrame(int[] firstUser, int[] secondUser) {
        int[] matchedTimeFrame = {0, 0, 0, 0, 0, 0};
        for (int i = 0; i < firstUser.length; i++) {
            if (firstUser[i] == 1 && secondUser[i] == 1) {
                matchedTimeFrame[i] = 1;
            }
        }
        return matchedTimeFrame;
    }

    /**
     * @Desc There are 6 category for the time {00-04} which is given a number 0
     * {04-08} which is given a number 1 {08-12} which is given a number 2
     * {12-16} which is given a number 3 {16-20} which is given a number 4
     * {20-24} which is given a number 5 where the first hour in the time range
     * is included whereas the second hour in time range is excluded.
     * @param hour
     * @return int
     */
    public int getTimeCategory(int hour) {
        int category = 6;
        if (hour >= 0 && hour < 4) {
            return 0;
        } else if (hour >= 4 && hour < 8) {
            return 1;
        } else if (hour >= 8 && hour < 12) {
            return 2;
        } else if (hour >= 12 && hour < 16) {
            return 3;
        } else if (hour >= 16 && hour < 20) {
            return 4;
        } else if (hour >= 20 && hour < 24) {
            return 5;
        }
        return category;
    }

    /**
     * @Desc This function contains the time frame definition for post
     * classification.
     * @param None
     * @return HashMap
     */
    public HashMap timeCategoryDefinition() {
        HashMap<Integer, String> hashMap = new HashMap();
        hashMap.put(0, "0004");
        hashMap.put(1, "0408");
        hashMap.put(2, "0812");
        hashMap.put(3, "1216");
        hashMap.put(4, "1620");
        hashMap.put(5, "2024");
        return hashMap;
    }
}
