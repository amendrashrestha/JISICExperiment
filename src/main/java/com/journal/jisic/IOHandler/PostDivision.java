
package com.journal.jisic.IOHandler;

import com.journal.jisic.model.Alias;
import com.journal.jisic.model.Posts;
import com.journal.jisic.model.User;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amendrashrestha
 */
public class PostDivision {

    public static List<Alias> divideUserIntoTwoRand(int divisionFlag, List<Alias> aliasList, User tempUser)
            throws FileNotFoundException, IOException {
        
        /**
         * Thsi is testing
         */

        Alias aliasA = new Alias();
        Alias aliasB = new Alias();
        int userID = tempUser.getId();

        List<String> postListA = new ArrayList<>();
        List<String> timeListA = new ArrayList<>();
        List<String> dateListA = new ArrayList<>();
        List<String> postListB = new ArrayList<>();
        List<String> timeListB = new ArrayList<>();
        List<String> dateListB = new ArrayList<>();

        List userPost = tempUser.getUserPost();
        int postSize = userPost.size();

        List<Integer> randomList = IOReadWrite.returnRandomNumberList(postSize);

        for (int i = 0; i < randomList.size(); i++) {
            int postNum = randomList.get(i);
            
            if (i % 2 == 0 && divisionFlag == 2) {
                Posts post = (Posts) userPost.get(postNum);
                String content = post.getContent();
                String time = post.getTime();
                String date = post.getDate();

                postListA.add(content);
                timeListA.add(time);
                dateListA.add(date);
            } else if (i % 2 != 0) {
                Posts post = (Posts) userPost.get(postNum);
                String content = post.getContent();
                String time = post.getTime();
                String date = post.getDate();

                postListB.add(content);
                timeListB.add(time);
                dateListB.add(date);
            }
        }

        if (divisionFlag == 2) {
            aliasA.setUserID(userID);
            aliasA.setType("A");
            aliasA.setPostTime(timeListA);
            aliasA.setPosts(postListA);
            aliasA.setPostDate(dateListA);
            aliasList.add(0, aliasA);
        }

        aliasB.setUserID(userID);
        aliasB.setType("B");
        aliasB.setPostTime(timeListB);
        aliasB.setPosts(postListB);
        aliasB.setPostDate(dateListB);
        aliasList.add(aliasB);

        return aliasList;
    }

}
