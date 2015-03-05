/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journal.jisic.model;

import java.util.Comparator;

/**
 * This method compares the two Users according to their posts and returns in 
 * descending order.
 * @author ITE
 */
public class ReturnSortedUserList implements Comparator<User> {

    /**
     *
     * @param first
     * @param second
     * @return
     */
    @Override
    public int compare(User first, User second) {
        int firstUseID = first.getUserPost().size();
        int secondUseID = second.getUserPost().size();
//        int firstUseID = first.getId();
//        int secondUseID = second.getId();
        return Integer.compare(secondUseID, firstUseID);
    }
    
}
