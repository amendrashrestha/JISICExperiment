package com.journal.jisic.view;

import com.journal.jisic.IOHandler.IOReadWrite;
import com.journal.jisic.IOHandler.PostDivision;
import com.journal.jisic.controller.AnalyzeTimeFV;
import com.journal.jisic.controller.AnalyzeTimeStyloFV;
import com.journal.jisic.model.Alias;
import com.journal.jisic.model.User;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amendrashrestha
 */
public class Experiment {

    public static void main(String args[]) throws IOException, SQLException, ParseException {
        Experiment init = new Experiment();
//        init.TimeExperiment();
        
        init.TimeStyloExperiment();
    }

    private void TimeExperiment() throws IOException, SQLException, ParseException {
        List<User> userList = IOReadWrite.getAllUsersAsObject();

        //For passing limited number of sorted users  
        List<User> tempUsers = IOReadWrite.returnLimitedSortedUser(userList, 1000);
        List<Alias> splitUsersList;
        int divisionTimes;

        for (int k = 0; k < tempUsers.size(); k++) {
            splitUsersList = new ArrayList<>();
            for (int j = 0; j < tempUsers.size(); j++) {
                if (k == j) {
                    divisionTimes = 2;
                } else {
                    divisionTimes = 1;
                }
                User user = tempUsers.get(j);
                splitUsersList = PostDivision.divideUserIntoTwoRand(divisionTimes, splitUsersList, user);
            }
            AnalyzeTimeFV compare = new AnalyzeTimeFV();
            compare.executeUserCompare(splitUsersList);
        }
    }

    private void TimeStyloExperiment() throws SQLException, ParseException, IOException {
        List<User> userList = IOReadWrite.getAllUsersAsObject();

        //For passing limited number of sorted users  
        List<User> tempUsers = IOReadWrite.returnLimitedSortedUser(userList, 5);
        List<Alias> splitUsersList;
        int divisionTimes;

        for (int k = 0; k < tempUsers.size(); k++) {
            splitUsersList = new ArrayList<>();
            for (int j = 0; j < tempUsers.size(); j++) {
                if (k == j) {
                    divisionTimes = 2;
                } else {
                    divisionTimes = 1;
                }
                User user = tempUsers.get(j);
                splitUsersList = PostDivision.divideUserIntoTwoRand(divisionTimes, splitUsersList, user);
            }
            AnalyzeTimeStyloFV compare = new AnalyzeTimeStyloFV();
            compare.executeUserCompare(splitUsersList);
        }
    }
}